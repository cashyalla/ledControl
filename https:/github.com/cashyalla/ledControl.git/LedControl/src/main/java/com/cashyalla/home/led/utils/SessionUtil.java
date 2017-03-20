package com.cashyalla.home.led.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.cashyalla.home.led.domain.AdminUser;
import com.cashyalla.home.led.domain.Menu;

public class SessionUtil {

	public static void sessionCreate(HttpServletRequest request, AdminUser adminUser, List<Menu> menuList) {
		request.getSession().setAttribute("sessionUser", adminUser);
		request.getSession().setAttribute("menuList", menuList);
	}
	
	public static boolean isLogin(HttpServletRequest request) {
		AdminUser loginUser = (AdminUser) request.getSession().getAttribute("sessionUser");
		
		if (loginUser == null) {
			return false;
		} else if (StringUtils.isNotEmpty(loginUser.getAdminId()) == true) {
			return true;
		} else {
			return false;
		}
	}

	public static AdminUser getSessionUser(HttpServletRequest request) {
		return (AdminUser) request.getSession().getAttribute("sessionUser");
	}
	
	public static String getLoginAdminId(HttpServletRequest request) {
		return getSessionUser(request).getAdminId();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Menu> getMenuList(HttpServletRequest request) {
		return (List<Menu>) request.getSession().getAttribute("menuList");
	}
	
	public static void sessionDelete(HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
}