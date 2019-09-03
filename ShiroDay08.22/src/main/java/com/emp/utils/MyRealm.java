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
 *  ��֤(��¼)
 *  ��Ȩ
 *  �ĺ���ҵ���߼�
 *
 */
public class MyRealm extends AuthorizingRealm {
 
	@Autowired
	private UserService userService;
 
	/**
	 * ��Ȩ����
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		/**
		 * ע��principals.getPrimaryPrincipal()��Ӧ
		 * new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName())�ĵ�һ������
		 */
		
		//��ȡ��ǰ���
		String userName = (String) principals.getPrimaryPrincipal();
		//��Ҫ���뵱ǰ�û������н�ɫ��Ϣ��Ȩ��,ȥ�����ж�
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//�����ݿ��в��Ҹ��û��кν�ɫ��Ȩ��
		Set<String> roles = userService.queryRoles(userName);
		Set<String> permissions = userService.queryPermissions(userName);
		
		//�����Ӧ��ɫ��Ȩ��,Ȼ���ж��Ƿ��ܽ��е�ǰ����
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		
		return info;
		
	}
 
	/**
	 * ��֤����
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();// ��ȡ�û���
        // �����û�������û��������ݿ����ƥ��
        User user = userService.queryUser(userName);
        //�û���������ƥ������
        if (user != null) {
             //1)principal����֤��ʵ����Ϣ��������userName��Ҳ���������ݿ���Ӧ���û���ʵ�����  
            Object principal = user.getUsername();

            //2)credentials�����ݿ��е�����  
            Object credentials = user.getPassword(); 

            //3)realmName����ǰrealm�����name�����ø����getName()��������  
            String realmName = getName();  

            //4)credentialsSalt��ֵ  
            ByteSource credentialsSalt = ByteSource.Util.bytes(userName);//ʹ���û�����Ϊ��ֵ  

            //�����û��������������AuthenticationInfo����,ͨ��ʹ�õ�ʵ����ΪSimpleAuthenticationInfo
            //5)�����ݿ����û�����������бȶԣ�������ֵ���ܣ���4����������realName��
            SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt,realmName);
            return authcInfo;
        } else {
        	//δ��ѯ�����û�
            return null;
        }
	}
 
}
