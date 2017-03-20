package com.cashyalla.home.led.dao;

import java.util.List;

import com.cashyalla.home.led.domain.CurrentBrightness;
import com.cashyalla.home.led.domain.CurrentLedMode;
import com.cashyalla.home.led.domain.DimDetail;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.GpioPinInfo;
import com.cashyalla.home.led.domain.LedMode;
import com.cashyalla.home.led.domain.LedModeSettings;

public interface LedControlDao {
	
	void saveGpioPinInfo(GpioPinInfo gpioPinInfo);
	
	List<GpioPinInfo> gpioPinInfoList();
	
	List<GpioPinInfo> getPinInfoListNotUsed();

	GpioPinInfo getGpioPinInfo(GpioPinInfo gpioPinInfo);
	
	GpioPinInfo getGpioPinInfoByPinNumber(GpioPinInfo gpioPinInfo);

	void removeGpioPinInfo(GpioPinInfo gpioPinInfo);

	List<DimGroup> getDimGroupList();

	List<DimGroup> getDimGroupListInUse();

	DimGroup getDimGroupInfo(DimGroup dimGroup);

	void saveDimGroup(DimGroup dimGroup);

	void removeDimGroup(DimGroup dimGroup);

	void removeDimGroupChildren(DimGroup dimGroup);

	void saveDimDetail(DimDetail dimDetail);

	DimDetail getDimDetail(DimDetail dimDetail);
	
	DimDetail getDimDetailByPinSeq(DimDetail dimDetail);

	void removeDimDetail(DimDetail dimDetail);

	List<LedMode> getLedModeList();
	
	LedMode getLedMode(LedMode ledMode);

	LedMode saveLedMode(LedMode ledMode);

	void removeLedMode(LedMode ledMode);

	List<LedModeSettings> getLedModeSettingsByModeId(LedMode ledMode);

	void saveLedModeSettings(List<LedModeSettings> ledModeSettingsList);

	void removeLedModeSettings(LedModeSettings ledModeSettings);
	
	void removeLedModeSettingsByModeId(LedMode ledMode);

	List<CurrentLedMode> getCurrentLedMode();

	List<CurrentBrightness> getCurrentBrightness();

}