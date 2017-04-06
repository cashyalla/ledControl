package com.cashyalla.home.led.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cashyalla.home.led.dao.repository.TimerScheduleDetailRepository;
import com.cashyalla.home.led.dao.repository.TimerScheduleRepository;
import com.cashyalla.home.led.domain.TimerSchedule;
import com.cashyalla.home.led.domain.TimerScheduleDetail;

@Repository
public class TimerScheduleDaoJpa implements TimerScheduleDao {

	@Autowired
	private TimerScheduleRepository timerScheduleRepository;
	
	@Autowired
	private TimerScheduleDetailRepository timerScheduleDetailRepository;
	
	@Override
	public TimerSchedule getTimerSchedule(TimerSchedule timerSchedule) {
		return timerScheduleRepository.findOne(timerSchedule.getSeq());
	}
	
	@Override
	public List<TimerSchedule> getTimerScheduleList() {
		return timerScheduleRepository.findAllByOrderByHourAscMinuteAsc();
	}
	
	@Override
	public void saveTimerSchedule(List<TimerSchedule> timerScheduleList) {
		timerScheduleRepository.save(timerScheduleList);
	}
	
	@Override
	public void removeTimerScheduleAll() {
		timerScheduleRepository.deleteAll();
		timerScheduleRepository.flush();
	}
	
	@Override
	public void removeTimerSchedule(TimerSchedule timerSchedule) {
		timerScheduleRepository.delete(timerSchedule);
		timerScheduleRepository.flush();
	}
	
	@Override
	public void saveTimerScheduleDetail(List<TimerScheduleDetail> timerScheduleDetailList) {
		timerScheduleDetailRepository.save(timerScheduleDetailList);
	}
	
	@Override
	public void removeTimerScheduleDetailAll() {
		timerScheduleDetailRepository.deleteAll();
		timerScheduleDetailRepository.flush();
	}
	
}
