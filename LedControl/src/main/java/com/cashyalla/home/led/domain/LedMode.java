package com.cashyalla.home.led.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class LedMode {

	@Id
	@Getter @Setter
	private String modeId;

	@Getter @Setter
	private String modeName;

	@Getter @Setter
	private String description;

	@Getter @Setter
	private String displayYn;

	@OneToMany(mappedBy = "ledMode")
	@Getter @Setter
	private List<LedModeSettings> ledModeSettingsList;

}
