package com.cashyalla.home.led.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashyalla.home.led.domain.CurrentBrightness;
import com.cashyalla.home.led.domain.DimDetail;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.GpioPwm;
import com.cashyalla.home.led.domain.LedMode;
import com.cashyalla.home.led.domain.LedModeSettings;
import com.cashyalla.home.led.properties.ConfigProperties;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

@Service
public class DimmingService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private LedControlService ledControlService;
	
	private static Object monitor = new Object();

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
		
		changeBrightness(dimGroupValueMap, targetValueMap, configProperties.getTermChangeBrightness());
	}
	
	public void changeBrightness(Map<DimGroup, Integer> srcValue, Map<DimGroup, Integer> tgtValue, long interval) {
		synchronized (monitor) {
			// 현재의 시간을 밀리세컨드로 조회
			Long startTime = System.currentTimeMillis();
			Long endTime = startTime + interval;

			Long prevTime = startTime;
			
			List<GpioPwm> pwmList = new ArrayList<>();

			while (prevTime <= endTime) {
				
				pwmList.clear();
				
				for (DimGroup dimGroup : tgtValue.keySet()) {
					
					List<DimDetail> dimDetailList = dimGroup.getDimDetails();
					
					int sourceValue = srcValue.get(dimGroup) == null? 0 : srcValue.get(dimGroup);
					int targetValue = tgtValue.get(dimGroup) == null? 0 : tgtValue.get(dimGroup);
					
					int termOfValue = (int) ((double) (targetValue - sourceValue) * ((double) (prevTime - startTime) / (double) configProperties.getTermChangeBrightness()));
					
					for (DimDetail dimDetail : dimDetailList) {
						GpioPwm pwm = new GpioPwm();
						pwm.setPinNumber(dimDetail.getGpioPinInfo().getPinNumber());
						pwm.setValue(sourceValue + termOfValue);
						
						pwmList.add(pwm);
					}
				}
				
				setBrightness(pwmList);
				
				prevTime = System.currentTimeMillis();
				
				// 너무 자주 돌지 않도록 조절....
				try {Thread.sleep(10);} catch (InterruptedException e) {}
			}
			
			// 다 끝났으면 타겟 밝기로 다시 한번 설정함
			pwmList.clear();
			
			for (DimGroup dimGroup : tgtValue.keySet()) {
				List<DimDetail> dimDetailList = dimGroup.getDimDetails();
				
				for (DimDetail dimDetail : dimDetailList) {
					GpioPwm pwm = new GpioPwm();
					pwm.setPinNumber(dimDetail.getGpioPinInfo().getPinNumber());
					pwm.setValue(tgtValue.get(dimGroup));
					
					pwmList.add(pwm);
				}
			}
			
			setBrightness(pwmList);
			
			// 현재 밝기값 저장
			List<CurrentBrightness> currentBrightnessList = new ArrayList<>();
			
			for (DimGroup dimGroup : tgtValue.keySet()) {
				CurrentBrightness currentBrightness = new CurrentBrightness();
				currentBrightness.setDimGroup(dimGroup);
				currentBrightness.setValue(tgtValue.get(dimGroup));
				
				currentBrightnessList.add(currentBrightness);
			}
			
			ledControlService.updateCurrentBrightness(currentBrightnessList);
		}
	}
	
	/**
	 * 현재 피밍 그룹의 밝기를 조정함. 
	 * @param brightness
	 */
	@Transactional
	public void changeBrightness(CurrentBrightness brightness) {
		List<CurrentBrightness> currentBrightnessList = ledControlService.getCurrentBrightness();
		List<GpioPwm> pwmList = new ArrayList<>();
		
		for (CurrentBrightness currentBrightness : currentBrightnessList) {
			
			int value;
			
			if (currentBrightness.getDimGroup().getDimId().equals(brightness.getDimGroup().getDimId()) == true) {
				value = brightness.getValue();
			} else {
				value = currentBrightness.getValue();
			}
			
			currentBrightness.setValue(value);
			
			for (DimDetail dimDetail : currentBrightness.getDimGroup().getDimDetails()) {
				GpioPwm pwm = new GpioPwm();
				pwm.setPinNumber(dimDetail.getGpioPinInfo().getPinNumber());
				pwm.setValue(value);
				
				pwmList.add(pwm);
			}
			
		}
		
		
		// 밝기 조정
		setBrightness(pwmList);
		
	}
	
	public void setBrightness(List<GpioPwm> pwmList) {
		synchronized (monitor) {
			setPwm(pwmList);
		}
	}
	
	private void setPwm(List<GpioPwm> pwmList) {
//		Gpio.wiringPiSetup();
		
		for (GpioPwm pwm : pwmList) {
//			logger.info("\nPWM\n{}", ToStringBuilder.reflectionToString(pwm, ToStringStyle.MULTI_LINE_STYLE));
//			SoftPwm.softPwmCreate(pwm.getPinNumber(), pwm.getValue(), configProperties.getRangeOfPwm());
		}
	}
	
	
}
