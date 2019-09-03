package com.emp.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.emp.dao.UserDao;
import com.emp.entity.User;
import com.emp.service.UserService;

@Service
public class UserServiceImpl
  implements UserService{
   //ע��Dao����
	@Resource
	private UserDao userDao;
	
	@Override
	public User queryUser(String username) {
		   User user = userDao.queryByUserName(username);
		return user;
	}

	@Override
	public Set<String> queryRoles(String username) {
		  Set<String> roles = userDao.queryRoles(username);
		return roles;
	}

	@Override
	public Set<String> queryPermissions(String username) {
		Set<String> pers = userDao.queryPermissions(username);
		return pers;
	}

	//ע��
	public void register(User user) {
		//����,���ܵ�Ч��
		//MD5�㷨(��ʽ:�㷨,����,��,���ܴ���)
		String password = new SimpleHash("MD5",user.getPassword(),user.getUsername(),1024).toString();
		user.setPassword(password);
		userDao.register(user);
		
	}

}
