package com.etiicos.etiicosServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.etiicos.customeEntity.OPDCustomEntity;
import com.etiicos.dataObject.OPDRequestDataObject;
import com.etiicos.dataObject.RejectDataObject;
import com.etiicos.entity.OPDApprovalRequest;
import com.etiicos.entity.OPDPendingApprovalRequest;
import com.etiicos.entity.OPDRejectsRequest;
import com.etiicos.entity.TokenApprovalRequest;
import com.etiicos.entity.TokenPendingApprovalRequest;
import com.etiicos.entity.TokenRejectRequest;
import com.etiicos.etiicosCustomeDB.DBOperator;
import com.etiicos.properties.PropertiesClassPath;
import com.etiicos.repository.TokenApprovalRequestRepository;
import com.etiicos.repository.TokenPendingApprovalRequestRepository;
import com.etiicos.repository.TokenRejectRequestRepository;

@RestController
public class TokenServices {

	@Autowired
	TokenPendingApprovalRequestRepository TokenPendingApprovalReqRepoData;
	
	@Autowired
	TokenApprovalRequestRepository tokenApprovalRequestRepoData;
	
	@Autowired
	TokenRejectRequestRepository tokenRejectRequestRepoData;
	 
	@Autowired
	PropertiesClassPath PropertiesFiles;
	
	public OPDCustomEntity tokenRequestEtiicosDataUpdating(OPDRequestDataObject requestData)
	{
		try
		{
			
		TokenApprovalRequest registeredEntity = new TokenApprovalRequest();
		Integer requestAccepted = TokenPendingApprovalReqRepoData.updaterequest(requestData.getRequest(), requestData.getGmail());
		SimpleDateFormat dateTime=new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	    String requestTime=dateTime.format(new Date());
		
	    TokenPendingApprovalRequest  tokenRequestCalls= TokenPendingApprovalReqRepoData.findByGmail(requestData.getGmail());
		if(requestAccepted==1)
		{
			
			registeredEntity.setUserId(tokenRequestCalls.getUserId());
			registeredEntity.setGmail(tokenRequestCalls.getGmail());
			registeredEntity.setHospitalName(tokenRequestCalls.getHospitalName());
			registeredEntity.setHospitalID(tokenRequestCalls.getHospitalID());
			TokenPendingApprovalReqRepoData.updaterequestTime(requestTime,requestData.getGmail());
			registeredEntity.setRequestDateTime(requestTime);
			
			tokenApprovalRequestRepoData.save(registeredEntity);
			
			List<String> tokenValueInsertion = new ArrayList<String>();
			
			tokenValueInsertion.add("user_id");
			tokenValueInsertion.add("city");
			tokenValueInsertion.add("request_date_time");
			tokenValueInsertion.add("gmail");
			tokenValueInsertion.add("hospital_name");
			tokenValueInsertion.add("hospital_id");
			tokenValueInsertion.add("password");
			tokenValueInsertion.add("state");
			tokenValueInsertion.add("address");
			//tokenValueInsertion.add("payment_status");
			//tokenValueInsertion.add("next_payment_date");
			
			DBOperator OPDCustomeDataBase = new DBOperator(PropertiesFiles.getRequestHost(),PropertiesFiles.getRequestPort(),PropertiesFiles.getRequestUsername(),PropertiesFiles.getRequestPassword()); 
			
			
			Boolean OPDInserted = OPDCustomeDataBase.insertTableValues("tokenaggregatedspace","recipient_register",tokenValueInsertion,tokenRequestCalls.getUserId(),tokenRequestCalls.getCity(),requestTime,tokenRequestCalls.getGmail(),tokenRequestCalls.getHospitalName(),tokenRequestCalls.getHospitalID(),tokenRequestCalls.getPassword(),tokenRequestCalls.getState(),tokenRequestCalls.getAddress());
			
			if(OPDInserted)
			{
			Boolean isDatabaseCreated = OPDCustomeDataBase.createDatabase(tokenRequestCalls.getHospitalID());
			if(isDatabaseCreated)
			{
	      	Boolean tableCallLogCreated =	OPDCustomeDataBase.createOPDTable(tokenRequestCalls.getHospitalID(),tokenRequestCalls.getHospitalID().concat("_calllog"),"patient_id","varchar(500) NOT NULL","patient_name","varchar(500)","token_no","int","room_no","varchar(255)","starttime","varchar(50)","endtime","varchar(50)","duration","varchar(50)","department","varchar(255),PRIMARY KEY (starttime)");
			Boolean tablePatientDetailsCreated = OPDCustomeDataBase.createOPDTable(tokenRequestCalls.getHospitalID(),tokenRequestCalls.getHospitalID().concat("_patientdetails"),"callno","varchar(255) NOT NULL","patient_id","varchar(500)","patient_name","varchar(500)","age","varchar(50)","sex","varchar(50)","token_no","int","room_no","varchar(255)","rupees","varchar(255)","phone_no","varchar(20)","date","varchar(50)","time","varchar(50)","department","varchar(255)","register_status","varchar(50)","audio_status","varchar(25)","status","bit(1)","call_completed","varchar(30)","booked_status","varchar(50)","viewed_status","varchar(30),PRIMARY KEY (callno)");
			Boolean RejecttablePatientDetailsCreated = OPDCustomeDataBase.createOPDTable(tokenRequestCalls.getHospitalID(),tokenRequestCalls.getHospitalID().concat("_rejectpatientdetails"),"patient_id","varchar(255) NOT NULL","patient_name","varchar(500)","age","varchar(50)","sex","varchar(50)","phone_no","varchar(20)","date","varchar(50)","time","varchar(50)","department","varchar(255)","register_status","varchar(50)","description","varchar(50),PRIMARY KEY (patient_id)");
			Boolean tableSettingCreated = OPDCustomeDataBase.createOPDTable(tokenRequestCalls.getHospitalID(),tokenRequestCalls.getHospitalID().concat("_setting"),"callno","varchar(255) NOT NULL","fieldname","varchar(255),PRIMARY KEY (callno)"); 
            Boolean extraPatientDetailsCreated = OPDCustomeDataBase.createOPDTable(tokenRequestCalls.getHospitalID(),tokenRequestCalls.getHospitalID().concat("_extrapatientdetails"),"tokencallno","varchar(255) NOT NULL","callno","varchar(255)","extfieldname","varchar(255)","extfieldvalue","varchar(255),PRIMARY KEY (tokencallno)"); 
            Boolean activestatusPage = OPDCustomeDataBase.createOPDTable(tokenRequestCalls.getHospitalID(),tokenRequestCalls.getHospitalID().concat("_activepage"),"department","varchar(255)","fromdate","varchar(255)","todate","varchar(255),PRIMARY KEY (department)"); 
            
            if(tableCallLogCreated && tablePatientDetailsCreated && tableSettingCreated && extraPatientDetailsCreated && activestatusPage && RejecttablePatientDetailsCreated)
			{
            	
            	List<String> activePage = new ArrayList<String>();
        		activePage.add("department");
        		activePage.add("fromdate");
        		activePage.add("todate");
        		
        		OPDCustomeDataBase.insertTableValues(tokenRequestCalls.getHospitalID(),tokenRequestCalls.getHospitalID().concat("_activepage"), activePage,"","","");

           	
				return new OPDCustomEntity(200,"success");
			} else{ return new OPDCustomEntity(400,"non-success"); }
			  }else{ return new OPDCustomEntity(400,"non-success"); }
			}else{ return new OPDCustomEntity(400,"non-success"); }
		        }
	    	else
	    	{
	    		return new OPDCustomEntity(101,"Wating for Request");
	    	}
	
		}catch (Exception e) {
			System.out.println("----Token RequestEtiicos Updated Error ----");
			return new OPDCustomEntity(400,"non-success");
		}
		}
	
