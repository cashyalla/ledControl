package com.cashyalla.home.led.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
public class GpioPinInfo {

	@Id
	@GeneratedValue
	@Getter @Setter
	private Integer seq;

	@Getter @Setter
	private int pinNumber;

	@Getter @Setter
	private String description;

	@Transient
	@Getter @Setter
	private List<GpioPinInfo> gpioPinInfos;

}
