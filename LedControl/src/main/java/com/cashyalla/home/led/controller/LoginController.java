package com.cashyalla.home.led.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cashyalla.home.led.domain.AdminUser;
import com.cashyalla.home.led.domain.CommonResult;
import com.cashyalla.home.led.domain.Menu;
import com.cashyalla.home.led.service.AdminUserService;
import com.cashyalla.home.led.service.MenuService;
import com.cashyalla.home.led.utils.SessionUtil;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (SessionUtil.isLogin(request) == true) {
			response.sendRedirect("/led/mode/list");
		}

		return "/login/loginPage";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap login(AdminUser adminUser, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		
		try {
			adminUser = adminUserService.loginAdminUser(adminUser);
			List<Menu> menuList = menuService.getMenuListInUse();
			SessionUtil.sessionCreate(request, adminUser, menuList);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SessionUtil.sessionDelete(request);
		response.sendRedirect("/login/page");
	}
	
}