	public OPDCustomEntity TokenCallRequestDatasCount(HttpSession session)
	 {
		 try {
			 
		   Integer pendingHospitalCount = TokenPendingApprovalReqRepoData.pendingHospitalCount(false);
	 	   Integer approvedHospitalCount = TokenPendingApprovalReqRepoData.pendingHospitalCount(true);
	 	    
	 	   
	 	   Map<String,Object> dashboardRequestAllDatasCounts = new LinkedHashMap<String, Object>();
	 	    
	 	  dashboardRequestAllDatasCounts.put("tokenpendinghospitalcount", pendingHospitalCount);
	 	 dashboardRequestAllDatasCounts.put("tokenapprovedghospitalcount", approvedHospitalCount);
	 	dashboardRequestAllDatasCounts.put("tokenrejecthospitalcount",tokenRejectRequestRepoData.rejectCallCount());
	 	
	 	session.setAttribute("tokendashboardrequestalldatasCounts", dashboardRequestAllDatasCounts);
  	
 	    Object tokenDashboadRequestDatasCount =session.getAttribute("tokendashboardrequestalldatasCounts");
 	 
  	return new OPDCustomEntity(200,tokenDashboadRequestDatasCount,"success");
	    }
	    catch (Exception e) {
	    	System.out.println("---- Token Dashboard Count Request Error ----");
	    	return new OPDCustomEntity(400,"non-success");
		}
	 	
	 }
	
