package com.cashyalla.home.led.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cashyalla.home.led.domain.TimerChartData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TimerScheduleServiceTests {
	
	@Autowired
	private TimerScheduleService timerScheduleService;
	
	@Test
	public void getTimerChartDataTest() {
		Map<String, List<TimerChartData>> map = timerScheduleService.getTimerChartData();
		
		for (String dimId : map.keySet()) {
			log.info("dimId : {}", dimId);
			List<TimerChartData> list = map.get(dimId);
			for (TimerChartData timerChartData : list) {
				log.info("\nchart data\n{}", ToStringBuilder.reflectionToString(timerChartData, ToStringStyle.MULTI_LINE_STYLE));
			}
		}
	}
	
	
}
