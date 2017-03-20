package com.cashyalla.home.led.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cashyalla.home.led.domain.GpioPinInfo;

public interface GpioPinInfoRepository extends JpaRepository<GpioPinInfo, Integer> {

	GpioPinInfo findByPinNumber(int pinNumber);
	
	@Query("SELECT m FROM GpioPinInfo m WHERE m NOT IN (SELECT n.gpioPinInfo FROM DimDetail n)")
	List<GpioPinInfo> findNotUsedPinInfo();
	
}
