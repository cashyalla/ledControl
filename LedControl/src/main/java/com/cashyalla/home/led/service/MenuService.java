package com.cashyalla.home.led.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashyalla.home.led.dao.MenuDao;
import com.cashyalla.home.led.domain.Menu;
import com.cashyalla.home.led.utils.DateUtils;

@Service
public class MenuService {

	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private DateUtils dateUtils;
	
	/**
	 * 현재 사용중인 메뉴의 리스트를 조회한다.
	 * @return
	 */
	public List<Menu> getMenuListInUse() {
		return menuDao.getMenuList("Y");
	}
	
	/**
	 * 사용/비사용 구분없이 모든 메뉴 리스트를 조회한다.
	 * @return
	 */
	public List<Menu> getMenuListAll() {
		return menuDao.getMenuList(null);
	}
	
	private void checkMenuId(Menu menu) {
		if (StringUtils.isEmpty(menu.getMenuId()) == true) {
			throw new RuntimeException("메뉴ID값은 필수입니다.");
		}
	}
	
	private void checkMenuName(Menu menu) {
		if (StringUtils.isEmpty(menu.getMenuName()) == true) {
			throw new RuntimeException("메뉴이름값은 필수입니다.");
		}
	}
	
	private void checkMenuUrl(Menu menu) {
		if (StringUtils.isEmpty(menu.getMenuUrl()) == true) {
			throw new RuntimeException("메뉴URL값은 필수입니다.");
		}
	}
	
	private void checkUseYn(Menu menu) {
		if (StringUtils.isEmpty(menu.getUseYn()) == true) {
			throw new RuntimeException("사용여부값은 필수입니다.");
		}
		
		if (menu.getUseYn().matches("[YN]") == false) {
			throw new RuntimeException("사용여부는 Y혹은 N값만 허용됩니다.");
		}
	}
	
	/**
	 * 메뉴 한개에 대한 상세 내용을 조회한다.
	 * @param menu
	 * @return
	 */
	public Menu getMenu(Menu menu) {
		checkMenuId(menu);
		
		return menuDao.getMenu(menu);
	}
	
	/**
	 * 신규 메뉴 등록
	 * @param menu
	 */
	@Transactional
	public void registerNewMenu(Menu menu) {
		checkMenuId(menu);
		checkMenuName(menu);
		checkMenuUrl(menu);
		checkUseYn(menu);
		
		// 아이디 중복 체크
		Menu duplMenu = menuDao.getMenu(menu);
		
		if (duplMenu != null) {
			throw new RuntimeException("이미 사용중인 메뉴ID입니다. 다른 메뉴ID를 사용해주세요.");
		}
		
		menu.setCrtDate(dateUtils.getNowDate());
		menu.setUpdDate(dateUtils.getNowDate());
		
		menuDao.saveMenu(menu);
		
	}
	
	/**
	 * 메뉴의 내용을 수정한다.
	 * @param menus
	 */
	@Transactional
	public void updateMenu(List<Menu> menus) {
		
		for (Menu menu : menus) {
			checkMenuId(menu);
			checkMenuName(menu);
			checkMenuUrl(menu);
			checkUseYn(menu);
			
			menu.setUpdDate(dateUtils.getNowDate());
			menuDao.saveMenu(menu);
		}
		
	}
	
	/**
	 * 메뉴를 삭제한다.
	 * @param menus
	 */
	@Transactional
	public void removeMenu(List<Menu> menus) {
		for (Menu menu : menus) {
			checkMenuId(menu);
			menuDao.removeMenu(menu);
		}
	}
	
}
