package com.wei.tempm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wei.tempm.entity.User;
import com.wei.tempm.mapper.RootMapper;
import com.wei.tempm.mapper.UserMapper;
import com.wei.tempm.service.UserService;

@Service
public class UserServiceImpl extends
		RootServiceImpl<User>
		implements UserService {

	@Autowired
	private UserMapper dao;

	@Override
	protected RootMapper<User> getDao() {
		return dao;
	}



}
