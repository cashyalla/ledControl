package com.cashyalla.home.led.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cashyalla.home.led.domain.CommonResult;
import com.cashyalla.home.led.domain.GpioPinInfo;
import com.cashyalla.home.led.service.LedControlService;

@Controller
@RequestMapping("/led/gpio")
public class GpioPinController {

	@Autowired
	private LedControlService ledControlService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
	
		List<GpioPinInfo> list = ledControlService.getGpioPinInfoList();
		model.addAttribute("pinList", list);
		
	}
	
	@RequestMapping(value = "/listAction", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap listAction() {
		ModelMap modelMap = new ModelMap();
		
		List<GpioPinInfo> list = ledControlService.getGpioPinInfoList();
		modelMap.addAttribute("pinList", list);
		
		return modelMap;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap get(GpioPinInfo gpioPinInfo) {
		ModelMap modelMap = new ModelMap();
		
		try {
			gpioPinInfo = ledControlService.getGpioPinInfo(gpioPinInfo);
			modelMap.addAttribute("gpioPinInfo", gpioPinInfo);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}

		return modelMap;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap insert(GpioPinInfo gpioPinInfo) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.registerGpioPinInfo(gpioPinInfo);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap update(GpioPinInfo gpioPinInfo) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.updateGpioPinInfo(gpioPinInfo);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap delete(GpioPinInfo gpioPinInfo) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.removeGpioPinInfo(gpioPinInfo);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
}
