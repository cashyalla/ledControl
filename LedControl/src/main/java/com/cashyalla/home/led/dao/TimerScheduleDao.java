package com.cashyalla.home.led.dao;

import java.util.List;

import com.cashyalla.home.led.domain.TimerSchedule;
import com.cashyalla.home.led.domain.TimerScheduleDetail;

public interface TimerScheduleDao {

	List<TimerSchedule> getTimerScheduleList();

	void saveTimerSchedule(List<TimerSchedule> timerScheduleList);

	void removeTimerScheduleAll();

	void saveTimerScheduleDetail(List<TimerScheduleDetail> timerScheduleDetailList);

	void removeTimerScheduleDetailAll();

}
