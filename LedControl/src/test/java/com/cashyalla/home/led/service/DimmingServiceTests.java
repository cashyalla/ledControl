package com.cashyalla.home.led.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cashyalla.home.led.domain.LedMode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DimmingServiceTests {

	@Autowired
	private DimmingService dimmingService;
	
	@Test
	public void changeBrightnessTest() {
		LedMode ledMode = new LedMode();
		ledMode.setModeId("MOD0000010");
		
		dimmingService.changeBrightness(ledMode);
	}
	
}
