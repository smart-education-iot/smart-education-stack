package com.education.manage.mapper;

import java.util.List;

import com.education.common.domain.news.News;
import com.education.common.utils.Page;

public interface NewsMapper {

	public List<News> getNewsList(Page<News> page);
	
	public News getNewsById(int newsId);
	
	public void addNews(News news);
}