	public OPDCustomEntity tokenDashboardCallRequestDatas(HttpSession session)
	 {
   	 
   List<String> pendingGmail = new ArrayList<String>();
  	List<String> pendingHospital = new ArrayList<String>();
  	List<String> pendingHospitalID = new ArrayList<String>();
  	List<String> pendingCity = new ArrayList<String>();
  	List<String> pendingState = new ArrayList<String>();
  	List<String> pendingRequestTime = new ArrayList<String>();
  	List<String> approvedGmail = new ArrayList<String>();
  	List<String> approvedCity = new ArrayList<String>();
  	List<String> approvedState = new ArrayList<String>();
  	List<String> approvedHospital = new ArrayList<String>();
  	List<String> approvedRequestTime = new ArrayList<String>();
  	List<String> approvedHospitalID = new ArrayList<String>();
  	List<String> rejectedGmail = new ArrayList<String>();
   List<String> rejectedHospital = new ArrayList<String>();
   List<String> rejectedRequestTime = new ArrayList<String>();
   List<String> rejectedState = new ArrayList<String>();
   List<String> rejectedCity = new ArrayList<String>();
   
    List<String> rejectedHospitalID = new ArrayList<String>();
  	List<TokenPendingApprovalRequest> pendingCallData = TokenPendingApprovalReqRepoData.findByRequestStatus(false);
  	List<TokenPendingApprovalRequest> approvedCallData = TokenPendingApprovalReqRepoData.findByRequestStatus(true);
    List<TokenRejectRequest>  rejectsUserData= tokenRejectRequestRepoData.findAll();
	   
	    try {
  	
  	for(TokenPendingApprovalRequest pendingRequest : pendingCallData)
  	{
  		pendingGmail.add(pendingRequest.getGmail());
  		pendingHospital.add(pendingRequest.getHospitalName());
  		pendingRequestTime.add(pendingRequest.getRequestDateTime());
  		pendingHospitalID.add(pendingRequest.getHospitalID());
  		pendingState.add(pendingRequest.getState());
  		pendingCity.add(pendingRequest.getCity());
  		
  		
  	}
  
  	for(TokenPendingApprovalRequest approvedRequest : approvedCallData)
  	{
  		approvedGmail.add(approvedRequest.getGmail());
  		approvedHospital.add(approvedRequest.getHospitalName());
  		approvedRequestTime.add(approvedRequest.getRequestDateTime());
  		approvedHospitalID.add(approvedRequest.getHospitalID());
  		approvedState.add(approvedRequest.getState());
  		approvedCity.add(approvedRequest.getCity());
  	}
  	
  	for(TokenRejectRequest rejectsUser : rejectsUserData)
  	{
  		rejectedGmail.add(rejectsUser.getGmail());
  		rejectedHospital.add(rejectsUser.getHospitalName());
  		rejectedRequestTime.add(rejectsUser.getRequestDateTime());
  		rejectedHospitalID.add(rejectsUser.getHospitalID());
  		rejectedState.add(rejectsUser.getState());
  		rejectedCity.add(rejectsUser.getCity());
  	}
  	
  	Map<String,Object> dashboardRequestAllDatas = new LinkedHashMap<String, Object>();
  	dashboardRequestAllDatas.put("tokenpendinggmail", pendingGmail);
  	dashboardRequestAllDatas.put("tokenpendinghospialID", pendingHospitalID);
  	dashboardRequestAllDatas.put("tokenpendinghospial", pendingHospital);
  	dashboardRequestAllDatas.put("tokenpendingstate", pendingState);
  	dashboardRequestAllDatas.put("tokenpendingcity", pendingCity);
  	dashboardRequestAllDatas.put("tokenpendingrequesttime", pendingRequestTime);
  	dashboardRequestAllDatas.put("tokenapprovedgmail", approvedGmail);
  	dashboardRequestAllDatas.put("tokenapprovedhospialID", approvedHospitalID);
  	dashboardRequestAllDatas.put("tokenapprovedhospial", approvedHospital);
  	dashboardRequestAllDatas.put("tokenapprovedstate", approvedState);
  	dashboardRequestAllDatas.put("tokenapprovedcity", approvedCity);
  	dashboardRequestAllDatas.put("tokenapprovedrequesttime", approvedRequestTime);
  	dashboardRequestAllDatas.put("tokenrejectgmail", rejectedGmail);
  	dashboardRequestAllDatas.put("tokenrejecthospialID", rejectedHospitalID);
  	dashboardRequestAllDatas.put("tokenrejecthospial", rejectedHospital);
  	dashboardRequestAllDatas.put("tokenrejectrequesttime", rejectedRequestTime);
  	dashboardRequestAllDatas.put("tokenrejectstate", rejectedState);
  	dashboardRequestAllDatas.put("tokenrejectcity", rejectedCity);
  	
  	
  	session.setAttribute("tokendashboardrequestalldatas", dashboardRequestAllDatas);
  	
  	 Object tokenDashboadRequestDatas =session.getAttribute("tokendashboardrequestalldatas");
  	
  	 
   	return new OPDCustomEntity(200,tokenDashboadRequestDatas,"success");
	    }
	    catch (Exception e) {
	    	System.out.println("---- Token Dashboard Request Error ----");
	    	return new OPDCustomEntity(400,"non-success");
		}
	   }
	
