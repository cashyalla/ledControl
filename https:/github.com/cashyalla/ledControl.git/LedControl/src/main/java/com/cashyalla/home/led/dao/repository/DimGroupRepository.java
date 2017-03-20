package com.cashyalla.home.led.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.DimGroup;

public interface DimGroupRepository extends JpaRepository<DimGroup, String> {

	List<DimGroup> findByUseYn(String useYn);
	
}
