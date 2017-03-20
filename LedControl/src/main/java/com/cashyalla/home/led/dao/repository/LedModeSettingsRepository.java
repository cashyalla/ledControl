package com.cashyalla.home.led.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.LedMode;
import com.cashyalla.home.led.domain.LedModeSettings;

public interface LedModeSettingsRepository extends JpaRepository<LedModeSettings, Integer> {

	List<LedModeSettings> findByLedMode(LedMode ledMode);
	
	void removeByLedMode(LedMode ledMode);
	
}
