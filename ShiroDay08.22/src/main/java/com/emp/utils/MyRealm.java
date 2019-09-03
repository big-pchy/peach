package com.emp.utils;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.emp.entity.User;
import com.emp.service.UserService;
 
/**
 *    
 *  认证(登录)
 *  授权
 *  的核心业务逻辑
 *
 */
public class MyRealm extends AuthorizingRealm {
 
	@Autowired
	private UserService userService;
 
	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		/**
		 * 注意principals.getPrimaryPrincipal()对应
		 * new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName())的第一个参数
		 */
		
		//获取当前身份
		String userName = (String) principals.getPrimaryPrincipal();
		//需要传入当前用户的所有角色信息和权限,去进行判断
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//从数据库中查找该用户有何角色和权限
		Set<String> roles = userService.queryRoles(userName);
		Set<String> permissions = userService.queryPermissions(userName);
		
		//传入对应角色和权限,然后判断是否能进行当前操作
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		
		return info;
		
	}
 
	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();// 获取用户名
        // 根据用户输入的用户名在数据库进行匹配
        User user = userService.queryUser(userName);
        //用户名存在则匹配密码
        if (user != null) {
             //1)principal：认证的实体信息，可以是userName，也可以是数据库表对应的用户的实体对象  
            Object principal = user.getUsername();

            //2)credentials：数据库中的密码  
            Object credentials = user.getPassword(); 

            //3)realmName：当前realm对象的name，调用父类的getName()方法即可  
            String realmName = getName();  

            //4)credentialsSalt盐值  
            ByteSource credentialsSalt = ByteSource.Util.bytes(userName);//使用用户名作为盐值  

            //根据用户的情况，来构建AuthenticationInfo对象,通常使用的实现类为SimpleAuthenticationInfo
            //5)与数据库中用户名和密码进行比对，密码盐值加密，第4个参数传入realName。
            SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt,realmName);
            return authcInfo;
        } else {
        	//未查询到该用户
            return null;
        }
	}
 
}
