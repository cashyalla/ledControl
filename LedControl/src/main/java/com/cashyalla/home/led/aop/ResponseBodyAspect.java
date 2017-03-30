package com.cashyalla.home.led.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseBodyAspect {

	@Pointcut("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
	public void pointcut() {
		
	}
	
	@Before("pointcut()")
	public void before(JoinPoint joinPoint) {
	}
	
}
