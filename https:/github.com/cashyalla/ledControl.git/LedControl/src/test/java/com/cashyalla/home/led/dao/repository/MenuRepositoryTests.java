package com.cashyalla.home.led.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuRepositoryTests {

	@Autowired
	private MenuRepository menuRepository;
	
	@Test
	public void findAllOrderByOrderAscTest() {
		menuRepository.findAllByOrderBySortNoAsc();
	}
	
	@Test
	public void findByUseYnOrderByOrderAscTest() {
		menuRepository.findByUseYnOrderBySortNoAsc("Y");
		menuRepository.findByUseYnOrderBySortNoAsc("N");
	}
	
}
