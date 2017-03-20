package com.cashyalla.home.led.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, String> {

	List<Menu> findByUseYnOrderBySortNoAsc(String useYn);
	
	List<Menu> findAllByOrderBySortNoAsc();
	
}
