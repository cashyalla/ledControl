package com.cashyalla.home.led.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cashyalla.home.led.dao.repository.CurrentBrightnessRepository;
import com.cashyalla.home.led.dao.repository.CurrentLedModeRepository;
import com.cashyalla.home.led.dao.repository.DimDetailRepository;
import com.cashyalla.home.led.dao.repository.DimGroupRepository;
import com.cashyalla.home.led.dao.repository.GpioPinInfoRepository;
import com.cashyalla.home.led.dao.repository.LedModeRepository;
import com.cashyalla.home.led.dao.repository.LedModeSettingsRepository;
import com.cashyalla.home.led.domain.CurrentBrightness;
import com.cashyalla.home.led.domain.CurrentLedMode;
import com.cashyalla.home.led.domain.DimDetail;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.GpioPinInfo;
import com.cashyalla.home.led.domain.LedMode;
import com.cashyalla.home.led.domain.LedModeSettings;

@Repository
public class LedControlDaoJpa implements LedControlDao {

	@Autowired
	private GpioPinInfoRepository gpioPinInfoRepository;
	
	@Autowired
	private DimGroupRepository dimGroupRepository;
	
	@Autowired
	private DimDetailRepository dimDetailRepository;
	
	@Autowired
	private LedModeRepository ledModeRepository;
	
	@Autowired
	private LedModeSettingsRepository ledModeSettingsRepository;
	
	@Autowired
	private CurrentLedModeRepository currentLedModeRepository;
	
	@Autowired
	private CurrentBrightnessRepository currentBrightnessRepository;
	
	@Override
	public void saveGpioPinInfo(GpioPinInfo gpioPinInfo) {
		gpioPinInfoRepository.save(gpioPinInfo);
	}
	
	@Override
	public List<GpioPinInfo> gpioPinInfoList() {
		return gpioPinInfoRepository.findAll();
	}
	
	@Override
	public List<GpioPinInfo> getPinInfoListNotUsed() {
		return gpioPinInfoRepository.findNotUsedPinInfo();
	}
	
	@Override
	public GpioPinInfo getGpioPinInfo(GpioPinInfo gpioPinInfo) {
		return gpioPinInfoRepository.findOne(gpioPinInfo.getSeq());
	}
	
	@Override
	public GpioPinInfo getGpioPinInfoByPinNumber(GpioPinInfo gpioPinInfo) {
		return gpioPinInfoRepository.findByPinNumber(gpioPinInfo.getPinNumber());
	}
	
	@Override
	public void removeGpioPinInfo(GpioPinInfo gpioPinInfo) {
		gpioPinInfoRepository.delete(gpioPinInfo.getSeq());
	}

	@Override
	public List<DimGroup> getDimGroupList() {
		return dimGroupRepository.findAll();
	}
	
	@Override
	public List<DimGroup> getDimGroupListInUse() {
		return dimGroupRepository.findByUseYn("Y");
	}
	
	@Override
	public DimGroup getDimGroupInfo(DimGroup dimGroup) {
		return dimGroupRepository.findOne(dimGroup.getDimId());
	}
	
	@Override
	public void saveDimGroup(DimGroup dimGroup) {
		dimGroupRepository.save(dimGroup);
	}
	
	@Override
	public void removeDimGroup(DimGroup dimGroup) {
		dimGroupRepository.delete(dimGroup.getDimId());
	}
	
	@Override
	public void removeDimGroupChildren(DimGroup dimGroup) {
		dimDetailRepository.deleteByDimGroup(dimGroup);
	}
	
	@Override
	public void saveDimDetail(DimDetail dimDetail) {
		dimDetailRepository.save(dimDetail);
	}
	
	@Override
	public DimDetail getDimDetail(DimDetail dimDetail) {
		return dimDetailRepository.findOne(dimDetail.getSeq());
	}
	
	@Override
	public DimDetail getDimDetailByPinSeq(DimDetail dimDetail) {
		return dimDetailRepository.findByGpioPinInfo(dimDetail.getGpioPinInfo());
	}
	
	@Override
	public void removeDimDetail(DimDetail dimDetail) {
		dimDetailRepository.delete(dimDetail);
	}
	
	@Override
	public List<LedMode> getLedModeList() {
		return ledModeRepository.findAll();
	}
	
	@Override
	public LedMode getLedMode(LedMode ledMode) {
		return ledModeRepository.findOne(ledMode.getModeId());
	}
	
	@Override
	public LedMode saveLedMode(LedMode ledMode) {
		return ledModeRepository.save(ledMode);
	}
	
	@Override
	public void removeLedMode(LedMode ledMode) {
		ledModeRepository.delete(ledMode);
	}
	
	@Override
	public List<LedModeSettings> getLedModeSettingsByModeId(LedMode ledMode) {
		return ledModeSettingsRepository.findByLedMode(ledMode);
	}
	
	@Override
	public void saveLedModeSettings(List<LedModeSettings> ledModeSettingsList) {
		ledModeSettingsRepository.save(ledModeSettingsList);
	}
	
	@Override
	public void removeLedModeSettings(LedModeSettings ledModeSettings) {
		ledModeSettingsRepository.delete(ledModeSettings);
		ledModeSettingsRepository.flush();
	}
	
	@Override
	public void removeLedModeSettingsByModeId(LedMode ledMode) {
		ledModeSettingsRepository.removeByLedMode(ledMode);
		ledModeSettingsRepository.flush();
	}
	
	@Override
	public List<CurrentLedMode> getCurrentLedMode() {
		return currentLedModeRepository.findAll();
	}
	
	@Override
	public List<CurrentBrightness> getCurrentBrightness() {
		return currentBrightnessRepository.findAll();
	}
	
}
