package com.education.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.common.domain.exam.ExamPaper;
import com.education.portal.mapper.ExamPaperMapper;
import com.education.portal.service.ExamPaperService;

@Service("examPaperService")
public class ExamPaperServiceImpl implements ExamPaperService {

	@Autowired
	private ExamPaperMapper examPaperMapper;
	
	@Override
	public ExamPaper getExamPaperById(int examPaperId) {
		return examPaperMapper.getExamPaperById(examPaperId);
	}

}
