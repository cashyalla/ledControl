package com.cashyalla.home.led.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class LedMode {

	@Id
	private String modeId;

	private String modeName;

	private String description;

	@OneToMany(mappedBy = "ledMode")
	private List<LedModeSettings> ledModeSettingsList;

	public String getModeId() {
		return modeId;
	}

	public void setModeId(String modeId) {
		this.modeId = modeId;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LedModeSettings> getLedModeSettingsList() {
		return ledModeSettingsList;
	}

	public void setLedModeSettingsList(List<LedModeSettings> ledModeSettingsList) {
		this.ledModeSettingsList = ledModeSettingsList;
	}

}