	public OPDCustomEntity tokenapprovalCallRequestDatas(HttpSession session)
	 {
    	List<String> pendingGmail = new ArrayList<String>();
   	List<String> pendingHospital = new ArrayList<String>();
   	List<String> pendingRequestTime = new ArrayList<String>();
   	List<String> pendingHospitalID = new ArrayList<String>();
   	List<String> pendingState = new ArrayList<String>();
   	List<String> pendingCity = new ArrayList<String>();
	List<String> approvedTime = new ArrayList<String>();
   	
   	try {
  	 List<TokenPendingApprovalRequest> pendingCallData = TokenPendingApprovalReqRepoData.findByRequestStatus(true);
  	 
  	 for(TokenPendingApprovalRequest pendingRequest : pendingCallData)
   	{
   		pendingGmail.add(pendingRequest.getGmail());
   		pendingHospital.add(pendingRequest.getHospitalName());
   		pendingHospitalID.add(pendingRequest.getHospitalID());
   		pendingState.add(pendingRequest.getState());
   		pendingCity.add(pendingRequest.getCity());
   		pendingRequestTime.add(pendingRequest.getRequestDateTime());
   		approvedTime.add(pendingRequest.getApprovedTime());
   	}
  	 
  	 Map<String,Object> tokenapprovalRequestAllDatas = new LinkedHashMap<String, Object>();
  	tokenapprovalRequestAllDatas.put("tokenapprovalgmail", pendingGmail);
  	tokenapprovalRequestAllDatas.put("tokenapprovalhospial", pendingHospital);
  	tokenapprovalRequestAllDatas.put("tokenapprovalhospialID", pendingHospitalID);
  	tokenapprovalRequestAllDatas.put("tokenapprovalstate", pendingState);
  	tokenapprovalRequestAllDatas.put("tokenapprovalcity", pendingCity);
  	tokenapprovalRequestAllDatas.put("tokenapprovalrequesttime", pendingRequestTime);
  	tokenapprovalRequestAllDatas.put("tokenapprovaltime", approvedTime);
   	
   	session.setAttribute("tokenapprovalrequestdatas", tokenapprovalRequestAllDatas);
   	
   	Object approvalRequestDatasAttribute = session.getAttribute("tokenapprovalrequestdatas");
   	
   	
   	return new OPDCustomEntity(200,approvalRequestDatasAttribute,"success");
   	
   	}catch (Exception e) {
   		System.out.println("---- Token pendingcall Request Error ----");
   		return new OPDCustomEntity(400,"non-success");
		}
	 }
	
	public OPDCustomEntity tokenPendingCallRequestDatas(HttpSession session)
	 {
     	List<String> pendingGmail = new ArrayList<String>();
    	List<String> pendingHospital = new ArrayList<String>();
    	List<String> pendingRequestTime = new ArrayList<String>();
    	List<String> pendingHospitalID = new ArrayList<String>();
    	List<String> pendingState = new ArrayList<String>();
    	List<String> pendingCity = new ArrayList<String>();
    	
    	try {
   	 List<TokenPendingApprovalRequest> pendingCallData = TokenPendingApprovalReqRepoData.findByRequestStatus(false);
   	 
   	 for(TokenPendingApprovalRequest pendingRequest : pendingCallData)
    	{
    		pendingGmail.add(pendingRequest.getGmail());
    		pendingHospital.add(pendingRequest.getHospitalName());
    		pendingHospitalID.add(pendingRequest.getHospitalID());
    		pendingState.add(pendingRequest.getState());
    		pendingCity.add(pendingRequest.getCity());
    		pendingRequestTime.add(pendingRequest.getRequestDateTime());
    	}
   	 
   	 Map<String,Object> tokenPendingRequestAllDatas = new LinkedHashMap<String, Object>();
   	tokenPendingRequestAllDatas.put("tokenpendinggmail", pendingGmail);
   	tokenPendingRequestAllDatas.put("tokenpendinghospial", pendingHospital);
   	tokenPendingRequestAllDatas.put("tokenpendinghospialID", pendingHospitalID);
   	tokenPendingRequestAllDatas.put("tokenpendingstate", pendingState);
   	tokenPendingRequestAllDatas.put("tokenpendingcity", pendingCity);
   	tokenPendingRequestAllDatas.put("tokenpendingrequesttime", pendingRequestTime);
    	
    	session.setAttribute("tokenpendingrequestdatas", tokenPendingRequestAllDatas);
    	
    	Object pendingRequestDatasAttribute = session.getAttribute("tokenpendingrequestdatas");
    	
    	
    	return new OPDCustomEntity(200,pendingRequestDatasAttribute,"success");
    	
    	}catch (Exception e) {
    		System.out.println("---- Token pendingcall Request Error ----");
    		return new OPDCustomEntity(400,"non-success");
		}
	 }
	
