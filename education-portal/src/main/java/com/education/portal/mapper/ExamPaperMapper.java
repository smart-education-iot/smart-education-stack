package com.education.portal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.education.common.domain.exam.ExamPaper;
import com.education.common.utils.Page;

public interface ExamPaperMapper {

	public List<ExamPaper> getExamPaperList(@Param("searchStr") String searchStr, @Param("page") Page<ExamPaper> page);
	
	public void insertExamPaper(ExamPaper examPaper);
	
	public ExamPaper getExamPaperById(int examPaperId);
	
	public void updateExamPaper(ExamPaper examPaper);
	
	public void deleteExamPaper(int id);
	
	public List<ExamPaper> getEnabledExamPaperList(@Param("userName") String userName,@Param("page") Page<ExamPaper> page);
}
