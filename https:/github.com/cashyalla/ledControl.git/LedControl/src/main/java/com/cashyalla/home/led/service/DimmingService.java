package com.cashyalla.home.led.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cashyalla.home.led.domain.DimGroup;

@Service
public class DimmingService {

	private Object monitor = new Object();
	
	public void setBrightness() {
		
	}
	
	/**
	 * 조명 모드를 설정하기 전에 테스트로 잠시 설정값으로 변경한다.
	 * @param dimGroupList
	 */
	public void setTest(List<DimGroup> dimGroupList) {
		
	}
	
}
