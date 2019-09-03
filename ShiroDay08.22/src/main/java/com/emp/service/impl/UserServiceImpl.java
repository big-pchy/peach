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
   //注入Dao对象
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

	//注册
	public void register(User user) {
		//加盐,加密的效果
		//MD5算法(格式:算法,密码,盐,加密次数)
		String password = new SimpleHash("MD5",user.getPassword(),user.getUsername(),1024).toString();
		user.setPassword(password);
		userDao.register(user);
		
	}

}