	public OPDCustomEntity rejectCallRequest(RejectDataObject rejectData)
    {
   	
   	SimpleDateFormat dateTime=new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	    String requestTime=dateTime.format(new Date());
	    
	    try {
	    	TokenPendingApprovalRequest requestRejctsList = TokenPendingApprovalReqRepoData.findByGmail(rejectData.getHospitalID());
    	Integer rejectedRequest  = TokenPendingApprovalReqRepoData.deleteByGmail(rejectData.getHospitalID());
    	TokenRejectRequest requestRejectData = new TokenRejectRequest();
   	if(rejectedRequest==1)
   	{
   		requestRejectData.setGmail(requestRejctsList.getGmail());
   		
   		requestRejectData.setUserId(requestRejctsList.getUserId());
   		requestRejectData.setGmail(requestRejctsList.getGmail());
   		requestRejectData.setHospitalName(requestRejctsList.getHospitalName());
   		requestRejectData.setHospitalID(requestRejctsList.getHospitalID());
   		requestRejectData.setPassword(requestRejctsList.getPassword());
   		requestRejectData.setReject_time(requestTime);
   		requestRejectData.setState(requestRejctsList.getState());
   		requestRejectData.setCity(requestRejctsList.getCity());
   		requestRejectData.setAddress(requestRejctsList.getAddress());
   		requestRejectData.setRequestDateTime(requestTime);
   		requestRejectData.setRequestStatus(requestRejctsList.getRequestStatus());
   		requestRejectData.setReason(rejectData.getReason());
   		//requestRejectData.setPaymentStatus(requestRejctsList.getPaymentStatus());
   		//requestRejectData.setNextPaymentDate(requestRejctsList.getNextPaymentDate());
   		
   		tokenRejectRequestRepoData.save(requestRejectData);
   		
   		return new OPDCustomEntity(200,"success");
   	}
   	else
   	{
   		return new OPDCustomEntity(400,"non-success");
   	}
	    }catch (Exception e) {
	    	System.out.println("---- Token Rejects Call Request Error ----");
	    	return new OPDCustomEntity(400,"non-success");
		}
    }
	
	public OPDCustomEntity rejectListAllCallRequest(HttpSession session)
    {
   	    List<String> rejectedGmail = new ArrayList<String>();
     	List<String> rejectedHospital = new ArrayList<String>();
     	List<String> rejectedRequestTime = new ArrayList<String>();
     	List<String> rejectedHospitalID = new ArrayList<String>();
     	List<String> rejectedCity = new ArrayList<String>();
     	List<String> rejectedState = new ArrayList<String>();
     	List<String> rejectedTime = new ArrayList<String>();
     	List<String> rejectedReason = new ArrayList<String>();
     	
     	
     	try {
   	List<TokenRejectRequest>  rejectsUserData= tokenRejectRequestRepoData.findAll();
   	
   	for(TokenRejectRequest rejectsUser : rejectsUserData)
   	{
   		rejectedGmail.add(rejectsUser.getGmail());
  		rejectedHospital.add(rejectsUser.getHospitalName());
  		rejectedRequestTime.add(rejectsUser.getRequestDateTime());
  		rejectedHospitalID.add(rejectsUser.getHospitalID());
  		rejectedState.add(rejectsUser.getState());
  		rejectedCity.add(rejectsUser.getCity());
  		rejectedTime.add(rejectsUser.getReject_time());
  		rejectedReason.add(rejectsUser.getReason());
   	}
   	
   	Map<String,Object> rejectRequestAllDatas = new LinkedHashMap<String, Object>();
   	rejectRequestAllDatas.put("tokenrejectgmail", rejectedGmail);
   	rejectRequestAllDatas.put("tokenrejecthospial", rejectedHospital);
   	rejectRequestAllDatas.put("tokenrejecthospialID", rejectedHospitalID);
   	rejectRequestAllDatas.put("tokenrejectstate", rejectedState);
   	rejectRequestAllDatas.put("tokenrejectcity", rejectedCity);
   	rejectRequestAllDatas.put("tokenrejectrequesttime", rejectedRequestTime);
   	rejectRequestAllDatas.put("rejecttime", rejectedTime);
   	rejectRequestAllDatas.put("rejectreason",rejectedReason);
    	
   	session.setAttribute("tokenrejectRequestdata", rejectRequestAllDatas);
   	
   	Object tokenRejectRequestDataAttribute = session.getAttribute("tokenrejectRequestdata");
   	
   	
    	return new OPDCustomEntity(200,tokenRejectRequestDataAttribute,"success");
    	
     	}catch (Exception e) {
     		System.out.println("---- Token Reject Request All Datas Error ----");
     		return new OPDCustomEntity(400,"non-success");
		}
    }

