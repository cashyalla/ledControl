package com.cashyalla.home.led.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cashyalla.home.led.domain.Menu;
import com.cashyalla.home.led.utils.SessionUtil;

@Component
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("isLogin : " + SessionUtil.isLogin(request));
		if (SessionUtil.isLogin(request) == true) {
			setCurrentMenu(request);
			return true;
		} else {
			response.sendRedirect("/login/page");
			return false;
		}
	}
	
	private void setCurrentMenu(HttpServletRequest request) {
		String url = request.getRequestURI();
		List<Menu> menuList = SessionUtil.getMenuList(request);
		
		for (Menu menu : menuList) {
			if (menu.getMenuUrl().equals(url) == true) {
				request.setAttribute("current", menu);
			}
		}
	}
}
