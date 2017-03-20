package com.cashyalla.home.led.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class CurrentBrightness {

	@Id
	@GeneratedValue
	private int seq;

	@OneToOne
	@JoinColumn(name = "dimId", referencedColumnName = "dimId")
	private DimGroup dimGroup;

	private int value;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public DimGroup getDimGroup() {
		return dimGroup;
	}

	public void setDimGroup(DimGroup dimGroup) {
		this.dimGroup = dimGroup;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
