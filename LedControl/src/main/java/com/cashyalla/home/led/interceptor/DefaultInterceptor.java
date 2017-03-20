package com.cashyalla.home.led.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cashyalla.home.led.properties.ConfigProperties;
import com.cashyalla.home.led.utils.SessionUtil;

@Component
public class DefaultInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ConfigProperties configProperties;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute("isLogin", SessionUtil.isLogin(request));
		request.setAttribute("config", configProperties);
		return super.preHandle(request, response, handler);
	}
	
}