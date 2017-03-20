package com.cashyalla.home.led.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/led/timer")
public class TimerController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		
	}
	
}
