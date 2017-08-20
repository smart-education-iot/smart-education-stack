package com.education.manage.controller.page.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.education.common.domain.question.Field;
import com.education.manage.service.QuestionService;

@Controller
public class DashBoardPageAdmin {
	@Autowired
	private QuestionService questionService;
	

	@RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
	public String dashboardPage(Model model) {
		
		List<Field> fieldList = questionService.getAllField(null);
		
		
		model.addAttribute("fieldList", fieldList);
		return "dashboard";
	}

}
