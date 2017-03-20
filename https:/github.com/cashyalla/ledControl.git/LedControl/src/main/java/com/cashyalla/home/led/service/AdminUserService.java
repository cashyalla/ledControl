package com.cashyalla.home.led.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashyalla.home.led.dao.AdminUserDao;
import com.cashyalla.home.led.domain.AdminUser;
import com.cashyalla.home.led.utils.DateUtils;
import com.cashyalla.home.led.utils.SHA256;

@Service
public class AdminUserService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AdminUserDao adminUserDao;
	
	@Autowired
	private DateUtils dateUtils;
	
	/**
	 * 로그인 혹은 가입을 진행하기 전 필수 입력값 체크
	 * @param adminUser
	 */
	private void checkAdminUser(AdminUser adminUser) {
		if (StringUtils.isEmpty(adminUser.getAdminId()) == true) {
			throw new RuntimeException("관리자ID는 필수값 입니다.");
		}
		
		if (StringUtils.isEmpty(adminUser.getPassword()) == true) {
			throw new RuntimeException("Password를 입력해 주세요.");
		}
	}

	/**
	 * 입력한 관리자 패스워드를 sha256으로 암호화
	 * @param adminUser
	 */
	private void encryptAdminPassword(AdminUser adminUser) {
		try {
			adminUser.setPassword(SHA256.hashString(adminUser.getPassword()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 새로운 관리자 등록
	 * @param adminUser
	 * @return
	 */
	@Transactional
	public AdminUser joinAdminUser(AdminUser adminUser) {
		
		checkAdminUser(adminUser);
		
		// 동일 ID로 가입한 관리자가 있는지 검사
		if (adminUserDao.getCountByAdminId(adminUser) > 0) {
			throw new RuntimeException("동일한 ID의 관리자가 존재합니다. 다른 ID를 사용해주세요.");
		}
		
		encryptAdminPassword(adminUser);
		
		adminUser.setCrtDate(dateUtils.getNowDate());
		
		adminUserDao.saveAdminUser(adminUser);
		
		return adminUser;
	}
	
	/**
	 * 관리자 로그인
	 * @param adminUser
	 * @return
	 */
	@Transactional
	public AdminUser loginAdminUser(AdminUser adminUser) {
		checkAdminUser(adminUser);
		encryptAdminPassword(adminUser);
		
		// 아이디와 패스워드로 관리자 정보 조회
		adminUser = adminUserDao.getAdminUser(adminUser);
		
		// 데이터가 조회되지 않았다면 아이디와 패스워드가 일치하지 않음
		if (adminUser == null) {
			throw new RuntimeException("아이디 또는 패스워드를 다시 확인해주세요.");
		}
		
		adminUser.setLoginDate(dateUtils.getNowDate());
		return adminUser;
	}
	
}
