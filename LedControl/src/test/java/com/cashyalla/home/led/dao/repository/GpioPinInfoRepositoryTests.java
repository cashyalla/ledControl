package com.cashyalla.home.led.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GpioPinInfoRepositoryTests {

	@Autowired
	private GpioPinInfoRepository gpioPinInfoRepository;
	
	@Test
	public void findNotUsedPinInfoTest() {
		gpioPinInfoRepository.findNotUsedPinInfo();
	}
	
}
