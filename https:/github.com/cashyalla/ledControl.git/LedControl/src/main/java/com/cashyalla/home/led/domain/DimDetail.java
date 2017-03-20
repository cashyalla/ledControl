package com.cashyalla.home.led.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DimDetail {

	@Id
	@GeneratedValue
	private Integer seq;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dimId", referencedColumnName = "dimId", updatable = false)
	private DimGroup dimGroup;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pinSeq")
	private GpioPinInfo gpioPinInfo;

	private String useYn;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public DimGroup getDimGroup() {
		return dimGroup;
	}

	public void setDimGroup(DimGroup dimGroup) {
		this.dimGroup = dimGroup;
	}

	public GpioPinInfo getGpioPinInfo() {
		return gpioPinInfo;
	}

	public void setGpioPinInfo(GpioPinInfo gpioPinInfo) {
		this.gpioPinInfo = gpioPinInfo;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

}
