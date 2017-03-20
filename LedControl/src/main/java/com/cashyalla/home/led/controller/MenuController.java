package com.cashyalla.home.led.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cashyalla.home.led.domain.CommonResult;
import com.cashyalla.home.led.domain.Menu;
import com.cashyalla.home.led.service.MenuService;

@Controller
@RequestMapping(value = "/led/menu")
public class MenuController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(Model model) {
		List<Menu> menuList = menuService.getMenuListAll();
		model.addAttribute("menuList", menuList);
	}
	
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap insert(Menu menu) {
		ModelMap modelMap = new ModelMap();
		
		try {
			menuService.registerNewMenu(menu);
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap update(Menu menu) {
		ModelMap modelMap = new ModelMap();
		
		try {
			menuService.updateMenu(menu.getMenus());
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap delete(Menu menu) {
		ModelMap modelMap = new ModelMap();
		
		try {
			menuService.removeMenu(menu.getMenus());
			modelMap.addAttribute(new CommonResult(true));
		} catch (Exception e) {
			modelMap.addAttribute(new CommonResult(e));
		}
		
		return modelMap;
	}
	
}
