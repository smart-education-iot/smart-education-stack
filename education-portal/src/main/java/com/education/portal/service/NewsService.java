package com.education.portal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.education.common.domain.news.News;
import com.education.common.utils.Page;

@Service
public interface NewsService {

	public List<News> getNewsList(Page<News> page);
	
	public News getNewsById(int newsId);
	
}
