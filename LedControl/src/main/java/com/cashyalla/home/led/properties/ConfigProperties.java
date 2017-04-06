package com.cashyalla.home.led.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties
public class ConfigProperties {

	private String seqMode;

	private String seqDim;

	private String modeSchedule;

	private String modeManual;

	private String modeCycle;

	private String modeTest;

	private Integer termChangeBrightness;

	private Integer rangeOfPwm;

}
