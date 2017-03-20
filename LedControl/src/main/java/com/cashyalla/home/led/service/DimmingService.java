package com.cashyalla.home.led.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashyalla.home.led.domain.CurrentBrightness;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.LedMode;
import com.cashyalla.home.led.domain.LedModeSettings;

@Service
public class DimmingService {

	@Autowired
	private LedControlService ledControlService;
	
	public void setBrightness() {
		
	}
	
	/**
	 * 조명 모드를 설정하기 전에 테스트로 잠시 설정값으로 변경한다.
	 * @param dimGroupList
	 */
	public void setTest(List<DimGroup> dimGroupList) {
		
	}
	
	@Transactional
	public void changeBrightness(LedMode ledMode) {
		// 현재 설정값 가져오기
		List<CurrentBrightness> currentBrightnessList = ledControlService.getCurrentBrightness();
		
		// 저장 되어있는 디밍 그룹 리스트 조회
		List<DimGroup> dimGroupList = ledControlService.getDimGroupListInUse();
		
		// 디밍 그룹 별 설정값 저장
		Map<DimGroup, Integer> dimGroupValueMap = new HashMap<>();
		
		// 목표 설정값
		Map<DimGroup, Integer> targetValueMap = new HashMap<>();
		
		for (DimGroup dimGroup : dimGroupList) {
			dimGroupValueMap.put(dimGroup, 0);
		}

		// 현재 설정값 맵에 저장
		for (CurrentBrightness currentBrightness : currentBrightnessList) {
			dimGroupValueMap.put(currentBrightness.getDimGroup(), currentBrightness.getValue());
		}
		
		LedMode targetLedMode = ledControlService.getLedMode(ledMode);
		
		for (LedModeSettings ledModeSettings : targetLedMode.getLedModeSettingsList()) {
			targetValueMap.put(ledModeSettings.getDimGroup(), ledModeSettings.getSetValue());
		}
	}
	
	public synchronized void changeBrightness(Map<DimGroup, Integer> srcValue, Map<DimGroup, Integer> tgtValue) {

	}
}
