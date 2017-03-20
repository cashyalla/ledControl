package com.cashyalla.home.led.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sequence {

	@Id
	private String seqType;

	@Column(updatable = false)
	private String seqPrefix;

	private long seqCurVal;

	@Column(updatable = false)
	private int seqLength;

	private String expl;

	public String getSeqType() {
		return seqType;
	}

	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}

	public String getSeqPrefix() {
		return seqPrefix;
	}

	public void setSeqPrefix(String seqPrefix) {
		this.seqPrefix = seqPrefix;
	}

	public long getSeqCurVal() {
		return seqCurVal;
	}

	public void setSeqCurVal(long seqCurVal) {
		this.seqCurVal = seqCurVal;
	}

	public int getSeqLength() {
		return seqLength;
	}

	public void setSeqLength(int seqLength) {
		this.seqLength = seqLength;
	}

	public String getExpl() {
		return expl;
	}

	public void setExpl(String expl) {
		this.expl = expl;
	}

}
