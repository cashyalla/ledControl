package com.cashyalla.home.led.service;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cashyalla.home.led.domain.AdminUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminUserServiceTests {

	@Autowired
	private AdminUserService adminUserService;
	
	private AdminUser adminUser;
	
	@Before
	public void setUp() {
		adminUser = new AdminUser();
		adminUser.setAdminId("admin2");
		adminUser.setPassword("1234");
	}
	
	@Test
	@Transactional
	public void joinAdminUserTest() {
		adminUserService.joinAdminUser(adminUser);
	}

	@Test
	@Transactional
	public void loginAdminUserTest() {
		adminUser = adminUserService.joinAdminUser(adminUser);
		adminUser.setPassword("1234");
		adminUser = adminUserService.loginAdminUser(adminUser);
		
		assertNotNull(adminUser);
	}
	
}
