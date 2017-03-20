package com.cashyalla.home.led.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DimGroup {

	@Id
	private String dimId;

	private String dimName;

	private String description;

	private String useYn;

	@OneToMany(mappedBy = "dimGroup")
	@JsonIgnore
	private List<DimDetail> dimDetails;

	public String getDimId() {
		return dimId;
	}

	public void setDimId(String dimId) {
		this.dimId = dimId;
	}

	public String getDimName() {
		return dimName;
	}

	public void setDimName(String dimName) {
		this.dimName = dimName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public List<DimDetail> getDimDetails() {
		return dimDetails;
	}

	public void setDimDetails(List<DimDetail> dimDetails) {
		this.dimDetails = dimDetails;
	}

}
