package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/main")
public class MainController {

	// 메인페이지
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String index() {
		System.out.println("main");
		return "/WEB-INF/views/main/index.jsp";
	}

}
