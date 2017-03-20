package com.cashyalla.home.led.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.CurrentLedMode;

public interface CurrentLedModeRepository extends JpaRepository<CurrentLedMode, String> {

}
