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
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.LedMode;
import com.cashyalla.home.led.service.LedControlService;

@Controller
@RequestMapping(value = "/led/mode")
public class LedModeController {

	@Autowired
	private LedControlService ledControlService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		List<DimGroup> dimGroupList = ledControlService.getDimGroupListInUse();
		model.addAttribute("dimGroupList", dimGroupList);
		
		List<LedMode> ledModeList = ledControlService.getLedModeList();
		model.addAttribute("ledModeList", ledModeList);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap get(LedMode ledMode) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledMode = ledControlService.getLedMode(ledMode);
			modelMap.addAttribute("ledMode", ledMode);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap insert(LedMode ledMode) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.registerLedMode(ledMode);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap update(LedMode ledMode) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.updateLedMode(ledMode);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap delete(LedMode ledMode) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.removeLedMode(ledMode);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
}
