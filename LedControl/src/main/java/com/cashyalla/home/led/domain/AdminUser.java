package com.cashyalla.home.led.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
public class AdminUser {

	@Id
	@Getter @Setter
	private String adminId;

	@Getter @Setter
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Getter @Setter
	private Date crtDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Getter @Setter
	private Date loginDate;

}
