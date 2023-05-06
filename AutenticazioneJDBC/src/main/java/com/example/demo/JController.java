package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JController {
	
	@RequestMapping("/")
	public String mostraHomePage(Model model)
	{
		return "index";
	}
	
	@RequestMapping("/consoleadmin")
	public ModelAndView mostraPaginaControllo()
	{
		ModelAndView mav = new ModelAndView("consoleadminpage");
		
		return mav;
	}
}
