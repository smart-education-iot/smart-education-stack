package com.education.portal.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.common.domain.user.Department;
import com.education.common.domain.user.Group;
import com.education.common.domain.user.Role;
import com.education.common.domain.user.User;
import com.education.common.utils.Page;
import com.education.portal.mapper.UserMapper;
import com.education.portal.service.UserService;

/**
 * @author jamesli
 * @date 2014年6月8日 下午8:21:31
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserMapper userMapper;

	@Override
	@Transactional
	public int addUser(User user,String authority,int groupId,HashMap<String,Role> roleMap) {
		try {
			int userId = -1;
			userMapper.insertUser(user);
			userId = user.getUserId();
			userMapper.grantUserRole(userId, roleMap.get(authority).getRoleId());
			if(user.getDepId() != 0)
				userMapper.addUser2Dep(userId, user.getDepId());
			return userId;
		} catch (Exception e) {
			String cause = e.getCause().getMessage();
			throw new RuntimeException(cause);
		}
	}
	
	
	@Override
	@Transactional
	public void updateUser(User user, String oldPassword) {
		try {
			userMapper.updateUser(user, oldPassword);
			userMapper.deleteUser2Dep(user.getUserId());
			userMapper.addUser2Dep(user.getUserId(), user.getDepId());
		} catch (Exception e) {
			String cause = e.getCause().getMessage();
			throw new RuntimeException(cause);
		}
	}

	@Override
	public List<Group> getGroupListByUserId(int userId, Page<Group> page) {
		// TODO Auto-generated method stub
		return userMapper.getGroupListByUserId(userId, page);
	}

	@Override
	public HashMap<String, Role> getRoleMap() {
		// TODO Auto-generated method stub
		List<Role> roleList = userMapper.getRoleList();
		HashMap<String,Role> map = new HashMap<String,Role>();
		for(Role r : roleList){
			map.put(r.getAuthority(), r);
		}
		return map;
	}

	@Override
	public void changeUserStatus(List<Integer> idList,boolean enabled) {
		userMapper.changeUserStatus(idList, enabled);
	}
	
	@Override
	public void addUserGroup(int userId, int groupId) {
		userMapper.addUserGroup(userId, groupId);
	}


	@Override
	public void deleteUserGroup(int userId, int groupId, int managerId) {
		userMapper.deleteUserGroup(userId, groupId, managerId);
	}


	@Override
	public List<Department> getDepList(Page<Department> page) {
		return userMapper.getDepList(page);
	}


	@Override
	public User getUserByName(String userName) {
		return userMapper.getUserByName(userName);
	}


	@Override
	public void updateUserPwd(User user, String oldPwd) {
		userMapper.updateUser(user, oldPwd);
	}
}
