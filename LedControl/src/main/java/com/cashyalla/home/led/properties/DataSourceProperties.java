package com.cashyalla.home.led.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "datasource")
public class DataSourceProperties {

	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private int initialSize;
	private int maxActive;
	private int maxIdle;
	private long maxWait;
	private boolean removeAbandoned;
	private int removeAbandonedTimeout;
	private boolean logAbandoned;
	private int maxOpenPreparedStatements;
	private boolean poolPreparedStatements;
	private boolean testOnBorrow;
	private String validationQuery;
	private boolean defaultAutoCommit;

}
