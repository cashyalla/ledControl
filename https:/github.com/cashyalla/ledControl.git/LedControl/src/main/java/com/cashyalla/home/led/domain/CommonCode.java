package com.cashyalla.home.led.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CommonCode {

	@Id
	private String fullCode;

	private String codeId;

	private String codeGrp;

	private String codeName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date crtDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updDate;

	public String getFullCode() {
		return fullCode;
	}

	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeGrp() {
		return codeGrp;
	}

	public void setCodeGrp(String codeGrp) {
		this.codeGrp = codeGrp;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

}
