package com.cashyalla.home.led.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
public class DimGroup {

	@Id
	@Getter @Setter
	private String dimId;

	@Getter @Setter
	private String dimName;

	@Getter @Setter
	private String description;

	@Getter @Setter
	private String color;

	@Getter @Setter
	private String useYn;

	@OneToMany(mappedBy = "dimGroup", fetch = FetchType.LAZY)
	@JsonIgnore
	@Getter @Setter
	private List<DimDetail> dimDetails;

}
