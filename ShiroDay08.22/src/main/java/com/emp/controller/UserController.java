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
	
	//跳转到登录页面
	@RequestMapping("/user/toLogin")
	public String toLogin(){
		return "Login";
	}

	@RequestMapping("/user/login")
	public String login(User user,Model model,@RequestParam(value="rememberMe",defaultValue="0")int rememberMe){
		//获取当前用户  Subject 主体 调用 "/user/login"请求的东西(用户，程序)
		Subject subject=SecurityUtils.getSubject();
		//创建一个令牌对象
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername().trim(), user.getPassword().trim());
		//判断是否选择了记住我
		if(rememberMe==1){
			token.setRememberMe(true);
		}
		try{
			//为当前用户进行认证，授权,并会自动放入session中
			subject.login(token);
			//重定向到主页员工列表
			return "redirect:/emp/conditionList";			
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("msg", "用户名或密码错误");
			return "Login";
		}
	}
	
	//跳转到注册页面
	@RequestMapping("/user/toRegister")
	public String toRegister(){
		return "register";
	}
	
	//注册
	@RequestMapping("/user/register")
	public String register(User user){
		userService.register(user);//未成功会抛异常
		//注册成功
		return "redirect:/user/toLogin";
	}
	
	//未授权页面的跳转
	@RequestMapping("/user/unauthorized")
	public String toUnauthorized(){
		return "Unauthorized";
	}
	
	
}
