package com.cashyalla.home.led.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.LedMode;

public interface LedModeRepository extends JpaRepository<LedMode, String> {

	List<LedMode> findByDisplayYn(String displayYn);
	
}
