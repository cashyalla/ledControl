package com.cashyalla.home.led.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cashyalla.home.led.dao.repository.SequenceRepository;
import com.cashyalla.home.led.domain.Sequence;

@Repository
public class SequenceDaoJpa implements SequenceDao {

	@Autowired
	private SequenceRepository sequenceRepository;
	
	@Override
	public Sequence getSequence(String seqType) {
		return sequenceRepository.findOne(seqType);
	}

}
