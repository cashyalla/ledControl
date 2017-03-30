package com.cashyalla.home.led.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class CurrentLedMode {

	@Id
	@Getter @Setter
	private String modeId;

}
