package com.cashyalla.home.led.dao;

import com.cashyalla.home.led.domain.AdminUser;

public interface AdminUserDao {

	int getCountByAdminId(AdminUser adminUser);
	
	void saveAdminUser(AdminUser adminUser);
	
	AdminUser getAdminUser(AdminUser adminUser);
	
}
