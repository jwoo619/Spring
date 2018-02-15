package com.javalec.ex;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping("/member/memberView")
	public String viewMemver(Model model , HttpServletRequest httpServletRequest ){
		
		String id = httpServletRequest.getParameter("id");
		String pw = httpServletRequest.getParameter("pw");
		
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		
		return "/member/memberView";
	}
	
	@RequestMapping("/board/checkId")
	public String checkId(@RequestParam("id") String id , @RequestParam("pw") int pw , Model model){
		
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		
		return "board/checkId";
	}
	
	@RequestMapping("/member/join")
	public String joinData(Member member){
		
		return "member/join";
	}
	
	@RequestMapping("/student/{student1}/{student2}")
	public String student(@PathVariable String student1 , @PathVariable String student2 , Model model){
		
		model.addAttribute("student1", student1);
		model.addAttribute("student2", student2);
		
		
		
		return "/student/studentView";
	}
	
	
	
}