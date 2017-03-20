package com.cashyalla.home.led.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cashyalla.home.led.utils.SessionUtil;

@Controller
@RequestMapping(value = "/")
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (SessionUtil.isLogin(request) == true) {
			response.sendRedirect("/led/mode/list");
		} else {
			response.sendRedirect("/login/page");
		}
	}
	
}
