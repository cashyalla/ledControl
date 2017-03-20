package com.cashyalla.home.led.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {

	int countByAdminId(String adminId);
	
	AdminUser findByAdminIdAndPassword(String adminId, String password);
	
}
