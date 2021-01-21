package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	//가입폼
	@RequestMapping(value = "joinform", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinform() {
		System.out.println("joinform");
		return "user/joinForm.jsp";
	}
	
	//가입
		@RequestMapping(value = "join", method = { RequestMethod.GET, RequestMethod.POST })
		public String join(@ModelAttribute UserVo uvo) {
			System.out.println("가입");
			
			UserDao udao = new UserDao();
			udao.insert(uvo);
			
			System.out.println("가입완료");
			return "user/joinOk";
		}
	
	
	
	
	// 로그인폼
	@RequestMapping(value = "loginform", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginform() {
		System.out.println("loginform");
		return "user/loginForm";
	}

	// 로그인
	@RequestMapping(value = "login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(UserVo authVo) {

		System.out.println("login");

		UserDao udao = new UserDao();
		System.out.println(authVo);

		if (authVo == null) {
			System.out.println("로그인 실패");
			return "redirect:/user/loginform&result=fail";
			
		} else {
			System.out.println("로그인 성공");
			
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authVo);
			
			return "main/index";
		}
		
	}
	
	
	
}
