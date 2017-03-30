package com.cashyalla.home.led.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashyalla.home.led.dao.TimerScheduleDao;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.TimerChartData;
import com.cashyalla.home.led.domain.TimerSchedule;
import com.cashyalla.home.led.domain.TimerScheduleDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TimerScheduleService {
	
	@Autowired
	private TimerScheduleDao timerScheduleDao;
	
	public List<TimerSchedule> getTimerScheduleList() {
		return timerScheduleDao.getTimerScheduleList();
	}
	
	public Map<String, List<TimerChartData>> getTimerChartData() {
		
		List<TimerSchedule> timerScheduleList = getTimerScheduleList();
		
		// 스케쥴이 없으면 챠트 데이터도 없음
		if (timerScheduleList == null || timerScheduleList.isEmpty() == true) {
			return null;
		}

		Map<String, List<TimerChartData>> chartDataMap = new HashMap<>();
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date chartDate = null;
		
		try {
			chartDate = timeFormat.parse("00:00");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		long chartTime = chartDate.getTime();

		// 스케쥴이 하나만 등록 되어있는 경우
		if (timerScheduleList.size() == 1) {
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			
			for (TimerScheduleDetail timerDetail : timerScheduleList.get(0).getTimerScheduleDetailList()) {
				
				List<TimerChartData> timerChartDataList = new ArrayList<>();
				
				for (int i = 0; i < 24; i++) {
					cal.set(Calendar.HOUR, i);
					
					TimerChartData chartData = new TimerChartData();
					chartData.setColor(timerDetail.getDimGroup().getColor());
					chartData.setTime(timeFormat.format(cal.getTime()));
					chartData.setValue(timerDetail.getSetValue());
					chartData.setName(timerDetail.getDimGroup().getDimName());
					
					timerChartDataList.add(chartData);
				}
				
				chartDataMap.put(timerDetail.getDimGroup().getDimId(), timerChartDataList);
			}
			
			return chartDataMap;
		}
		
		// 시간 순 정렬
		timerScheduleList.sort(new Comparator<TimerSchedule>() {

			@Override
			public int compare(TimerSchedule o1, TimerSchedule o2) {
				
				if (o1.getScheduleTime().getTime() < o2.getScheduleTime().getTime()) {
					return -1;
				} else if (o1.getScheduleTime().getTime() == o2.getScheduleTime().getTime()) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		
		long millisOfDay = 86400000;
		long millisOfHour = 3600000;
		
		TimerSchedule prevSchedule = null;
		TimerSchedule nextSchedule = null;
		
		int prevScheduleIndex = timerScheduleList.size() - 1;
		int nextScheduleIndex = 0;
		
		for (int i = 0; i < 24; i++) {
			
			// i시간 기준으로 이전 설정시간 검색
		
			prevSchedule = timerScheduleList.get(prevScheduleIndex);
			nextSchedule = timerScheduleList.get(nextScheduleIndex);
			
			long prevTime = prevSchedule.getScheduleTime().getTime();
			long nextTime = nextSchedule.getScheduleTime().getTime();
			
			log.info("prevTime : {}", prevTime);
			log.info("nextTime : {}", nextTime);
			log.info("chartTime : {}", chartTime);
			
			// 차트 시간이 다음 스케쥴 시간보다 크다면 다음으로
			if (nextTime < chartTime) {
				i--;
				prevScheduleIndex++;
				nextScheduleIndex++;
				
				if (nextScheduleIndex >= timerScheduleList.size()) {
					prevScheduleIndex = timerScheduleList.size() - 1;
					nextScheduleIndex = 0;
				}
				
				if (prevScheduleIndex >= timerScheduleList.size()) {
					prevScheduleIndex = 0;
				}
				
				log.info("prevScheduleIndex : {}", prevScheduleIndex);
				log.info("nextScheduleIndex : {}", nextScheduleIndex);
				
				continue;
			}
			
			chartDate = new Date(chartTime);
			
			if (prevTime > nextTime) {
				nextTime += millisOfDay;
			}

			long termOfSchedule = nextTime - prevTime;
			double scale = (double) (chartTime - prevTime) / (double) termOfSchedule;
			
			for (TimerScheduleDetail prevDetail : prevSchedule.getTimerScheduleDetailList()) {
				for (TimerScheduleDetail nextDetail : nextSchedule.getTimerScheduleDetailList()) {
					if (prevDetail.getDimGroup().getDimId().equals(nextDetail.getDimGroup().getDimId()) == true) {
						
						DimGroup dimGroup = prevDetail.getDimGroup();
						
						if (chartDataMap.get(dimGroup.getDimId()) == null) {
							chartDataMap.put(dimGroup.getDimId(), new ArrayList<>());
						}
						
						TimerChartData chartData = new TimerChartData();
						chartData.setColor(dimGroup.getColor());
						chartData.setTime(timeFormat.format(chartDate));
						chartData.setName(dimGroup.getDimName());
						
						int increaseValue = (int) (scale * (double) (nextDetail.getSetValue() - prevDetail.getSetValue()));
						
						log.info("prevValue : {}", prevDetail.getSetValue());
						log.info("nextValue : {}", nextDetail.getSetValue());
						log.info("increaseValue : {}", increaseValue);
						
						chartData.setValue(prevDetail.getSetValue() + increaseValue);
						
						chartDataMap.get(dimGroup.getDimId()).add(chartData);
						
						break;
					}
				}
			}
			
			chartTime += millisOfHour;
			
		}

		return chartDataMap;
	}
	
	@Transactional
	public void updateTimerSchedule(TimerSchedule timerSchedule) {
		
		log.info(ToStringBuilder.reflectionToString(timerSchedule, ToStringStyle.MULTI_LINE_STYLE));
		
		List<TimerSchedule> timerScheduleList = timerSchedule.getTimerScheduleList();

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		// 시간과 분으로 java.util.Date값 설정
		for (TimerSchedule schedule : timerScheduleList) {
			try {
				Date date = timeFormat.parse(Integer.toString(schedule.getHour()) + ":" + Integer.toString(schedule.getMinute()));
				schedule.setScheduleTime(date);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		// 다시 돌면서 중복되는 시간이 있는지 검사
		for (int i = 0; i < timerScheduleList.size() - 1; i++) {
			for (int j = i + 1; j < timerScheduleList.size(); j++) {
				if (timerScheduleList.get(i).getScheduleTime().getTime() ==
						timerScheduleList.get(j).getScheduleTime().getTime()) {
					throw new RuntimeException("동일한 시간에 두가지 설정값이 존재합니다. (" + timeFormat.format(timerScheduleList.get(i).getScheduleTime()) + ")");
				}
			}
		}

		// 기존 설정값 삭제
		timerScheduleDao.removeTimerScheduleAll();
		
		// 기존 상세 설정값 삭제
		timerScheduleDao.removeTimerScheduleDetailAll();
		
		// 새로운 설정값 저장
		timerScheduleDao.saveTimerSchedule(timerScheduleList);
		
		// 상세 설정값 저장
		for (TimerSchedule schedule : timerScheduleList) {
			
			// 상세 설정값에 타임 스케쥴 연결
			for (TimerScheduleDetail detail : schedule.getTimerScheduleDetailList()) {
				detail.setTimerSchedule(schedule);
				log.info("detail\n{}", ToStringBuilder.reflectionToString(detail, ToStringStyle.MULTI_LINE_STYLE));
			}
			
			timerScheduleDao.saveTimerScheduleDetail(schedule.getTimerScheduleDetailList());
		}
	}
	
}
