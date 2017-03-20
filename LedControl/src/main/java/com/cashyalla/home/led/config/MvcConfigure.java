package com.cashyalla.home.led.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cashyalla.home.led.interceptor.AuthCheckInterceptor;
import com.cashyalla.home.led.interceptor.DefaultInterceptor;

@Configuration
public class MvcConfigure extends WebMvcConfigurerAdapter {

	@Autowired
	private DefaultInterceptor defaultInterceptor;
	
	@Autowired
	private AuthCheckInterceptor authCheckInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(defaultInterceptor).addPathPatterns("/**");
		registry.addInterceptor(authCheckInterceptor).addPathPatterns("/led/**");
	}
	
}