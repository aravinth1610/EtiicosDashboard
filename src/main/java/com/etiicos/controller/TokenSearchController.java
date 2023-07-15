package com.etiicos.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiicos.customeEntity.OPDCustomEntity;
import com.etiicos.dataObject.OPDRequestDataObject;
import com.etiicos.dataObject.RejectDataObject;
import com.etiicos.etiicosServices.TokenServices;

@RestController
public class TokenSearchController {

	@Autowired
	private TokenServices tokenServiceData;
	
	@GetMapping("/innerprocess/etiicos/tokendashboardrequestdatacount/")
	public OPDCustomEntity tokenDashboardRequestAllDataCounts(HttpSession session)
	{
	     	return tokenServiceData.TokenCallRequestDatasCount(session);
	}
	
	@GetMapping("/innerprocess/etiicos/tokendashboardrequestdata/")
	public OPDCustomEntity tokenDashboardRequestAllData(HttpSession session)
	{
	     	return tokenServiceData.tokenDashboardCallRequestDatas(session);
	}
	
	@GetMapping("/innerprocess/etiicos/tokenpendingrequestdata/")
	public OPDCustomEntity PendingRequestDatas(HttpSession session)
	{
     	return tokenServiceData.tokenPendingCallRequestDatas(session);
	}
	
	@GetMapping("/innerprocess/etiicos/tokenapprovedrequestdata/")
	public OPDCustomEntity approvalRequestDatas(HttpSession session)
	{
     	return tokenServiceData.tokenapprovalCallRequestDatas(session);
	}
	
	@PostMapping("/innerprocess/etiicos/tokenrequest/")
	public OPDCustomEntity requestTEtiicosData(OPDRequestDataObject requestDataObject,HttpSession session)
	{
		 session.setAttribute("tokenrequestetiicos",requestDataObject);
		 OPDRequestDataObject etiicosRequestDetails = (OPDRequestDataObject) session.getAttribute("tokenrequestetiicos");
	
		 return tokenServiceData.tokenRequestEtiicosDataUpdating(etiicosRequestDetails);
	}
	
	@GetMapping("/innerprocess/etiicos/tokenrejectListrequestdata/")
	public OPDCustomEntity rejectListRequestDatas(HttpSession session)
	{
     	return tokenServiceData.rejectListAllCallRequest(session);
	}
	
	@PostMapping("/innerprocess/etiicos/tokenrejectrequestdata/")
	public OPDCustomEntity rejectRequestDatas(RejectDataObject rejectReason,HttpSession session)
	{
    	session.setAttribute("tokenrejectgmail", rejectReason);
    	RejectDataObject tokenRejectGmail = (RejectDataObject) session.getAttribute("tokenrejectgmail");
	  	
     	return tokenServiceData.rejectCallRequest(tokenRejectGmail);
	}
	
	@PostMapping("/innerprocess/etiicos/tokenapprovalrejectrequestdata/")
	public OPDCustomEntity approvalRejectRequest(RejectDataObject rejectReason,HttpSession session)
	{
    	session.setAttribute("tokenapprovalrejecthospitalID", rejectReason);
    	RejectDataObject tokenApprovalRejecthospitalID = (RejectDataObject) session.getAttribute("tokenapprovalrejecthospitalID");
	   
     	return tokenServiceData.approvalRejectCallRequest(tokenApprovalRejecthospitalID);
	}
	
	@PostMapping("/innerprocess/etiicos/tokenrejectapprovalrequestdata/")
	public OPDCustomEntity rejectApprovalRequest(String gmail,HttpSession session)
	{
    	session.setAttribute("tokenrejectapprovalgmail", gmail);
	    String tokenRejectApprovalGmail = (String) session.getAttribute("tokenrejectapprovalgmail");
	   
     	return tokenServiceData.rejectApprovalCallRequest(tokenRejectApprovalGmail);
	}
	
	@GetMapping("innerprocess/etiicos/tokenapprovaldownloard")
	private OPDCustomEntity approvalExcelGenerator(HttpSession session)
	{
		return tokenServiceData.tokenApprovalExcelDownloader(session);
	}
	
	@GetMapping("innerprocess/etiicos/tokenrejectdownloard")
	private OPDCustomEntity rejectExcelGenerator(HttpSession session)
	{
		return tokenServiceData.tokenRejectExcelDownloader(session);
	}
	
}
