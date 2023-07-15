package com.etiicos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OPDViewController {

	
	@RequestMapping("/login")
	public String registerEtiicos(HttpSession session)
	{
	String loginAuth = (String)	session.getAttribute("loginAuthcert");
	
	if(loginAuth != null && loginAuth.equals("loginAuthL66M*P))"))
	{
		return "etiicosloginauth";
	}
	else
	{
		return "redirect:/";
	}
	}
	
	@RequestMapping("/")
	public String loginEtiicos()
	{
		Authentication authentication =	SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
		{
		return "etiicoslogin";
		}
		else
		{
			return "redirect:/etiicosHome";
		}
	}
	
	@RequestMapping("/etiicosHome")
	public String etiicosHome()
	{
	return "etiicosviewhome";
	}
	
	@RequestMapping("/Dashboard")
	public String etiicosUserDashboard()
	{
	return "dashboardOPD";
	}
	
	@RequestMapping("/Pending")
	public String etiicosUserBending()
	{
	return "pening";
	}
	
	@RequestMapping("/Approved")
	public String etiicosUserApproved()
	{
	return "approved";
	}
	
	@RequestMapping("/Reject")
	public String etiicosUserReject()
	{
	return "reject";
	}
	
	
}
