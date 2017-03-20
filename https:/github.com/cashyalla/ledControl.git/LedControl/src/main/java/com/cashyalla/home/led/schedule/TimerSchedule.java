package com.cashyalla.home.led.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cashyalla.home.led.domain.CurrentLedMode;
import com.cashyalla.home.led.service.LedControlService;

@Component
public class TimerSchedule {

	@Autowired
	private LedControlService ledControlService;
	
	@Scheduled(cron = "0 */1 * * * *")
	public void ledTimer() {
		List<CurrentLedMode> currentModeList = ledControlService.getCurrentLedMode();
		
		// 설정된 모드가 없다면 종료
		if (currentModeList == null && currentModeList.isEmpty() == true) {
			return;
		}
	}
	
}
