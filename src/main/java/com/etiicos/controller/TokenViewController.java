package com.etiicos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TokenViewController {

	@RequestMapping("/departments")
	private String department()
	{
		return "department";
	}
	
	@RequestMapping("/Token-Dashboard")
	private String TokenDashboard()
	{
		return "tokendashboard";
	}
	
	@RequestMapping("/Token-Pending")
	private String TokenUserBending()
	{
		return "tokenpening";
	}
	
	@RequestMapping("/Token-Approved")
	private String TokenApproval()
	{
		return "tokenapproval";
	}
	
	@RequestMapping("/Token-Reject")
	private String TokenUserReject()
	{
		return "tokenreject";
	}
	
}
