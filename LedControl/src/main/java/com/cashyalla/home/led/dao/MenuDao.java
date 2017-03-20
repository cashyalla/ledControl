package com.cashyalla.home.led.dao;

import java.util.List;

import com.cashyalla.home.led.domain.Menu;

public interface MenuDao {

	/**
	 * 메뉴 리스트 조회
	 * @return
	 */
	List<Menu> getMenuList(String useYn);

	Menu saveMenu(Menu menu);

	Menu getMenu(Menu menu);
	
	void removeMenu(Menu menu);

}
