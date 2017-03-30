package com.cashyalla.home.led.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DimDetail {

	@Id
	@GeneratedValue
	@Getter @Setter
	private Integer seq;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dimId", referencedColumnName = "dimId", updatable = false)
	@Getter @Setter
	private DimGroup dimGroup;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pinSeq")
	@Getter @Setter
	private GpioPinInfo gpioPinInfo;

	@Getter @Setter
	private String useYn;

}
