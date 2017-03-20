package com.cashyalla.home.led.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cashyalla.home.led.dao.repository.AdminUserRepository;
import com.cashyalla.home.led.dao.repository.GpioPinInfoRepository;
import com.cashyalla.home.led.domain.AdminUser;

@Repository
public class AdminUserDaoJpa implements AdminUserDao {

	@Autowired
	private AdminUserRepository adminUserRepository;
	
	@Override
	public int getCountByAdminId(AdminUser adminUser) {
		return adminUserRepository.countByAdminId(adminUser.getAdminId());
	}
	
	@Override
	public void saveAdminUser(AdminUser adminUser) {
		adminUserRepository.save(adminUser);
	}
	
	@Override
	public AdminUser getAdminUser(AdminUser adminUser) {
		return adminUserRepository.findByAdminIdAndPassword(adminUser.getAdminId(), adminUser.getPassword());
	}
	
}
