package com.cashyalla.home.led.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ConfigProperties {

	private String seqMode;

	private String seqDim;

	private String modeSchedule;

	private String modeManual;

	private String modeCycle;

	public String getSeqMode() {
		return seqMode;
	}

	public void setSeqMode(String seqMode) {
		this.seqMode = seqMode;
	}

	public String getSeqDim() {
		return seqDim;
	}

	public void setSeqDim(String seqDim) {
		this.seqDim = seqDim;
	}

	public String getModeSchedule() {
		return modeSchedule;
	}

	public void setModeSchedule(String modeSchedule) {
		this.modeSchedule = modeSchedule;
	}

	public String getModeManual() {
		return modeManual;
	}

	public void setModeManual(String modeManual) {
		this.modeManual = modeManual;
	}

	public String getModeCycle() {
		return modeCycle;
	}

	public void setModeCycle(String modeCycle) {
		this.modeCycle = modeCycle;
	}

}
