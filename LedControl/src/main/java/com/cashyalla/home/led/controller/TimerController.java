package com.cashyalla.home.led.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cashyalla.home.led.domain.CommonResult;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.TimerChartData;
import com.cashyalla.home.led.domain.TimerSchedule;
import com.cashyalla.home.led.service.LedControlService;
import com.cashyalla.home.led.service.TimerScheduleService;

@Controller
@RequestMapping(value = "/led/timer")
public class TimerController {

	@Autowired
	private LedControlService ledControlService;
	
	@Autowired
	private TimerScheduleService timerScheduleService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		
		List<DimGroup> dimGroupList = ledControlService.getDimGroupListInUse();
		model.addAttribute("dimGroupList", dimGroupList);
		
		List<TimerSchedule> timerScheduleList = timerScheduleService.getTimerScheduleList();
		model.addAttribute("timerScheduleList", timerScheduleList);
		
		Map<String, List<TimerChartData>> timerChartMap = timerScheduleService.getTimerChartData();
		model.addAttribute("timerChartMap", timerChartMap);
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap update(TimerSchedule timerSchedule) {
		ModelMap modelMap = new ModelMap();
		
		try {
			timerScheduleService.updateTimerSchedule(timerSchedule);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap delete(TimerSchedule timerSchedule) {
		ModelMap modelMap = new ModelMap();
		
		try {
			timerScheduleService.deleteTimerSchedule(timerSchedule);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
}
