package com.cashyalla.home.led.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashyalla.home.led.dao.LedControlDao;
import com.cashyalla.home.led.domain.CurrentBrightness;
import com.cashyalla.home.led.domain.CurrentLedMode;
import com.cashyalla.home.led.domain.DimDetail;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.GpioPinInfo;
import com.cashyalla.home.led.domain.LedMode;
import com.cashyalla.home.led.domain.LedModeSettings;
import com.cashyalla.home.led.properties.ConfigProperties;

@Service
public class LedControlService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private LedControlDao ledControlDao;
	
	@Autowired
	private ConfigProperties configProperties;
	
	/**
	 * 전체 Gpio핀 정보 조회
	 * @return
	 */
	public List<GpioPinInfo> getGpioPinInfoList() {
		return ledControlDao.gpioPinInfoList();
	}
	
	/**
	 * 디밍 그룹에서 사용되지 않은 핀 정보만 조회
	 * @return
	 */
	public List<GpioPinInfo> getGpioPinInfoListNotUsed() {
		return ledControlDao.getPinInfoListNotUsed();
	}
	
	public GpioPinInfo getGpioPinInfo(GpioPinInfo gpioPinInfo) {
		GpioPinInfo info = ledControlDao.getGpioPinInfo(gpioPinInfo);
		
		if (info == null) {
			throw new RuntimeException("핀 정보를 조회활 수 없습니다.");
		}
		
		return info;
	}
	
	/**
	 * 신규 Gpio핀 정보 저장
	 * @param gpioPinInfo
	 */
	@Transactional
	public void registerGpioPinInfo(GpioPinInfo gpioPinInfo) {
		// 존재하는 핀 번호인지 검사
		if (ledControlDao.getGpioPinInfoByPinNumber(gpioPinInfo) != null) {
			throw new RuntimeException("이미 사용중인 핀 번호입니다. 다른 핀 번호를 선택해주세요.");
		}

		ledControlDao.saveGpioPinInfo(gpioPinInfo);
	}
	
	/**
	 * Gpio핀 정보 수정
	 * @param gpioPinInfo
	 */
	@Transactional
	public void updateGpioPinInfo(GpioPinInfo gpioPinInfo) {
		GpioPinInfo prevPinInfo = ledControlDao.getGpioPinInfo(gpioPinInfo); 
		
		// 존재하는 핀 번호인지 검사
		if (prevPinInfo == null) {
			throw new RuntimeException("존재하지 않는 핀 번호 입니다. 다시 확인하여 주세요.");
		}
		
		// 핀 번호로 핀 정보를 조회하여 중복되는 핀 번호를 사용하는지 검사
		prevPinInfo = ledControlDao.getGpioPinInfoByPinNumber(gpioPinInfo);

		if (prevPinInfo != null && gpioPinInfo.getSeq() != prevPinInfo.getSeq()) {
			throw new RuntimeException("이미 사용중인 핀 번호입니다. 다른 핀 번호를 선택해주세요.");
		}

		// 저장
		ledControlDao.saveGpioPinInfo(gpioPinInfo);
	}
	
	/**
	 * Gpio핀 정보 삭제
	 * @param gpioPinInfo
	 */
	@Transactional
	public void removeGpioPinInfo(GpioPinInfo gpioPinInfo) {
		ledControlDao.removeGpioPinInfo(gpioPinInfo);
	}
	
	/**
	 * 디밍 그룹 리스트 조회
	 * @return
	 */
	public List<DimGroup> getDimGroupList() {
		return ledControlDao.getDimGroupList();
	}

	/**
	 * 사용여부가 Y인 디밍 그룹 리스트 조회
	 * @return
	 */
	public List<DimGroup> getDimGroupListInUse() {
		return ledControlDao.getDimGroupListInUse();
	}
	
	/**
	 * DIM ID로 디밍 그룹 상세 조회
	 * @param dimGroup
	 * @return
	 */
	public DimGroup getDimGroupInfo(DimGroup dimGroup) {
		
		if (StringUtils.isEmpty(dimGroup.getDimId()) == true) {
			throw new RuntimeException("그룹 ID값을 찾을 수 없습니다.");
		}
		
		return ledControlDao.getDimGroupInfo(dimGroup);
	}
	
	/**
	 * 신규 디밍 그룹 등록
	 * @param dimGroup
	 */
	@Transactional
	public void registerDimGroup(DimGroup dimGroup) {
		
		if (StringUtils.isEmpty(dimGroup.getUseYn()) == true) {
			throw new RuntimeException("사용여부값이 비어있습니다.");
		}
		
		if (dimGroup.getUseYn().matches("[YN]") == false) {
			throw new RuntimeException("사용여부는 Y,N값만 허용됩니다.");
		}
		
		// DIM ID발급
		String dimId = sequenceService.getSequence(configProperties.getSeqDim());
		dimGroup.setDimId(dimId);
		
		ledControlDao.saveDimGroup(dimGroup);
		
	}
	
	/**
	 * 디밍 그룹 삭제, 그룹을 삭제하면 하위 상세내역도 같이 삭제된다.
	 * @param dimGroup
	 */
	@Transactional
	public void removeDimGroup(DimGroup dimGroup) {
		
		if (StringUtils.isEmpty(dimGroup.getDimId()) == true) {
			throw new RuntimeException("그룹 ID값을 찾을 수 없습니다.");
		}
		
		// 하위 DIM상세 삭제
		ledControlDao.removeDimGroupChildren(dimGroup);
		
		// DIM GROUP삭제
		ledControlDao.removeDimGroup(dimGroup);
	}
	
	/**
	 * 디밍 그룹의 내용 수정
	 * @param dimGroup
	 */
	@Transactional
	public void updateDimGroup(DimGroup dimGroup) {
		
		if (StringUtils.isEmpty(dimGroup.getDimId()) == true) {
			throw new RuntimeException("그룹 ID값을 찾을 수 없습니다.");
		}
		
		// DIM ID로 존재하는 디밍 그룹인지 조회
		DimGroup prevDimGroup = getDimGroupInfo(dimGroup);
		
		if (prevDimGroup == null) {
			throw new RuntimeException("존재하지 않는 디밍그룹 입니다.");
		}
		
		// 변경내용 저장
		ledControlDao.saveDimGroup(dimGroup);
		
	}
	
	private void checkModifyDimDetail(DimDetail dimDetail) {
		// 디밍 그룹 존재여부 체크
		if (ledControlDao.getDimGroupInfo(dimDetail.getDimGroup()) == null) {
			throw new RuntimeException("존재하지 않는 디밍그룹 입니다.");
		}
		
		// 핀 정보가 존재하는지 체크
		if (ledControlDao.getGpioPinInfo(dimDetail.getGpioPinInfo()) == null) {
			throw new RuntimeException("등록되지 않은 핀 번호입니다. 등록 후 사용해주세요.");
		}
		
		// 사용여부 값 체크
		if (StringUtils.isEmpty(dimDetail.getUseYn()) == true) {
			throw new RuntimeException("사용여부값이 비어있습니다.");
		}
		
		if (dimDetail.getUseYn().matches("[YN]") == false) {
			throw new RuntimeException("사용여부는 Y,N값만 허용됩니다.");
		}
	}
	
	/**
	 * 디밍 상세 정보 조회
	 * @param dimDetail
	 * @return
	 */
	public DimDetail getDimDetail(DimDetail dimDetail) {
		return ledControlDao.getDimDetail(dimDetail);
	}
	
	/**
	 * 디밍 상세 등록
	 * @param dimDetail
	 */
	@Transactional
	public void registerDimDetail(DimDetail dimDetail) {
		
		checkModifyDimDetail(dimDetail);
		
		// 이미 다른 디밍 그룹에서 사용하는지 체크
		DimDetail otherPinInfo = ledControlDao.getDimDetailByPinSeq(dimDetail);
		
		if (otherPinInfo != null) {
			throw new RuntimeException("이미 사용중인 핀 입니다. 다른 핀을 사용해주세요.");
		}
		
		// 저장
		ledControlDao.saveDimDetail(dimDetail);
		
	}
	
	/**
	 * 디밍 상세 정보 수정
	 * @param dimDetail
	 */
	@Transactional
	public void updateDimDetail(DimDetail dimDetail) {
		
		if (ledControlDao.getDimDetail(dimDetail) == null) {
			throw new RuntimeException("잘못된 핀 정보입니다. 다시 확인해주세요.");
		}
		
		// 이미 다른 디밍 그룹에서 사용하는지 체크
		DimDetail otherPinInfo = ledControlDao.getDimDetailByPinSeq(dimDetail);
		
		if (otherPinInfo != null && otherPinInfo.getSeq() != dimDetail.getSeq()) {
			throw new RuntimeException("이미 사용중인 핀 입니다. 다른 핀을 사용해주세요.");
		}
		
		checkModifyDimDetail(dimDetail);
		// 저장
		ledControlDao.saveDimDetail(dimDetail);
	}
	
	/**
	 * 디밍 상세 정보 삭제
	 * @param dimDetail
	 */
	@Transactional
	public void removeDimDetail(DimDetail dimDetail) {
		ledControlDao.removeDimDetail(dimDetail);
	}
	
	/**
	 * 조명 모드 리스트 조회
	 * @return
	 */
	public List<LedMode> getLedModeList() {
		return ledControlDao.getLedModeList();
	}
	
	/**
	 * 조명 모드 한개 조회
	 * @param ledMode
	 * @return
	 */
	public LedMode getLedMode(LedMode ledMode) {
		
		if (StringUtils.isEmpty(ledMode.getModeId()) == true) {
			throw new RuntimeException("조명 ID값을 찾을 수 없습니다.");
		}
		
		ledMode = ledControlDao.getLedMode(ledMode);
		
		if (ledMode == null) {
			throw new RuntimeException("잘못된 조명 모드 정보입니다. 다시 확인해주세요.");
		}
		
		return ledMode;
	}
	
	/**
	 * 신규 조명 모드 등록
	 * @param ledMode
	 */
	@Transactional
	public void registerLedMode(LedMode ledMode) {
		
		if (StringUtils.isEmpty(ledMode.getModeName()) == true) {
			throw new RuntimeException("조명 Mode의 이름을 입력해주세요.");
		}
		
		// 아이디 발급
		String modeId = sequenceService.getSequence(configProperties.getSeqMode());
		
		ledMode.setModeId(modeId);
		
		// 모드 저장
		ledControlDao.saveLedMode(ledMode);
		
		for (LedModeSettings settings : ledMode.getLedModeSettingsList()) {
			settings.setLedMode(ledMode);
		}
		
		// 상세 설정값 저장
		ledControlDao.saveLedModeSettings(ledMode.getLedModeSettingsList());
		
	}

	/**
	 * 조명 모드 정보 수정
	 * @param ledMode
	 */
	@Transactional
	public void updateLedMode(LedMode ledMode) {
		
		if (StringUtils.isEmpty(ledMode.getModeId()) == true) {
			throw new RuntimeException("조명 ID값을 찾을 수 없습니다.");
		}
		
		if (StringUtils.isEmpty(ledMode.getModeName()) == true) {
			throw new RuntimeException("조명 Mode의 이름을 입력해주세요.");
		}
		
		if (ledControlDao.getLedMode(ledMode) == null) {
			throw new RuntimeException("잘못된 조명 모드 정보입니다. 다시 확인해주세요.");
		}
		
		// 변경사항 저장
		LedMode savedMode = ledControlDao.saveLedMode(ledMode);

		// 기존 상세 설정값 삭제
		ledControlDao.removeLedModeSettingsByModeId(ledMode);
		
		for (LedModeSettings settings : ledMode.getLedModeSettingsList()) {
			settings.setLedMode(savedMode);
		}

		// 상세 설정값 다시 등록
		ledControlDao.saveLedModeSettings(ledMode.getLedModeSettingsList());

	}

	/**
	 * 조명 모드 삭제
	 * @param ledMode
	 */
	@Transactional
	public void removeLedMode(LedMode ledMode) {

		if (StringUtils.isEmpty(ledMode.getModeId()) == true) {
			throw new RuntimeException("조명 ID값을 찾을 수 없습니다.");
		}

		ledMode = ledControlDao.getLedMode(ledMode);

		if (ledMode == null) {
			throw new RuntimeException("잘못된 조명 모드 정보입니다. 다시 확인해주세요.");
		}

		// 조명 상세 설정값 삭제
		for (LedModeSettings settings : ledMode.getLedModeSettingsList()) {
			ledControlDao.removeLedModeSettings(settings);
		}

		// 조명 모드 삭제
		ledControlDao.removeLedMode(ledMode);

	}
	
	/**
	 * 현재 설정중인 조명 모드 조회
	 * @return
	 */
	public CurrentLedMode getCurrentLedMode() {
		List<CurrentLedMode> list = ledControlDao.getCurrentLedMode();
		
		if (list == null || list.isEmpty() == true) {
			return null;
		}
		
		return list.get(0);
	}

	/**
	 * 현재 조명 모도의 설정을 변경
	 * @param currentLedMode
	 */
	@Transactional
	public void setCurrentLedMode(CurrentLedMode currentLedMode) {
		
		if (StringUtils.isEmpty(currentLedMode.getModeId()) == true) {
			throw new RuntimeException("조명 모드를 선택해주세요.");
		}
		
		LedMode ledMode = new LedMode();
		ledMode.setModeId(currentLedMode.getModeId());
		ledMode = ledControlDao.getLedMode(ledMode);
		
		if (ledMode == null) {
			throw new RuntimeException("설정하려는 조명 모드를 찾을 수 없습니다.");
		}
		
		// 기존 설정 된 조명 모드 삭제
		ledControlDao.removeCurrentLedMode();
		
		// 새로 설정하려는 조명 모드 저장
		ledControlDao.saveCurrentLedMode(currentLedMode);
		
	}
	
	/**
	 * 각 디밍 그룹별 현재 설정값을 조회
	 * @return
	 */
	public List<CurrentBrightness> getCurrentBrightness() {
		return ledControlDao.getCurrentBrightness();
	}

}
