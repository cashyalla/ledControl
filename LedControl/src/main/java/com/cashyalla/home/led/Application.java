package com.cashyalla.home.led;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cashyalla.home.led.properties.DataSourceProperties;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

@SpringBootApplication
@EnableScheduling
@PropertySource({"classpath:datasource.properties", "classpath:config.properties"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public DataSource dataSource(DataSourceProperties jdbcProperties) {
		
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(jdbcProperties.getDriverClassName());
		basicDataSource.setUrl(jdbcProperties.getUrl());
		basicDataSource.setUsername(jdbcProperties.getUsername());
		basicDataSource.setPassword(jdbcProperties.getPassword());
		basicDataSource.setInitialSize(jdbcProperties.getInitialSize());
		basicDataSource.setMaxTotal(jdbcProperties.getMaxActive());
		basicDataSource.setMaxIdle(jdbcProperties.getMaxIdle());
		basicDataSource.setMaxWaitMillis(jdbcProperties.getMaxWait());
		basicDataSource.setRemoveAbandonedOnBorrow(jdbcProperties.isRemoveAbandoned());
		basicDataSource.setRemoveAbandonedOnMaintenance(jdbcProperties.isRemoveAbandoned());
		basicDataSource.setRemoveAbandonedTimeout(jdbcProperties.getRemoveAbandonedTimeout());
		basicDataSource.setLogAbandoned(jdbcProperties.isLogAbandoned());
		basicDataSource.setMaxOpenPreparedStatements(jdbcProperties.getMaxOpenPreparedStatements());
		basicDataSource.setPoolPreparedStatements(jdbcProperties.isPoolPreparedStatements());
		basicDataSource.setTestOnBorrow(jdbcProperties.isTestOnBorrow());
		basicDataSource.setValidationQuery(jdbcProperties.getValidationQuery());
		basicDataSource.setDefaultAutoCommit(jdbcProperties.isDefaultAutoCommit());
		
		Log4jdbcProxyDataSource dataSource = new Log4jdbcProxyDataSource(basicDataSource);
		
		Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
		formatter.setLoggingType(LoggingType.MULTI_LINE);;
		formatter.setSqlPrefix("\nSQL=>\n");
		
		dataSource.setLogFormatter(formatter);
		
		return dataSource;
	}
}
