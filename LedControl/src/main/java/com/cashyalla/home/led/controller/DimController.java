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
import com.cashyalla.home.led.domain.DimDetail;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.service.LedControlService;

@Controller
@RequestMapping(value = "/led/dim")
public class DimController {

	@Autowired
	private LedControlService ledControlService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		List<DimGroup> dimGroupList = ledControlService.getDimGroupList();
		model.addAttribute("dimGroupList", dimGroupList);
	}
	
	@RequestMapping(value = "/listJson", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap listJson() {
		ModelMap modelMap = new ModelMap();
		
		List<DimGroup> dimGroupList = ledControlService.getDimGroupListInUse();
		modelMap.addAttribute("list", dimGroupList);

		return modelMap;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap get(DimGroup dimGroup) {
		ModelMap modelMap = new ModelMap();
		
		try {
			dimGroup = ledControlService.getDimGroupInfo(dimGroup);
			modelMap.addAttribute("dimGroup", dimGroup);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap insert(DimGroup dimGroup) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.registerDimGroup(dimGroup);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap update(DimGroup dimGroup) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.updateDimGroup(dimGroup);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap delete(DimGroup dimGroup) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.removeDimGroup(dimGroup);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/insertDetail", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap insertDetail(DimDetail dimDetail) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.registerDimDetail(dimDetail);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap updateDetail(DimDetail dimDetail) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.updateDimDetail(dimDetail);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/getDetail", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap getDetail(DimDetail dimDetail) {
		ModelMap modelMap = new ModelMap();

		dimDetail = ledControlService.getDimDetail(dimDetail);
		// 무한루프 방지
		dimDetail.setDimGroup(null);
		modelMap.addAttribute("dimDetail", dimDetail);

		return modelMap;
	}
	
	@RequestMapping(value = "/deleteDetail", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap deleteDetail(DimDetail dimDetail) {
		ModelMap modelMap = new ModelMap();
		
		try {
			ledControlService.removeDimDetail(dimDetail);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
}
