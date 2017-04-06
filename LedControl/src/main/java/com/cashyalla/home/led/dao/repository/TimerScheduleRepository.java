package com.cashyalla.home.led.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.TimerSchedule;

public interface TimerScheduleRepository extends JpaRepository<TimerSchedule, Integer> {

	List<TimerSchedule> findAllByOrderByHourAscMinuteAsc();
	
}
