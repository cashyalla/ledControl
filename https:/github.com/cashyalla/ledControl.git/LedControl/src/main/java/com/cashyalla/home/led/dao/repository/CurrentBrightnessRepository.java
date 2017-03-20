package com.cashyalla.home.led.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.CurrentBrightness;

public interface CurrentBrightnessRepository extends JpaRepository<CurrentBrightness, Integer> {

}