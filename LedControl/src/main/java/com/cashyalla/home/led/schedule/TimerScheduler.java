package com.cashyalla.home.led.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cashyalla.home.led.domain.CurrentLedMode;
import com.cashyalla.home.led.domain.DimDetail;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.GpioPwm;
import com.cashyalla.home.led.domain.TimerSchedule;
import com.cashyalla.home.led.domain.TimerScheduleDetail;
import com.cashyalla.home.led.properties.ConfigProperties;
import com.cashyalla.home.led.service.DimmingService;
import com.cashyalla.home.led.service.LedControlService;
import com.cashyalla.home.led.service.TimerScheduleService;

@Component
public class TimerScheduler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private LedControlService ledControlService;
	
	@Autowired
	private TimerScheduleService timerScheduleService;
	
	@Autowired
	private DimmingService dimmingService;
	
	@Scheduled(cron = "0 */1 * * * *")
	@Transactional
	public void ledTimer() {

		// 현재 설정된 모드 조회
		CurrentLedMode currentLedMode = ledControlService.getCurrentLedMode();

		// 현재 설정된 모드가 없다면 끝!
		if (currentLedMode == null) {
			return;
		}

		// 타이머로 설정 되었을 경우
		if (currentLedMode.getModeId().equals(configProperties.getModeSchedule())) {
			timerSchedule();
		}
		
	}

	private void timerSchedule() {
		List<TimerSchedule> scheduleList = timerScheduleService.getTimerScheduleList();
		
		// 스케쥴이 등록되지 않았다면 아무것도 하지 않음
		if (scheduleList == null || scheduleList.isEmpty() == true) {
			return;
		}
		
		// 스케쥴이 하나만 등록 되었다면 밝기는 등록된 하나만으로 고정
		if (scheduleList.size() == 1) {
			setBrightnessByTimerSchedule(scheduleList.get(0));
		}

		// 지금 시 분을 기준으로 바로 전의 설정 데이터
		TimerSchedule prevSchedule = null;
		// 지금 시 분을 기준으로 바로 다음 설정 데이터
		TimerSchedule nextSchedule = null;
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date now = null;
		
		try {
			now = timeFormat.parse(timeFormat.format(new Date()));	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO: 텔레그램으로 실패 메세지 전송
		}

		for (TimerSchedule schedule : scheduleList) {
			// 설정된 시간과 현재 시간이 일치하면 그 값으로 설정하면 됨!!
			if (schedule.getScheduleTime().getTime() == now.getTime()) {
				setBrightnessByTimerSchedule(schedule);

				return;
			}

			if (schedule.getScheduleTime().getTime() < now.getTime()) {
				if (prevSchedule == null) {
					prevSchedule = schedule;
				} else if (prevSchedule.getScheduleTime().getTime() < schedule.getScheduleTime().getTime()) {
					prevSchedule = schedule;
				}
			} else if (schedule.getScheduleTime().getTime() > now.getTime()) {
				if (nextSchedule == null) {
					nextSchedule = schedule;
				} else if (nextSchedule.getScheduleTime().getTime() > schedule.getScheduleTime().getTime()) {
					nextSchedule = schedule;
				}
			}
		}

		if (prevSchedule == null) {
			prevSchedule = scheduleList.get(scheduleList.size() - 1);
		}
		
		if (nextSchedule == null) {
			nextSchedule = scheduleList.get(0);
		}
		
		// 이 전 설정 타임이 다음 설정 타임보다 작다면 (ex next 06:00 prev : 23:00)
		// 다음 설정 타임에 24시간을 더해준다.
		if (prevSchedule.getScheduleTime().getTime() > nextSchedule.getScheduleTime().getTime()) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(nextSchedule.getScheduleTime());
			cal.add(Calendar.HOUR, 24);
			nextSchedule.setScheduleTime(cal.getTime());
		}
		
		long termOfTime = Math.abs(nextSchedule.getScheduleTime().getTime() - prevSchedule.getScheduleTime().getTime());

		Map<DimGroup, Integer> sourceDimValueMap = new HashMap<>();
		Map<DimGroup, Integer> targetDimValueMap = new HashMap<>();
		
		for (TimerScheduleDetail prevDetail : prevSchedule.getTimerScheduleDetailList()) {
			
			for (TimerScheduleDetail nextDetail : nextSchedule.getTimerScheduleDetailList()) {
				if (prevDetail.getDimGroup().getDimId().equals(nextDetail.getDimGroup().getDimId()) == true) {
					// 이 전 스케쥴과 다음 스케쥴의 설정값의 차
					int termOfValue = nextDetail.getSetValue() - prevDetail.getSetValue();
					// 현재 설정되여야 하는 값 계산
					// 1밀리초 당 변화되는 값 계산
					double termOfValuePerMillis = (double) termOfValue / (double) termOfTime;
					// 이 전 스케쥴 시간부터 지금까지 지난 시간을 구한 후 더해야 할 값 계산
					int increasedValue = (int) (termOfValuePerMillis * (now.getTime() - prevSchedule.getScheduleTime().getTime()));
					
					targetDimValueMap.put(nextDetail.getDimGroup(), prevDetail.getSetValue() + increasedValue);
					sourceDimValueMap.put(nextDetail.getDimGroup(), 0);
				}
			}
		}
		
		// 즉시 밝기 조정
		dimmingService.changeBrightness(sourceDimValueMap, targetDimValueMap, 0);

	}
	
	private void setBrightnessByTimerSchedule(TimerSchedule schedule) {
		
		List<GpioPwm> pwmList = new ArrayList<>();
		
		for (TimerScheduleDetail detail : schedule.getTimerScheduleDetailList()) {
			DimGroup dimGroup = detail.getDimGroup();
			List<DimDetail> dimDetailList = ledControlService.getDimDetailListByDimGroup(dimGroup);

			for (DimDetail dimDetail : dimDetailList) {
				GpioPwm pwm = new GpioPwm();
				pwm.setPinNumber(dimDetail.getGpioPinInfo().getPinNumber());
				pwm.setValue(detail.getSetValue());
				pwmList.add(pwm);
			}
		}
		
		dimmingService.setBrightness(pwmList);
	}
	
}
