package com.education.portal.service;

import java.util.List;

import com.education.common.domain.question.Comment;
import com.education.common.utils.Page;


public interface CommentService {

	public List<Comment> getCommentByTypeAndReferId(int referType,int referId,int indexId,Page<Comment> page);
	
	public void addComment(Comment comment);
}
