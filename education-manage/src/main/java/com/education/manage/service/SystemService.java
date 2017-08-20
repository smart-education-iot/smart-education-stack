package com.education.manage.service;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

import com.education.common.utils.MenuItem;

@Service
public interface SystemService {

	public LinkedHashMap<String,MenuItem> getMenuItemsByAuthority(String authority);
}
