package com.etiicos.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.etiicos.customeEntity.OPDCustomEntity;
import com.etiicos.dataObject.OPDRequestDataObject;
import com.etiicos.dataObject.RejectDataObject;
import com.etiicos.entity.EtiicosLogin;
import com.etiicos.etiicosServices.OPDServices;

@RestController
public class OPDSearchController {

	
	@Autowired
	private OPDServices etiicosServices;
	
	
	@PostMapping("/otpauth")
	private OPDCustomEntity registration(String gmail,HttpSession session)
	{
		 session.setAttribute("etiicosadmingmail",gmail);
		 
		 String etiicosAdminGmail = (String) session.getAttribute("etiicosadmingmail");
			 
		 return etiicosServices.otpHTMLTag(etiicosAdminGmail);
		
	}
	
	@PostMapping("/loginauth")
	private OPDCustomEntity loginAuthOTP(Integer otp,HttpSession session)
	{
		session.setAttribute("otp", otp);
		
		Integer otpAuth = (Integer) session.getAttribute("otp");
		
		return etiicosServices.loginAuthOTPVerify(otpAuth,session);
		
	}
	
	@PostMapping("/innerprocess/etiicos/request/")
	public OPDCustomEntity requestTEtiicosData(OPDRequestDataObject requestDataObject,HttpSession session)
	{
		 session.setAttribute("requestetiicos",requestDataObject);
		 OPDRequestDataObject etiicosRequestDetails = (OPDRequestDataObject) session.getAttribute("requestetiicos");
	
		 return etiicosServices.requestEtiicosDataUpdating(etiicosRequestDetails);
	}
	
	@GetMapping("/innerprocess/etiicos/dashboardrequestdatacount/")
	public OPDCustomEntity DashboardRequestAllDataCounts(HttpSession session)
	{
	     	return etiicosServices.dashboardCallRequestDatasCount(session);
	}
	 
	@GetMapping("/innerprocess/etiicos/dashboardrequestdata/")
	public OPDCustomEntity DashboardRequestAllData(HttpSession session)
	{
	     	return etiicosServices.dashboardCallRequestDatas(session);
	}
	
	@GetMapping("/innerprocess/etiicos/pendingrequestdata/")
	public OPDCustomEntity PendingRequestDatas(HttpSession session)
	{
     	return etiicosServices.pendingCallRequestDatas(session);
	}
	
	@GetMapping("/innerprocess/etiicos/approvedrequestdata/")
	public OPDCustomEntity approvalRequestDatas(HttpSession session)
	{
     	return etiicosServices.approvedCallRequestDatas(session);
	}
	
	@GetMapping("/innerprocess/etiicos/rejectListrequestdata/")
	public OPDCustomEntity rejectListRequestDatas(HttpSession session)
	{
     	return etiicosServices.rejectListAllCallRequest(session);
	}
	
	@PostMapping("/innerprocess/etiicos/rejectrequestdata/")
	public OPDCustomEntity rejectRequestDatas(RejectDataObject rejectReson,HttpSession session)
	{
    	session.setAttribute("rejectgmail", rejectReson);
    	RejectDataObject rejectGmail = (RejectDataObject) session.getAttribute("rejectgmail");
	  	
     	return etiicosServices.rejectCallRequest(rejectGmail);
	}
	
	@PostMapping("/innerprocess/etiicos/approvalrejectrequestdata/")
	public OPDCustomEntity approvalRejectRequest(RejectDataObject rejectReson,HttpSession session)
	{
    	session.setAttribute("approvalrejecthospitalID", rejectReson);
    	RejectDataObject approvalRejecthospitalID = (RejectDataObject) session.getAttribute("approvalrejecthospitalID");
	  	
     	return etiicosServices.approvalRejectCallRequest(approvalRejecthospitalID);
	}
	
	@PostMapping("/innerprocess/etiicos/rejectapprovalrequestdata/")
	public OPDCustomEntity rejectApprovalRequest(String gmail,HttpSession session)
	{
    	session.setAttribute("rejectapprovalgmail", gmail);
	    String rejectApprovalGmail = (String) session.getAttribute("rejectapprovalgmail");
	 	
     	return etiicosServices.rejectApprovalCallRequest(rejectApprovalGmail);
	}
	
	@GetMapping("innerprocess/etiicos/approvaldownloard")
	private OPDCustomEntity approvalExcelGenerator(HttpSession session)
	{
		return etiicosServices.approvalExcelDownloader(session);
	}
	
	@GetMapping("innerprocess/etiicos/rejectdownloard")
	private OPDCustomEntity rejectExcelGenerator(HttpSession session)
	{
		return etiicosServices.rejectExcelDownloader(session);
	}
	
}
