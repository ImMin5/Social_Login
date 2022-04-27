package com.team.bf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		System.out.println("home");
		mav.setViewName("home");
		return mav;
	}
	@GetMapping("/login")
	public ModelAndView home2() {
		ModelAndView mav = new ModelAndView();
		System.out.println("home2");
		mav.setViewName("home2");
		return mav;
	}
}
