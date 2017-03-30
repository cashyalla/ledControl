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
public class LedModeSettings {

	@Id
	@GeneratedValue
	@Getter @Setter
	private int seq;

	@ManyToOne
	@JoinColumn(name = "modeId", referencedColumnName = "modeId")
	@JsonIgnore
	@Getter @Setter
	private LedMode ledMode;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dimId", referencedColumnName = "dimId")
	@Getter @Setter
	private DimGroup dimGroup;

	@Getter @Setter
	private int setValue;

}