	 public OPDCustomEntity approvalRejectCallRequest(RejectDataObject rejectReason)
    {
		    SimpleDateFormat dateTime=new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	 	    String requestTime=dateTime.format(new Date());
	 	    
	 	    try {
	 	   	DBOperator tokenCustomeDataBase = new DBOperator(PropertiesFiles.getRequestHost(),PropertiesFiles.getRequestPort(),PropertiesFiles.getRequestUsername(),PropertiesFiles.getRequestPassword()); 
	 		
	 	   TokenPendingApprovalRequest requestRejctsList = TokenPendingApprovalReqRepoData.findByHospitalID(rejectReason.getHospitalID());
	     	
	 	  TokenRejectRequest requestRejectData = new TokenRejectRequest();
	    	
	    	if(requestRejctsList!=null)
	    	{
	    		
	    		
	    		requestRejectData.setUserId(requestRejctsList.getUserId());
	    		requestRejectData.setGmail(requestRejctsList.getGmail());
	    		requestRejectData.setHospitalName(requestRejctsList.getHospitalName());
	    		requestRejectData.setHospitalID(requestRejctsList.getHospitalID());
	    		requestRejectData.setPassword(requestRejctsList.getPassword());
	    		requestRejectData.setReject_time(requestTime);
	    		requestRejectData.setRequestDateTime(requestRejctsList.getRequestDateTime());
	    		requestRejectData.setRequestStatus(requestRejctsList.getRequestStatus());
	    		requestRejectData.setCity(requestRejctsList.getCity());
	    		requestRejectData.setState(requestRejctsList.getState());
	    		requestRejectData.setReason(rejectReason.getReason());
	    		requestRejectData.setAddress(requestRejctsList.getAddress());
	    		//requestRejectData.setPaymentStatus(requestRejctsList.getPaymentStatus());
	    		//requestRejectData.setNextPaymentDate(requestRejctsList.getNextPaymentDate());
	    		
	    		tokenRejectRequestRepoData.save(requestRejectData);
	    		Integer rejectedRequest  = TokenPendingApprovalReqRepoData.deleteByHospitalId(rejectReason.getHospitalID());
	    		Boolean approvalRecipientReject = tokenCustomeDataBase.deleteTableValues("tokenaggregatedspace","recipient_register","hospital_id",rejectReason.getHospitalID());
	    		Boolean approvalTokenLinkReject = tokenCustomeDataBase.updateTableValues("tokenaggregatedspace","token_link","hospital_id",rejectReason.getHospitalID(),"hospital_id",rejectReason.getHospitalID().concat("_$Etiicos"));

	    	if(rejectedRequest==1 && approvalRecipientReject && approvalTokenLinkReject)
	    	{
	    		return new OPDCustomEntity(200,"success");
	    	} else {return new OPDCustomEntity(400,"non-success");}
	    	}
	    	else
	    	{
	    		return new OPDCustomEntity(400,"non-success");
	    	}
	 	    }catch (Exception e) {
	 	    	System.out.println("----Token Rejects Call Request Error ----");
	 	    	return new OPDCustomEntity(400,"non-success");
			}
    }

