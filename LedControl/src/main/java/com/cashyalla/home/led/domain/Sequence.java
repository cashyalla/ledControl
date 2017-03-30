package com.cashyalla.home.led.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Sequence {

	@Id
	private String seqType;

	@Column(updatable = false)
	private String seqPrefix;

	private long seqCurVal;

	@Column(updatable = false)
	private int seqLength;

	private String expl;

}
