package com.education.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.common.domain.news.News;
import com.education.common.utils.Page;
import com.education.manage.mapper.NewsMapper;
import com.education.manage.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsMapper newsMapper;
	@Override
	public List<News> getNewsList(Page<News> page) {
		return newsMapper.getNewsList(page);
	}

	@Override
	public News getNewsById(int newsId) {
		return newsMapper.getNewsById(newsId);
	}

	@Override
	public void addNews(News news) {
		newsMapper.addNews(news);
	}

}
