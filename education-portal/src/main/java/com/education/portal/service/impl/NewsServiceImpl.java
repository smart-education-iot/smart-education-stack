package com.education.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.common.domain.news.News;
import com.education.common.utils.Page;
import com.education.portal.mapper.NewsMapper;
import com.education.portal.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsMapper newsMapper;
	@Override
	public List<News> getNewsList(Page<News> page) {
		// TODO Auto-generated method stub
		return newsMapper.getNewsList(page);
	}

	@Override
	public News getNewsById(int newsId) {
		// TODO Auto-generated method stub
		return newsMapper.getNewsById(newsId);
	}

}
