package com.cashyalla.home.led.dao.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cashyalla.home.led.domain.LedMode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LedModeSettingsRepositoryTests {

	@Autowired
	private LedModeSettingsRepository ledModeSettingsRepository;
	
	private LedMode ledMode;
	
	@Before
	public void setUp() {
		ledMode = new LedMode();
		ledMode.setModeId("MOD0000006");
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void deleteByLedModeTest() {
		ledModeSettingsRepository.removeByLedMode(ledMode);
	}
	
}
