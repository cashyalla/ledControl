package com.cashyalla.home.led.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class CurrentBrightness {

	@Id
	@GeneratedValue
	@Getter @Setter
	private int seq;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dimId", referencedColumnName = "dimId")
	@Getter @Setter
	private DimGroup dimGroup;

	@Getter @Setter
	private int value;

}
