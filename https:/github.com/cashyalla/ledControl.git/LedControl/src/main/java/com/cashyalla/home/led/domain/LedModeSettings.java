package com.cashyalla.home.led.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LedModeSettings {

	@Id
	@GeneratedValue
	private int seq;

	@ManyToOne
	@JoinColumn(name = "modeId", referencedColumnName = "modeId")
	@JsonIgnore
	private LedMode ledMode;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dimId", referencedColumnName = "dimId")
	private DimGroup dimGroup;

	private int setValue;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public LedMode getLedMode() {
		return ledMode;
	}

	public void setLedMode(LedMode ledMode) {
		this.ledMode = ledMode;
	}

	public DimGroup getDimGroup() {
		return dimGroup;
	}

	public void setDimGroup(DimGroup dimGroup) {
		this.dimGroup = dimGroup;
	}

	public int getSetValue() {
		return setValue;
	}

	public void setSetValue(int setValue) {
		this.setValue = setValue;
	}

}
