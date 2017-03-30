package com.cashyalla.home.led.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.TimerSchedule;

public interface TimerScheduleRepository extends JpaRepository<TimerSchedule, Integer> {

//	@Query("SELECT ts FROM TimerSchedule ts JOIN FETCH ts.timerScheduleDetailList tsd JOIN FETCH tsd.dimGroup")
	List<TimerSchedule> findAllByOrderByHourAscMinuteAsc();

//	TimerSchedule findByHourAndMinute(Integer hour, Integer minute);
	
}
