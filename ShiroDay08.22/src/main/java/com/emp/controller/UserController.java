package com.emp.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.emp.entity.User;
import com.emp.service.UserService;

@Controller
public class UserController {
	
	@Resource
	private UserService userService;
	
	//��ת����¼ҳ��
	@RequestMapping("/user/toLogin")
	public String toLogin(){
		return "Login";
	}

	@RequestMapping("/user/login")
	public String login(User user,Model model,@RequestParam(value="rememberMe",defaultValue="0")int rememberMe){
		//��ȡ��ǰ�û�  Subject ���� ���� "/user/login"����Ķ���(�û�������)
		Subject subject=SecurityUtils.getSubject();
		//����һ�����ƶ���
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername().trim(), user.getPassword().trim());
		//�ж��Ƿ�ѡ���˼�ס��
		if(rememberMe==1){
			token.setRememberMe(true);
		}
		try{
			//Ϊ��ǰ�û�������֤����Ȩ,�����Զ�����session��
			subject.login(token);
			//�ض�����ҳԱ���б�
			return "redirect:/emp/conditionList";			
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("msg", "�û������������");
			return "Login";
		}
	}
	
	//��ת��ע��ҳ��
	@RequestMapping("/user/toRegister")
	public String toRegister(){
		return "register";
	}
	
	//ע��
	@RequestMapping("/user/register")
	public String register(User user){
		userService.register(user);//δ�ɹ������쳣
		//ע��ɹ�
		return "redirect:/user/toLogin";
	}
	
	//δ��Ȩҳ�����ת
	@RequestMapping("/user/unauthorized")
	public String toUnauthorized(){
		return "Unauthorized";
	}
	
	
}
