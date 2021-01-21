package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value = "/gbook")
public class GuestbookController {

	// 리스트
	@RequestMapping(value = "/addlist", method = { RequestMethod.GET, RequestMethod.POST })
	public String addlist(Model model) {
		System.out.println("addlist");
		
		GuestDao gdao = new GuestDao();
		List<GuestVo> GList = gdao.GList();

		model.addAttribute("gList", GList);
		System.out.println(GList.toString());
		
		return "guestbook/AddList";
	}

	// 등록
	@RequestMapping(value = "add", method = { RequestMethod.GET, RequestMethod.POST })
	public String add(@ModelAttribute GuestVo gvo, 
			          Model model) {
		System.out.println("add");
		
		GuestDao gdao = new GuestDao();
		gdao.insert(gvo);
		System.out.println(gvo + "등록");

		return "redirect:/gbook/addlist";
	}

	// 삭제폼
	@RequestMapping(value = "dform", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteform(@RequestParam("no") int no) {
		System.out.println("deleteForm");
		
		return "guestbook/DeleteForm";
	}

	// 삭제
	@RequestMapping(value = "delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@RequestParam int no, 
						 @RequestParam String password) {
		System.out.println("delete");

		GuestDao gdao = new GuestDao();
		GuestVo gvo = gdao.getGuest(no);
		
		
		if (gvo.getPassword().equals(password)) {
			System.out.println(gvo + "삭제");
			gdao.delete(gvo);

			return "redirect:/gbook/addlist";
			
		} else {
			return "redirect:/gbook/dform?no=" + no + "&result=fail";
		}

	}

}