	 public OPDCustomEntity rejectApprovalCallRequest(String userGmail)
     {
	
		    SimpleDateFormat dateTime=new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	 	    String requestTime=dateTime.format(new Date());

	 	    DBOperator tokenCustomeDataBase = new DBOperator(PropertiesFiles.getRequestHost(),PropertiesFiles.getRequestPort(),PropertiesFiles.getRequestUsername(),PropertiesFiles.getRequestPassword()); 
	 	   TokenRejectRequest rejectDatas = tokenRejectRequestRepoData.findByGmail(userGmail);
	 	   
	 	    TokenPendingApprovalRequest requestApproval = new TokenPendingApprovalRequest();
		  	
	     	 List<String> tokenValueInsertion = new ArrayList<String>();
			
	     	tokenValueInsertion.add("user_id");
	     	tokenValueInsertion.add("gmail");
	     	tokenValueInsertion.add("hospital_name");
	     	tokenValueInsertion.add("hospital_id");
			tokenValueInsertion.add("password");
			tokenValueInsertion.add("request_date_time");
			tokenValueInsertion.add("city");
			tokenValueInsertion.add("state");
			tokenValueInsertion.add("address");
		//	tokenValueInsertion.add("payment_status");
		//	tokenValueInsertion.add("next_payment_date");
		
	 	    try {
	 	    	
	 	    	if(tokenApprovalRequestRepoData.existsByGmail(userGmail))
	 	    	{
	
	 	    	if(rejectDatas != null)
		    	{
		  requestApproval.setUserId(rejectDatas.getUserId());
	 	  requestApproval.setGmail(rejectDatas.getGmail());
	 	  requestApproval.setHospitalName(rejectDatas.getHospitalName());
	 	  requestApproval.setHospitalID(rejectDatas.getHospitalID());
	 	  requestApproval.setPassword(rejectDatas.getPassword());
	 	  requestApproval.setRequestStatus(true);
	 	  requestApproval.setRequestDateTime(rejectDatas.getRequestDateTime());
	 	  requestApproval.setApprovedTime(requestTime);
	 	  requestApproval.setCity(rejectDatas.getCity());
	 	  requestApproval.setState(rejectDatas.getState());
	 	  requestApproval.setAddress(rejectDatas.getAddress());
	 	 // requestApproval.setPaymentStatus(rejectDatas.getPaymentStatus());
	 	 // requestApproval.setNextPaymentDate(rejectDatas.getNextPaymentDate());
	 	 
	 	 Boolean tokenInserted = tokenCustomeDataBase.insertTableValues("tokenaggregatedspace","recipient_register",tokenValueInsertion,rejectDatas.getUserId(),rejectDatas.getGmail(),rejectDatas.getHospitalName(),rejectDatas.getHospitalID(),rejectDatas.getPassword(),requestTime,rejectDatas.getCity(),rejectDatas.getState(),rejectDatas.getAddress());
	 	
	 	if(tokenInserted)
			{
	 		TokenPendingApprovalReqRepoData.save(requestApproval);
	 		tokenRejectRequestRepoData.deleteByGmail(userGmail);
	  	    tokenCustomeDataBase.updateTableValues("tokenaggregatedspace","token_link","hospital_id",rejectDatas.getHospitalID().concat("_$Etiicos"),"hospital_id",rejectDatas.getHospitalID());

	 		return new OPDCustomEntity(200,"success");
	 	
			}else {return new OPDCustomEntity(400,"non-success");}
	 	
		    	}
		    	else
		    	{
		    		return new OPDCustomEntity(400,"non-success");
		    	}
	 	    	 }
	 	    	 else
	 	    	 {
	 	    		if(rejectDatas != null)
			    	{
			  requestApproval.setUserId(rejectDatas.getUserId());
		 	  requestApproval.setGmail(rejectDatas.getGmail());
		 	  requestApproval.setHospitalName(rejectDatas.getHospitalName());
		 	  requestApproval.setHospitalID(rejectDatas.getHospitalID());
		 	  requestApproval.setPassword(rejectDatas.getPassword());
		 	  requestApproval.setRequestStatus(true);
		 	  requestApproval.setApprovedTime(requestTime);
		 	  requestApproval.setRequestDateTime(rejectDatas.getRequestDateTime());
		 	  requestApproval.setCity(rejectDatas.getCity());
		 	  requestApproval.setState(rejectDatas.getState());		
		 	  requestApproval.setAddress(rejectDatas.getAddress());
		 	 // requestApproval.setPaymentStatus(rejectDatas.getPaymentStatus());
		 	 // requestApproval.setNextPaymentDate(rejectDatas.getNextPaymentDate());
		 	  
	 				Boolean tokenInserted = tokenCustomeDataBase.insertTableValues("tokenaggregatedspace","recipient_register",tokenValueInsertion,rejectDatas.getUserId(),rejectDatas.getGmail(),rejectDatas.getHospitalName(),rejectDatas.getHospitalID(),rejectDatas.getPassword(),requestTime,rejectDatas.getCity(),rejectDatas.getState(),rejectDatas.getAddress());
	 				
	 				if(tokenInserted)
	 				{

	 					TokenApprovalRequest registeredEntity = new TokenApprovalRequest();
	 					
	 					registeredEntity.setUserId(rejectDatas.getUserId());
	 					registeredEntity.setGmail(rejectDatas.getGmail());
	 					registeredEntity.setHospitalName(rejectDatas.getHospitalName());
	 					registeredEntity.setHospitalID(rejectDatas.getHospitalID());       
	 					TokenPendingApprovalReqRepoData.updaterequestTime(requestTime,rejectDatas.getGmail());
	 					registeredEntity.setRequestDateTime(requestTime);
	 					tokenApprovalRequestRepoData.save(registeredEntity);
	 					
	 				Boolean isDatabaseCreated = tokenCustomeDataBase.createDatabase(rejectDatas.getHospitalID());
	 				if(isDatabaseCreated)
	 				{
	 				  	Boolean tableCallLogCreated =	tokenCustomeDataBase.createOPDTable(rejectDatas.getHospitalID(),rejectDatas.getHospitalID().concat("_calllog"),"patient_id","varchar(500) NOT NULL","patient_name","varchar(500)","token_no","int","room_no","varchar(255)","starttime","varchar(50)","endtime","varchar(50)","duration","varchar(50)","department","varchar(255),PRIMARY KEY (starttime)");
	 					Boolean tablePatientDetailsCreated = tokenCustomeDataBase.createOPDTable(rejectDatas.getHospitalID(),rejectDatas.getHospitalID().concat("_patientdetails"),"callno","varchar(255) NOT NULL","patient_id","varchar(500)","patient_name","varchar(500)","age","varchar(50)","sex","varchar(50)","token_no","int","room_no","varchar(255)","rupees","varchar(255)","phone_no","varchar(20)","date","varchar(50)","time","varchar(50)","department","varchar(255)","register_status","varchar(50)","audio_status","varchar(25)","status","bit(1)","call_completed","varchar(30)","booked_status","varchar(50)","viewed_status","varchar(30),PRIMARY KEY (callno)");
	 					Boolean RejecttablePatientDetailsCreated = tokenCustomeDataBase.createOPDTable(rejectDatas.getHospitalID(),rejectDatas.getHospitalID().concat("_rejectpatientdetails"),"patient_id","varchar(255) NOT NULL","patient_name","varchar(500)","age","varchar(50)","sex","varchar(50)","phone_no","varchar(20)","date","varchar(50)","time","varchar(50)","department","varchar(255)","register_status","varchar(50)","description","varchar(50),PRIMARY KEY (patient_id)");
	 					Boolean tableSettingCreated = tokenCustomeDataBase.createOPDTable(rejectDatas.getHospitalID(),rejectDatas.getHospitalID().concat("_setting"),"callno","varchar(255) NOT NULL","fieldname","varchar(255),PRIMARY KEY (callno)"); 
	 		            Boolean extraPatientDetailsCreated = tokenCustomeDataBase.createOPDTable(rejectDatas.getHospitalID(),rejectDatas.getHospitalID().concat("_extrapatientdetails"),"tokencallno","varchar(255) NOT NULL","callno","varchar(255)","extfieldname","varchar(255)","extfieldvalue","varchar(255),PRIMARY KEY (tokencallno)"); 
	 		            Boolean activestatusPage = tokenCustomeDataBase.createOPDTable(rejectDatas.getHospitalID(),rejectDatas.getHospitalID().concat("_activepage"),"department","varchar(255)","fromdate","varchar(255)","todate","varchar(255),PRIMARY KEY (department)"); 
	 			   
	 				if(tableCallLogCreated && tablePatientDetailsCreated && tableSettingCreated && extraPatientDetailsCreated && activestatusPage && RejecttablePatientDetailsCreated)
	 				{
	 	            	List<String> activePage = new ArrayList<String>();
	 	        		activePage.add("department");
	 	        		activePage.add("fromdate");
	 	        		activePage.add("todate");
	 	        		
	 	        		tokenCustomeDataBase.insertTableValues(rejectDatas.getHospitalID(),rejectDatas.getHospitalID().concat("_activepage"), activePage,"","","");
	 					
	 					TokenPendingApprovalReqRepoData.save(requestApproval);
	 					tokenRejectRequestRepoData.deleteByGmail(userGmail);
	 					return new OPDCustomEntity(200,"success");
	 				} else{ return new OPDCustomEntity(400,"non-success"); }
	 				  }else{ return new OPDCustomEntity(400,"non-success"); }
	 				}else{ return new OPDCustomEntity(400,"non-success"); }
	 	    	 }
	 				else
			    	{
			    		return new OPDCustomEntity(400,"non-success");
			    	}
	 	    	  }
     }catch (Exception e) {
    			 System.out.println("----Token Rejects Approval Call Request Error ----");
	    	return new OPDCustomEntity(400,"non-success");
		}
    }
	
