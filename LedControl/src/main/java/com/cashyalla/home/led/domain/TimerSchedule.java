package com.cashyalla.home.led.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EqualsAndHashCode(exclude = {"timerScheduleDetailList", "timerScheduleList"})
public class TimerSchedule {

	@Id
	@GeneratedValue
	@Getter @Setter
	private Integer seq;

	@Getter @Setter
	private Integer hour;

	@Getter @Setter
	private Integer minute;

	@Temporal(TemporalType.TIME)
	@Getter @Setter
	private Date scheduleTime;

	@OneToMany(mappedBy = "timerSchedule", fetch = FetchType.LAZY)
	@Getter @Setter
	List<TimerScheduleDetail> timerScheduleDetailList;

	@Transient
	@Getter @Setter
	private List<TimerSchedule> timerScheduleList;

}
