package com.cashyalla.home.led.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.CommonCode;

public interface CommonCodeRepository extends JpaRepository<CommonCode, String> {

	List<CommonCode> findByCodeGrp(String codeGrp);
	
}
