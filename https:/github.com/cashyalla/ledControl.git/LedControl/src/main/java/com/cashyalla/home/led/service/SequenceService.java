package com.cashyalla.home.led.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cashyalla.home.led.dao.SequenceDao;
import com.cashyalla.home.led.domain.Sequence;

@Service
public class SequenceService {

	@Autowired
	private SequenceDao sequenceDao;
	
	/**
	 * 시퀀스 번호 발급
	 * @param seqType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getSequence(String seqType) {
		
		if (StringUtils.isEmpty(seqType) == true) {
			throw new RuntimeException("SEQ TYPE값은 필수입니다.");
		}
		
		Sequence sequence = sequenceDao.getSequence(seqType);
		
		if (sequence == null) {
			throw new RuntimeException("시퀀스 정보를 조회할 수 없습니다.");
		}
		
		int seqLength = sequence.getSeqLength();
		int prefixLength = sequence.getSeqPrefix().length();
		
		// 시퀀스 번호 생성
		String seqVal = sequence.getSeqPrefix() + String.format("%0" + (seqLength - prefixLength) + "d", sequence.getSeqCurVal());
		
		// 시퀀스 값 1 증가
		sequence.setSeqCurVal(sequence.getSeqCurVal() + 1);
		
		return seqVal;
		
	}
	
}