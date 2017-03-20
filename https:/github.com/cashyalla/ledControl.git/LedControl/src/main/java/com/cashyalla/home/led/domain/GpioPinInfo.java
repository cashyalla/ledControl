package com.cashyalla.home.led.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class GpioPinInfo {

	@Id
	@GeneratedValue
	private Integer seq;

	private int pinNumber;

	private String description;

	@Transient
	private List<GpioPinInfo> gpioPinInfos;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<GpioPinInfo> getGpioPinInfos() {
		return gpioPinInfos;
	}

	public void setGpioPinInfos(List<GpioPinInfo> gpioPinInfos) {
		this.gpioPinInfos = gpioPinInfos;
	}

}
