package com.cashyalla.home.led.dao;

import com.cashyalla.home.led.domain.Sequence;

public interface SequenceDao {

	Sequence getSequence(String seqType);
	
}
