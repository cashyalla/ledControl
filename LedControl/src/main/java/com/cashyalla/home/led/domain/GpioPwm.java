package com.cashyalla.home.led.domain;

import lombok.Getter;
import lombok.Setter;

public class GpioPwm {

	@Getter @Setter
	private int pinNumber;

	@Getter @Setter
	private int value;
}
