package com.cashyalla.home.led.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashyalla.home.led.domain.Sequence;

public interface SequenceRepository extends JpaRepository<Sequence, String> {
	
}
