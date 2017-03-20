package com.cashyalla.home.led.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.DimDetail;
import com.cashyalla.home.led.domain.DimGroup;
import com.cashyalla.home.led.domain.GpioPinInfo;

public interface DimDetailRepository extends JpaRepository<DimDetail, Integer> {

	void deleteByDimGroup(DimGroup dimGroup);
	
	DimDetail findByGpioPinInfo(GpioPinInfo gpioPinInfo);
	
}