	 public OPDCustomEntity tokenApprovalExcelDownloader(HttpSession session)
	 {
      	
   	try {
  	 List<TokenPendingApprovalRequest> pendingCallData = TokenPendingApprovalReqRepoData.findByRequestStatus(true);
  	 
  
	 List<ArrayList<Object>> approvalList
       = new ArrayList<ArrayList<Object>>();
	 
	// One space allocated for R0	        
	approvalList.add(new ArrayList<Object>());
	 
	approvalList.get(0).addAll(Arrays.asList("Hospitals","Hospital ID","Gmail","State","City","Request Time","Approval Time"));
	
	for(int i=0;i<pendingCallData.size();i++)
	{
		approvalList.add(new ArrayList<Object>());
		 
		approvalList.get(i+1).addAll(Arrays.asList(pendingCallData.get(i).getHospitalName(),pendingCallData.get(i).getHospitalID(),pendingCallData.get(i).getGmail(),pendingCallData.get(i).getState(),pendingCallData.get(i).getCity(),pendingCallData.get(i).getRequestDateTime(),pendingCallData.get(i).getApprovedTime()));
		
	}
	      	
	session.setAttribute("tokenapprovedexceldownload", approvalList);
	
	Object approvedExcelDownloadAttribute = session.getAttribute("tokenapprovedexceldownload");
	
	return new OPDCustomEntity(200,approvedExcelDownloadAttribute,"success");
	
   	}catch (Exception e) {
   		System.out.println("---- Token Approval Downloader Error ----");
   		return new OPDCustomEntity(400,"non-success");
		}
	 }

	 public OPDCustomEntity tokenRejectExcelDownloader(HttpSession session)
	 {
	     	
	     	try {
	   	List<TokenRejectRequest>  rejectsUserData= tokenRejectRequestRepoData.findAll();
	   	
	   	List<ArrayList<Object>> rejectlList
        = new ArrayList<ArrayList<Object>>();
 	 
 	    // One space allocated for R0	        
    	rejectlList.add(new ArrayList<Object>());
 	 
    	rejectlList.get(0).addAll(Arrays.asList("Hospitals","Hospital ID","Gmail","State","City","Request Time","Reject Time"));
 	
 	for(int i=0;i<rejectsUserData.size();i++)
	{
 		rejectlList.add(new ArrayList<Object>());
 		 
 		rejectlList.get(i+1).addAll(Arrays.asList(rejectsUserData.get(i).getHospitalName(),rejectsUserData.get(i).getHospitalID(),rejectsUserData.get(i).getGmail(),rejectsUserData.get(i).getState(),rejectsUserData.get(i).getCity(),rejectsUserData.get(i).getRequestDateTime(),rejectsUserData.get(i).getReject_time()));
 	}
	      	
 	session.setAttribute("tokenrejectexceldownload", rejectlList);
 	
 	Object rejectExcelDownloadAttribute = session.getAttribute("tokenrejectexceldownload");
 	
 	return new OPDCustomEntity(200,rejectExcelDownloadAttribute,"success");
    	
     	
      	}catch (Exception e) {
      		System.out.println("----Token Reject Excel Downloader Error ----");
      		return new OPDCustomEntity(400,"non-success");
		}
	 }
	 
}
