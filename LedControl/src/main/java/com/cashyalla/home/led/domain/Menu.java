package com.cashyalla.home.led.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Menu {

	@Id
	@Getter @Setter
	private String menuId;

	@Getter @Setter
	private String menuName;

	@Getter @Setter
	private String menuUrl;

	@Getter @Setter
	private int sortNo;

	@Getter @Setter
	private String useYn;
	
	@Getter @Setter
	private String icon;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	@Getter @Setter
	private Date crtDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Getter @Setter
	private Date updDate;

	@Transient
	@Getter @Setter
	private List<Menu> menus;

}
