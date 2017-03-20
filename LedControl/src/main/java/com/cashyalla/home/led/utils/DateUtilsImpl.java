package com.cashyalla.home.led.utils;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtilsImpl implements DateUtils {

	@Override
	public Date getNowDate() {
		return new Date();
	}
	
	@Override
	public Timestamp getNowTimestamp() {
		return new Timestamp(getNowDate().getTime());
	}
	
}
