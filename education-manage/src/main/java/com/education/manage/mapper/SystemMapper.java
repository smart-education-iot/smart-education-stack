package com.education.manage.mapper;

import java.util.List;

import com.education.common.utils.MenuItem;


public interface SystemMapper {

	public List<MenuItem> getMenuItemsByAuthority(String authority);
}
