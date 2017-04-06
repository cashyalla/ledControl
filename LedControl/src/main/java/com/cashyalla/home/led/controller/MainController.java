package com.cashyalla.home.led.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cashyalla.home.led.domain.CurrentBrightness;
import com.cashyalla.home.led.domain.CurrentLedMode;
import com.cashyalla.home.led.domain.LedMode;
import com.cashyalla.home.led.service.LedControlService;
import com.cashyalla.home.led.utils.SessionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/")
public class MainController {

	@Autowired
	private LedControlService ledControlService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (SessionUtil.isLogin(request) == true) {
			log.info("send redirect to main");
			response.sendRedirect("/led/main");
		} else {
			log.info("send redirect to login");
			response.sendRedirect("/login/page");
		}
	}
	
	@RequestMapping(value = "/led/main", method = RequestMethod.GET)
	public void main(Model model) {
		
		// 현재 설정 모드
		CurrentLedMode currentLedMode = ledControlService.getCurrentLedMode();
		LedMode ledMode = new LedMode();
		ledMode.setModeId(currentLedMode.getModeId());
		
		ledMode = ledControlService.getLedMode(ledMode);
		model.addAttribute("currentLedMode", ledMode);
		
		// 현재 밝기값
		List<CurrentBrightness> currentBrightness = ledControlService.getCurrentBrightness();
		model.addAttribute("currentBrightness", currentBrightness);
		
	}

}
