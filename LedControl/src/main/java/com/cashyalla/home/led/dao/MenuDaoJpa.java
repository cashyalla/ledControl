package com.cashyalla.home.led.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cashyalla.home.led.dao.repository.MenuRepository;
import com.cashyalla.home.led.domain.Menu;

@Repository
public class MenuDaoJpa implements MenuDao {

	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public List<Menu> getMenuList(String useYn){
		if (useYn == null || useYn.length() <= 0) {
			return menuRepository.findAllByOrderBySortNoAsc();
		} else {
			return menuRepository.findByUseYnOrderBySortNoAsc(useYn);	
		}
	}
	
	@Override
	public Menu saveMenu(Menu menu) {
		return menuRepository.save(menu);
	}
	
	@Override
	public Menu getMenu(Menu menu) {
		return menuRepository.findOne(menu.getMenuId());
	}
	
	@Override
	public void removeMenu(Menu menu) {
		menuRepository.delete(menu.getMenuId());
	}

}
