package com.cashyalla.home.led.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CurrentLedMode {

	@Id
	private String modeId;

	public String getModeId() {
		return modeId;
	}

	public void setModeId(String modeId) {
		this.modeId = modeId;
	}

}
