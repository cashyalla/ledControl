package com.cashyalla.home.led.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
public class TimerScheduleDetail {

	@Id
	@GeneratedValue
	@Getter @Setter
	private int seq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scheduleSeq", referencedColumnName = "seq")
	@JsonIgnore
	@Getter @Setter
	private TimerSchedule timerSchedule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dimId", referencedColumnName = "dimId")
	@Getter @Setter
	private DimGroup dimGroup;

	@Getter @Setter
	private int setValue;

}
