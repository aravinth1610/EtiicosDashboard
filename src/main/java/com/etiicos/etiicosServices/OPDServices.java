package com.etiicos.etiicosServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.etiicos.customeEntity.OPDCustomEntity;
import com.etiicos.dataObject.OPDRequestDataObject;
import com.etiicos.dataObject.RejectDataObject;
import com.etiicos.entity.EtiicosLogin;
import com.etiicos.entity.OPDApprovalRequest;
import com.etiicos.entity.OPDPendingApprovalRequest;
import com.etiicos.entity.OPDRejectsRequest;
import com.etiicos.etiicosCustomeDB.DBOperator;
import com.etiicos.localException.LocalException;
import com.etiicos.mailGeneration.MailGenerator;
import com.etiicos.properties.PropertiesClassPath;
import com.etiicos.repository.EtiicosLoginRepository;
import com.etiicos.repository.OPDApprovalRepository;
import com.etiicos.repository.OPDRejectRepository;
import com.etiicos.repository.OPDRequestRepository;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

@Service
public class OPDServices {

	@Autowired
	OPDRequestRepository OPDRequestRepoData;
	
	@Autowired
	OPDRejectRepository OPDRejectRepoData;
	
	@Autowired
	OPDApprovalRepository OPDApprovalRepoData;
	
	@Autowired
	EtiicosLoginRepository etiicosLoginRepoData;
	
	
	@Autowired
	PropertiesClassPath PropertiesFiles;
	
	@Autowired
	MailGenerator generatingMail;
	
	
	public OPDCustomEntity otpHTMLTag(String gmail)
	{
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String s = encoder.encode("adminL66M*P))");
		
		System.out.println(s);
		
		try
		{
		if(gmail.equals("etiicosdashboard@gmail.com"))
		{
			
		Boolean isAdminMailSent =	generatingMail.adminLoginAuth(gmail);
			
		if(isAdminMailSent)
		{
		String otpTag ="                 <div class=\"col-12\">\r\n"
				+ "                      <label for=\"yourPassword\" class=\"form-label\">OTP</label>\r\n"
				+ "                      <input type=\"text\"  maxlength=\"4\" pattern=\"\\d{4}\" name=\"otp\" class=\"form-control\" id=\"yourotp\" autofocus=\"autofocus\" autocomplete=\"off\" required>\r\n"
				+ "                      <div class=\"invalid-feedback\">Please enter your password!</div>\r\n"
				+ "                    </div> <br>"
				+ "                     <div class=\"col-12\">\r\n"
				+ "                      <button onclick=loginview() class=\"btn btn-primary w-100\" type=\"submit\">Submit</button>\r\n"
				+ "                    </div>";
	      
		return new OPDCustomEntity(200,otpTag,"success");
		}
		else {return new OPDCustomEntity(400,"non-success");}
		}
		else
		{
			return new OPDCustomEntity(101,"InvalidMail-non-success");
		}
		}catch (Exception e) {
			System.out.println("--OTP HTML Tag Error--");
			return new OPDCustomEntity(400,"non-success");
		}
	}
	
	public OPDCustomEntity loginAuthOTPVerify(Integer otp,HttpSession session)
	{
		
		try
		{
		if(otp.equals(MailGenerator.randomOTP))
		{
			session.setAttribute("loginAuthcert","loginAuthL66M*P))");
			
			return new OPDCustomEntity(200,"login","success");
		}
		else
		{
			return new OPDCustomEntity(202,"otpnotvalid-non-success");
		}
		}
		catch (Exception e) {
			System.out.println("--Login Auth Verify Error --");
			return new OPDCustomEntity(400,"non-success");
		}
	}
	

	
	public OPDCustomEntity requestEtiicosDataUpdating(OPDRequestDataObject requestData)
	{
		try
		{
			
		OPDApprovalRequest registeredEntity = new OPDApprovalRequest();
		Integer requestAccepted = OPDRequestRepoData.updaterequest(requestData.getRequest(), requestData.getGmail());
		SimpleDateFormat dateTime=new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	    String requestTime=dateTime.format(new Date());
		
	    OPDPendingApprovalRequest  OPDRequestCalls= OPDRequestRepoData.findByGmail(requestData.getGmail());
		if(requestAccepted==1)
		{
			
			registeredEntity.setUserId(OPDRequestCalls.getUserId());
			registeredEntity.setGmail(OPDRequestCalls.getGmail());
			registeredEntity.setHospital(OPDRequestCalls.getHospital());
			registeredEntity.setHospitalId(OPDRequestCalls.getHospitalId());       
			OPDRequestRepoData.updaterequestTime(requestTime,requestData.getGmail());
			OPDApprovalRepoData.save(registeredEntity);
			
			List<String> OPDValueInsertion = new ArrayList<String>();
			
			OPDValueInsertion.add("user_id");
			OPDValueInsertion.add("gmail");
			OPDValueInsertion.add("hospital");
			OPDValueInsertion.add("hospital_id");
			OPDValueInsertion.add("password");
			OPDValueInsertion.add("registered_time");
			OPDValueInsertion.add("state");
			OPDValueInsertion.add("city");
			OPDValueInsertion.add("payment_status");
			OPDValueInsertion.add("next_payment_date");
			
			DBOperator OPDCustomeDataBase = new DBOperator(PropertiesFiles.getRequestHost(),PropertiesFiles.getRequestPort(),PropertiesFiles.getRequestUsername(),PropertiesFiles.getRequestPassword()); 
			
			
			Boolean OPDInserted = OPDCustomeDataBase.insertTableValues("opdaggregatedspace","opd_etiicos_registered",OPDValueInsertion,OPDRequestCalls.getUserId(),OPDRequestCalls.getGmail(),OPDRequestCalls.getHospital(),OPDRequestCalls.getHospitalId(),OPDRequestCalls.getPassword(),requestTime,OPDRequestCalls.getState(),OPDRequestCalls.getCity(),OPDRequestCalls.getPaymentStatus(),OPDRequestCalls.getNextPaymentDate());
			
			if(OPDInserted)
			{
			Boolean isDatabaseCreated = OPDCustomeDataBase.createDatabase(OPDRequestCalls.getHospitalId());
			if(isDatabaseCreated)
			{
			Boolean tableCreated =	OPDCustomeDataBase.createOPDTable(OPDRequestCalls.getHospitalId(),OPDRequestCalls.getHospitalId().concat("_record"),"callno","varchar(255) NOT NULL","doctordata","varchar(500)","status","bit(1)","doctor_name","varchar(255)","doctor_ward","varchar(255)","audio_status","varchar(25),PRIMARY KEY (callno)");
			Boolean tableCallLogCreated =	OPDCustomeDataBase.createOPDTable(OPDRequestCalls.getHospitalId(),OPDRequestCalls.getHospitalId().concat("_calllog"),"callno","varchar(255) NOT NULL","doctordata","varchar(500)","starttime","varchar(50)","endtime","varchar(50)","duration","varchar(50)","endview","varchar(20)","doctor_name","varchar(255)","doctor_roomno","varchar(255)","doctor_department","varchar(255),PRIMARY KEY (starttime)");
		   
			if(tableCreated && tableCallLogCreated)
			{
				return new OPDCustomEntity(200,"success");
			} 
			  }
			}else{ return new OPDCustomEntity(400,"non-success"); }
		        }
	    	else
	    	{
	    		return new OPDCustomEntity(101,"Wating for Request");
	    	}
	
		}catch (Exception e) {
			System.out.println("---- RequestEtiicos Updated Error ----");
			return new OPDCustomEntity(400,"non-success");
		}
		return new OPDCustomEntity(101,"Wating for Request");
		}
	
	
	public OPDCustomEntity dashboardCallRequestDatasCount(HttpSession session)
	 {
		PreparedStatement dbStatement = null;
  		PreparedStatement selectTableStatement = null;
  		Connection OPDDBConnections = null;
  		Statement OPDDBStatement = null;
  		
  		Integer NoFDoctor=0;
		
		 try {
    		 DBOperator OPDCustomeDataBase = new DBOperator(); 
    	    
    		 String OPDDBURL = "jdbc:mysql://".concat(PropertiesFiles.getHost()).concat(":").concat(PropertiesFiles.getPort()).concat("/?enabledTLSProtocols=TLSv1.2");
    		 OPDDBConnections = DriverManager.getConnection(OPDDBURL, PropertiesFiles.getUsername(), PropertiesFiles.getPassword());
    	
    		 String doctorCount = "select count(doctor_phoneno) from user_doctor;";
    		 
    	 ResultSet gettingTableValus = OPDCustomeDataBase.excecuteOPDQuery(OPDDBConnections,OPDDBStatement,dbStatement,selectTableStatement,"opdaggregatedspace",doctorCount);
    		
         gettingTableValus.next();
		
         NoFDoctor  = gettingTableValus.getInt("count(doctor_phoneno)");
	 	
    	} 
    	catch(SQLSyntaxErrorException syntaxException)
  		{
  			if(syntaxException.getMessage().contains("already exists"))
  			{
  				throw new LocalException("EXISTING_TABLE");
  			}
  			return new OPDCustomEntity(400,"non-success");
  		}
  		catch(CommunicationsException communicationException)
  		{
  			communicationException.printStackTrace();
  			throw new LocalException("INVALID_URL");
  		}
  		catch(SQLException sqlException)
  		{
  			if(sqlException.getMessage().contains("Unknown database"))
  			{
  				throw new LocalException("FALSE_DATABASE");
  			}
  			else if(sqlException.getMessage().contains("Access denied"))
  			{
  				throw new LocalException("INVALID_CREDENTIALS");
  			}
  			sqlException.printStackTrace();
  			return new OPDCustomEntity(400,"non-success");
  		}
    	  catch (Exception e) {
    		System.out.println("---- Getting Doctor Count Error ----");
    		return new OPDCustomEntity(400,"non-success");
		}
     	finally 
   		{
   		    if (OPDDBConnections != null) {
   		        try {
   		        	OPDDBConnections.close();
   		        } catch (SQLException e) { /* Ignored */}
   		    }
   		    
   		  if (dbStatement != null) {
 		        try {
 		        	dbStatement.close();
 		        } catch (SQLException e) { /* Ignored */}
 		    }
 		    if (selectTableStatement != null) {
 		        try {
 		        	selectTableStatement.close();
 		        } catch (SQLException e) { /* Ignored */}
 		    }
 		    if (OPDDBStatement != null) 
 		    {
 		        try 
 		        {
 		        	OPDDBStatement.close();
 		        } catch (SQLException e) { /* Ignored */}
 		    }
   		    
   		}
		
		
		
		 try {
			 
		   Integer pendingHospitalCount = OPDRequestRepoData.pendingHospitalCount(false);
	 	   Integer approvedHospitalCount = OPDRequestRepoData.pendingHospitalCount(true);
	 	    
	 	   
	 	   Map<String,Object> dashboardRequestAllDatasCounts = new LinkedHashMap<String, Object>();
	 	    
	 	   dashboardRequestAllDatasCounts.put("NodoctorCount",NoFDoctor);
	 	  dashboardRequestAllDatasCounts.put("pendinghospitalcount", pendingHospitalCount);
	 	 dashboardRequestAllDatasCounts.put("approvedghospitalcount", approvedHospitalCount);
	 	dashboardRequestAllDatasCounts.put("rejecthospitalcount",OPDRejectRepoData.rejectCallCount());
	 	
	 	
	 	session.setAttribute("dashboardrequestalldatasCounts", dashboardRequestAllDatasCounts);
   	
  	    Object dashboadRequestDatasCount =session.getAttribute("dashboardrequestalldatasCounts");
  	
    
   	return new OPDCustomEntity(200,dashboadRequestDatasCount,"success");
	    }
	    catch (Exception e) {
	    	System.out.println("----Dashboard Count Request Error ----");
	    	return new OPDCustomEntity(400,"non-success");
		}
	 	
	 }
	
	
	
	public OPDCustomEntity dashboardCallRequestDatas(HttpSession session)
	 {
    	 
    List<String> pendingGmail = new ArrayList<String>();
   	List<String> pendingHospital = new ArrayList<String>();
   	List<String> pendingHospitalID = new ArrayList<String>();
   	List<String> pendingRequestTime = new ArrayList<String>();
	List<String> pendingCity = new ArrayList<String>();
  	List<String> pendingState = new ArrayList<String>();
   	List<String> approvedGmail = new ArrayList<String>();
   	List<String> approvedHospital = new ArrayList<String>();
   	List<String> approvedRequestTime = new ArrayList<String>();
   	List<String> approvedHospitalID = new ArrayList<String>();
	List<String> approvedCity = new ArrayList<String>();
  	List<String> approvedState = new ArrayList<String>();
   	List<String> rejectedGmail = new ArrayList<String>();
    List<String> rejectedHospital = new ArrayList<String>();
    List<String> rejectedRequestTime = new ArrayList<String>();
    List<String> rejectedHospitalID = new ArrayList<String>();
    List<String> rejectedState = new ArrayList<String>();
    List<String> rejectedCity = new ArrayList<String>();
    
   	List<OPDPendingApprovalRequest> pendingCallData = OPDRequestRepoData.findByRequest(false);
   	List<OPDPendingApprovalRequest> approvedCallData = OPDRequestRepoData.findByRequest(true);
    List<OPDRejectsRequest>  rejectsUserData= OPDRejectRepoData.findAll();
	   
	    try {
   	
   	for(OPDPendingApprovalRequest pendingRequest : pendingCallData)
   	{
   		pendingGmail.add(pendingRequest.getGmail());
   		pendingHospital.add(pendingRequest.getHospital());
   		pendingRequestTime.add(pendingRequest.getRequestTime());
   		pendingHospitalID.add(pendingRequest.getHospitalId());
   		pendingCity.add(pendingRequest.getCity());
   		pendingState.add(pendingRequest.getState());
   	}
   
   	for(OPDPendingApprovalRequest approvedRequest : approvedCallData)
   	{
   		approvedGmail.add(approvedRequest.getGmail());
   		approvedHospital.add(approvedRequest.getHospital());
   		approvedRequestTime.add(approvedRequest.getRequestTime());
   		approvedHospitalID.add(approvedRequest.getHospitalId());
   		approvedCity.add(approvedRequest.getCity());
   		approvedState.add(approvedRequest.getState());
   	}
   	
   	for(OPDRejectsRequest rejectsUser : rejectsUserData)
   	{
   		rejectedGmail.add(rejectsUser.getGmail());
   		rejectedHospital.add(rejectsUser.getHospital());
   		rejectedRequestTime.add(rejectsUser.getReject_time());
   		rejectedHospitalID.add(rejectsUser.getHospital_id());
   		rejectedState.add(rejectsUser.getState());
   		rejectedCity.add(rejectsUser.getCity());
   	}
   	
   	Map<String,Object> dashboardRequestAllDatas = new LinkedHashMap<String, Object>();
   	dashboardRequestAllDatas.put("pendinggmail", pendingGmail);
   	dashboardRequestAllDatas.put("pendinghospialID", pendingHospitalID);
   	dashboardRequestAllDatas.put("pendinghospial", pendingHospital);
   	dashboardRequestAllDatas.put("pendingrequesttime", pendingRequestTime);
   	dashboardRequestAllDatas.put("pendingstate",pendingState);
   	dashboardRequestAllDatas.put("pendingcity",pendingCity);
   	dashboardRequestAllDatas.put("approvedgmail", approvedGmail);
   	dashboardRequestAllDatas.put("approvedhospialID", approvedHospitalID);
   	dashboardRequestAllDatas.put("approvedhospial", approvedHospital);
   	dashboardRequestAllDatas.put("approvedrequesttime", approvedRequestTime);
	dashboardRequestAllDatas.put("approvedstate", approvedState);
	dashboardRequestAllDatas.put("approvedcity", approvedCity);
   	dashboardRequestAllDatas.put("rejectgmail", rejectedGmail);
   	dashboardRequestAllDatas.put("rejecthospialID", rejectedHospitalID);
   	dashboardRequestAllDatas.put("rejecthospial", rejectedHospital);
   	dashboardRequestAllDatas.put("rejectrequesttime", rejectedRequestTime);
   	dashboardRequestAllDatas.put("rejectstate", rejectedState);
   	dashboardRequestAllDatas.put("rejectcity", rejectedCity);
   	
   	
   	
   	session.setAttribute("dashboardrequestalldatas", dashboardRequestAllDatas);
   	
   	 Object dashboadRequestDatas =session.getAttribute("dashboardrequestalldatas");
   	
   	 
    	return new OPDCustomEntity(200,dashboadRequestDatas,"success");
	    }
	    catch (Exception e) {
	    	System.out.println("----Dashboard Request Error ----");
	    	return new OPDCustomEntity(400,"non-success");
		}
	   }
	
	
	
	
	
	 public OPDCustomEntity pendingCallRequestDatas(HttpSession session)
	 {
    	List<String> pendingGmail = new ArrayList<String>();
     	List<String> pendingHospital = new ArrayList<String>();
     	List<String> pendingRequestTime = new ArrayList<String>();
     	List<String> pendingHospitalID = new ArrayList<String>();
    	List<String> pendingCity = new ArrayList<String>();
       	List<String> pendingState = new ArrayList<String>();
     	
     	try {
    	 List<OPDPendingApprovalRequest> pendingCallData = OPDRequestRepoData.findByRequest(false);
    	 
    	 for(OPDPendingApprovalRequest pendingRequest : pendingCallData)
     	{
     		pendingGmail.add(pendingRequest.getGmail());
     		pendingHospital.add(pendingRequest.getHospital());
     		pendingHospitalID.add(pendingRequest.getHospitalId());
     		pendingRequestTime.add(pendingRequest.getRequestTime());
     		pendingCity.add(pendingRequest.getCity());
     		pendingState.add(pendingRequest.getState());
     	}
    	 
    	 Map<String,Object> pendingRequestAllDatas = new LinkedHashMap<String, Object>();
     	pendingRequestAllDatas.put("pendinggmail", pendingGmail);
     	pendingRequestAllDatas.put("pendinghospial", pendingHospital);
     	pendingRequestAllDatas.put("pendinghospialID", pendingHospitalID);
     	pendingRequestAllDatas.put("pendingrequesttime", pendingRequestTime);
     	pendingRequestAllDatas.put("pendingstate", pendingState);
     	pendingRequestAllDatas.put("pendingcity", pendingCity);
     	
     	session.setAttribute("pendingrequestdatas", pendingRequestAllDatas);
     	
     	Object pendingRequestDatasAttribute = session.getAttribute("pendingrequestdatas");
     	
     	
     	return new OPDCustomEntity(200,pendingRequestDatasAttribute,"success");
     	
     	}catch (Exception e) {
     		System.out.println("---- pendingcall Request Error ----");
     		return new OPDCustomEntity(400,"non-success");
		}
	 }
	
	 
	 public OPDCustomEntity approvedCallRequestDatas(HttpSession session)
	 {
    	List<String> approvedGmail = new ArrayList<String>();
     	List<String> approvedHospital = new ArrayList<String>();
     	List<String> approvedRequestTime = new ArrayList<String>();
     	List<String> approveHospitalID = new ArrayList<String>();
    	List<String> approvedState = new ArrayList<String>();
       	List<String> approvedCity = new ArrayList<String>();
       	List<String> approvedTime = new ArrayList<String>();
       	
     	try {
     	List<OPDPendingApprovalRequest> approvedCallData = OPDRequestRepoData.findByRequest(true);
     	 
     	for(OPDPendingApprovalRequest approvedRequest : approvedCallData)
    	{
    		approvedGmail.add(approvedRequest.getGmail());
    		approvedHospital.add(approvedRequest.getHospital());
    		approvedRequestTime.add(approvedRequest.getRequestTime());
    		approveHospitalID.add(approvedRequest.getHospitalId());
    		approvedState.add(approvedRequest.getState());
    		approvedCity.add(approvedRequest.getCity());
    		approvedTime.add(approvedRequest.getApprovedTime());
    		
    	}
     	Map<String,Object> approvedRequestAllDatas = new LinkedHashMap<String, Object>();
     	approvedRequestAllDatas.put("approvedgmail", approvedGmail);
     	approvedRequestAllDatas.put("approvedhospial", approvedHospital);
     	approvedRequestAllDatas.put("approvedhospialID", approveHospitalID);
     	approvedRequestAllDatas.put("approvedrequesttime", approvedRequestTime);
     	approvedRequestAllDatas.put("approvedcity", approvedCity);
     	approvedRequestAllDatas.put("approvedstate", approvedState);
     	approvedRequestAllDatas.put("approvedtime", approvedTime);
     	
      	
     	session.setAttribute("approvedrequestdatas", approvedRequestAllDatas);
     	
     	Object approvedRequestDatasAttribute = session.getAttribute("approvedrequestdatas");
     	
     	
     	return new OPDCustomEntity(200,approvedRequestDatasAttribute,"success");
     	
     	}catch (Exception e) {
     		System.out.println("---- approved Request Error -----");
     		return new OPDCustomEntity(400,"non-success");
		}
	 }
	 
	 public OPDCustomEntity rejectCallRequest(RejectDataObject rejectData)
     {
    	
    	SimpleDateFormat dateTime=new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
 	    String requestTime=dateTime.format(new Date());
 	    
 	    try {
 	    OPDPendingApprovalRequest requestRejctsList = OPDRequestRepoData.findByGmail(rejectData.getHospitalID());
     	Integer rejectedRequest  = OPDRequestRepoData.deleteByGmail(rejectData.getHospitalID());
    	OPDRejectsRequest requestRejectData = new OPDRejectsRequest();
    	
    	if(rejectedRequest==1)
    	{
    		requestRejectData.setGmail(requestRejctsList.getGmail());
    		
    		requestRejectData.setUser_id(requestRejctsList.getUserId());
    		requestRejectData.setGmail(requestRejctsList.getGmail());
    		requestRejectData.setHospital(requestRejctsList.getHospital());
    		requestRejectData.setHospital_id(requestRejctsList.getHospitalId());
    		requestRejectData.setPassword(requestRejctsList.getPassword());
    		requestRejectData.setReject_time(requestTime);
    		requestRejectData.setRequest_time(requestRejctsList.getRequestTime());
    		requestRejectData.setRequest(requestRejctsList.getRequest());
    		requestRejectData.setState(requestRejctsList.getState());
       		requestRejectData.setCity(requestRejctsList.getCity());
       		requestRejectData.setReason(rejectData.getReason());
       		requestRejectData.setPaymentStatus(requestRejctsList.getPaymentStatus());
       		requestRejectData.setNextPaymentDate(requestRejctsList.getNextPaymentDate());
       		
    		OPDRejectRepoData.save(requestRejectData);
    		
    		return new OPDCustomEntity(200,"success");
    	}
    	else
    	{
    		return new OPDCustomEntity(400,"non-success");
    	}
 	    }catch (Exception e) {
 	    	System.out.println("---- Rejects Call Request Error ----");
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
    	List<OPDRejectsRequest>  rejectsUserData= OPDRejectRepoData.findAll();
    	
    	for(OPDRejectsRequest rejectsUser : rejectsUserData)
    	{
    		rejectedGmail.add(rejectsUser.getGmail());
    		rejectedHospital.add(rejectsUser.getHospital());
    		rejectedRequestTime.add(rejectsUser.getReject_time());
    		rejectedHospitalID.add(rejectsUser.getHospital_id());
    		rejectedState.add(rejectsUser.getState());
      		rejectedCity.add(rejectsUser.getCity());
      		rejectedTime.add(rejectsUser.getReject_time());
      		rejectedReason.add(rejectsUser.getReason());
    	}
    	
    	Map<String,Object> rejectRequestAllDatas = new LinkedHashMap<String, Object>();
    	rejectRequestAllDatas.put("rejectgmail", rejectedGmail);
    	rejectRequestAllDatas.put("rejecthospial", rejectedHospital);
    	rejectRequestAllDatas.put("rejecthospialID", rejectedHospitalID);
    	rejectRequestAllDatas.put("rejectrequesttime", rejectedRequestTime);
    	rejectRequestAllDatas.put("rejectstate", rejectedState);
       	rejectRequestAllDatas.put("rejectcity", rejectedCity);
       	rejectRequestAllDatas.put("rejecttime", rejectedTime);
       	rejectRequestAllDatas.put("rejectreason",rejectedReason);
       	
    	session.setAttribute("rejectRequestdata", rejectRequestAllDatas);
    	
    	Object rejectRequestDataAttribute = session.getAttribute("rejectRequestdata");
    	
    	
     	return new OPDCustomEntity(200,rejectRequestDataAttribute,"success");
     	
      	}catch (Exception e) {
      		System.out.println("---- Reject Request All Datas Error ----");
      		return new OPDCustomEntity(400,"non-success");
		}
     }
	 
	 public OPDCustomEntity approvalRejectCallRequest(RejectDataObject rejectReason)
     {
		    SimpleDateFormat dateTime=new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	 	    String requestTime=dateTime.format(new Date());
	 	    
	 	    try {
	 	   	DBOperator OPDCustomeDataBase = new DBOperator(PropertiesFiles.getRequestHost(),PropertiesFiles.getRequestPort(),PropertiesFiles.getRequestUsername(),PropertiesFiles.getRequestPassword()); 
	 		
	 	    OPDPendingApprovalRequest requestRejctsList = OPDRequestRepoData.findByHospitalId(rejectReason.getHospitalID());
	        
	    	OPDRejectsRequest requestRejectData = new OPDRejectsRequest();
	    	
	    	if(requestRejctsList!=null)
	    	{
	    		
	    		
	    		requestRejectData.setUser_id(requestRejctsList.getUserId());
	    		requestRejectData.setGmail(requestRejctsList.getGmail());
	    		requestRejectData.setHospital(requestRejctsList.getHospital());
	    		requestRejectData.setHospital_id(requestRejctsList.getHospitalId());
	    		requestRejectData.setPassword(requestRejctsList.getPassword());
	    		requestRejectData.setReject_time(requestTime);
	    		requestRejectData.setRequest_time(requestRejctsList.getRequestTime());
	    		requestRejectData.setRequest(requestRejctsList.getRequest());
	    		requestRejectData.setCity(requestRejctsList.getCity());
	    		requestRejectData.setState(requestRejctsList.getState());
	    		requestRejectData.setReason(rejectReason.getReason());
	    		requestRejectData.setPaymentStatus(requestRejctsList.getPaymentStatus());
	    		requestRejectData.setNextPaymentDate(requestRejctsList.getNextPaymentDate());
	    		
	    		OPDRejectRepoData.save(requestRejectData);
	    		Integer rejectedRequest  = OPDRequestRepoData.deleteByHospitalId(rejectReason.getHospitalID());
	    		Boolean approvalRecipientReject = OPDCustomeDataBase.deleteTableValues("opdaggregatedspace","opd_etiicos_registered","hospital_id",rejectReason.getHospitalID());
	    		Boolean approvalDoctorReject = OPDCustomeDataBase.updateTableValues("opdaggregatedspace","user_doctor","opd_recipient_id",rejectReason.getHospitalID(),"opd_recipient_id",requestRejctsList.getHospitalId().concat("_$Etiicos"));
	    
	    	if(rejectedRequest==1 && approvalRecipientReject && approvalDoctorReject)
	    	{
	    		return new OPDCustomEntity(200,"success");
	    	} else {return new OPDCustomEntity(400,"non-success");}
	    	}
	    	else
	    	{
	    		return new OPDCustomEntity(400,"non-success");
	    	}
	 	    }catch (Exception e) {
	 	    	System.out.println("---- Rejects Call Request Error ----");
	 	    	return new OPDCustomEntity(400,"non-success");
			}
     }

	 public OPDCustomEntity rejectApprovalCallRequest(String userGmail)
     {
	
		    SimpleDateFormat dateTime=new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	 	    String requestTime=dateTime.format(new Date());

	 	    DBOperator OPDCustomeDataBase = new DBOperator(PropertiesFiles.getRequestHost(),PropertiesFiles.getRequestPort(),PropertiesFiles.getRequestUsername(),PropertiesFiles.getRequestPassword()); 
	 	    OPDRejectsRequest rejectDatas = OPDRejectRepoData.findByGmail(userGmail);
	 	    OPDPendingApprovalRequest requestApproval = new OPDPendingApprovalRequest();
		  	
	     	 List<String> OPDValueInsertion = new ArrayList<String>();
			
			OPDValueInsertion.add("user_id");
			OPDValueInsertion.add("gmail");
			OPDValueInsertion.add("hospital");
			OPDValueInsertion.add("hospital_id");
			OPDValueInsertion.add("password");
			OPDValueInsertion.add("registered_time");
			OPDValueInsertion.add("city");
			OPDValueInsertion.add("state");
			OPDValueInsertion.add("payment_status");
			OPDValueInsertion.add("next_payment_date");
	    	
	 	    try {
	 	    	
	 	    	if(OPDApprovalRepoData.existsByGmail(userGmail))
	 	    	{
	
	 	    	if(rejectDatas != null)
		    	{
		  requestApproval.setUserId(rejectDatas.getUser_id());
	 	  requestApproval.setGmail(rejectDatas.getGmail());
	 	  requestApproval.setHospital(rejectDatas.getHospital());
	 	  requestApproval.setHospitalId(rejectDatas.getHospital_id());
	 	  requestApproval.setPassword(rejectDatas.getPassword());
	 	  requestApproval.setRequest(true);
	 	  requestApproval.setRequestTime(rejectDatas.getRequest_time());
	 	  requestApproval.setApprovedTime(requestTime);
	 	  requestApproval.setCity(rejectDatas.getCity());
	 	  requestApproval.setState(rejectDatas.getState());
	 	  requestApproval.setPaymentStatus(rejectDatas.getPaymentStatus());
	 	  requestApproval.setNextPaymentDate(rejectDatas.getNextPaymentDate());
	 	  
	 	 Boolean OPDInserted = OPDCustomeDataBase.insertTableValues("opdaggregatedspace","opd_etiicos_registered",OPDValueInsertion,rejectDatas.getUser_id(),rejectDatas.getGmail(),rejectDatas.getHospital(),rejectDatas.getHospital_id(),rejectDatas.getPassword(),requestTime,rejectDatas.getCity(),rejectDatas.getState(),rejectDatas.getPaymentStatus(),rejectDatas.getNextPaymentDate());
	 	
	 	if(OPDInserted)
			{
	 		 OPDRequestRepoData.save(requestApproval);
	 		OPDRejectRepoData.deleteByGmail(userGmail);
	 		OPDCustomeDataBase.updateTableValues("opdaggregatedspace","user_doctor","opd_recipient_id",rejectDatas.getHospital_id().concat("_$Etiicos"),"opd_recipient_id",rejectDatas.getHospital_id());

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
			  requestApproval.setUserId(rejectDatas.getUser_id());
		 	  requestApproval.setGmail(rejectDatas.getGmail());
		 	  requestApproval.setHospital(rejectDatas.getHospital());
		 	  requestApproval.setHospitalId(rejectDatas.getHospital_id());
		 	  requestApproval.setPassword(rejectDatas.getPassword());
		 	  requestApproval.setRequest(true);
		 	  requestApproval.setRequestTime(rejectDatas.getRequest_time());
		 	  requestApproval.setApprovedTime(requestTime);
		 	  requestApproval.setCity(rejectDatas.getCity());
		 	  requestApproval.setState(rejectDatas.getState());	
		      requestApproval.setPaymentStatus(rejectDatas.getPaymentStatus());
		 	  requestApproval.setNextPaymentDate(rejectDatas.getNextPaymentDate());
		    
			  
	 				Boolean OPDInserted = OPDCustomeDataBase.insertTableValues("opdaggregatedspace","opd_etiicos_registered",OPDValueInsertion,rejectDatas.getUser_id(),rejectDatas.getGmail(),rejectDatas.getHospital(),rejectDatas.getHospital_id(),rejectDatas.getPassword(),requestTime,rejectDatas.getCity(),rejectDatas.getState(),rejectDatas.getPaymentStatus(),rejectDatas.getNextPaymentDate());
	 				
	 				if(OPDInserted)
	 				{

	 					OPDApprovalRequest registeredEntity = new OPDApprovalRequest();
	 					
	 					registeredEntity.setUserId(rejectDatas.getUser_id());
	 					registeredEntity.setGmail(rejectDatas.getGmail());
	 					registeredEntity.setHospital(rejectDatas.getHospital());
	 					registeredEntity.setHospitalId(rejectDatas.getHospital_id());       
	 					OPDRequestRepoData.updaterequestTime(requestTime,rejectDatas.getGmail());
	 					OPDApprovalRepoData.save(registeredEntity);
	 					
	 				Boolean isDatabaseCreated = OPDCustomeDataBase.createDatabase(rejectDatas.getHospital_id());
	 				if(isDatabaseCreated)
	 				{
	 				Boolean tableCreated =	OPDCustomeDataBase.createOPDTable(rejectDatas.getHospital_id(),rejectDatas.getHospital_id().concat("_record"),"callno","varchar(255) NOT NULL","doctordata","varchar(500)","status","bit(1)","doctor_name","varchar(255)","doctor_ward","varchar(255)","audio_status","varchar(25),PRIMARY KEY (callno)");
	 				Boolean tableCallLogCreated =	OPDCustomeDataBase.createOPDTable(rejectDatas.getHospital_id(),rejectDatas.getHospital_id().concat("_calllog"),"callno","varchar(255) NOT NULL","doctordata","varchar(500)","starttime","varchar(50)","endtime","varchar(50)","duration","varchar(50)","endview","varchar(20)","doctor_name","varchar(255)","doctor_roomno","varchar(255)","doctor_department","varchar(255),PRIMARY KEY (starttime)");
	 			   
	 				if(tableCreated && tableCallLogCreated)
	 				{
	 					OPDRequestRepoData.save(requestApproval);
	 				 	OPDRejectRepoData.deleteByGmail(userGmail);
	 					return new OPDCustomEntity(200,"success");
	 				} 
	 				  }
	 				}else{ return new OPDCustomEntity(400,"non-success"); }
	 	    	 }
	 				else
			    	{
			    		return new OPDCustomEntity(400,"non-success");
			    	}
	 	    	  }
     }catch (Exception e) {
    	 	System.out.println("---- Rejects Approval Call Request Error ----");
	    	return new OPDCustomEntity(400,"non-success");
		}
	 	    return new OPDCustomEntity(200,"success");
    }
	 
	 
	 public OPDCustomEntity approvalExcelDownloader(HttpSession session)
	 {
	       	
	     	try {
	     	List<OPDPendingApprovalRequest> approvedCallData = OPDRequestRepoData.findByRequest(true);
	     	
	     	
	     	 List<ArrayList<Object>> approvalList
	            = new ArrayList<ArrayList<Object>>();
	     	 
	     	// One space allocated for R0	        
	     	approvalList.add(new ArrayList<Object>());
	     	 
	     	approvalList.get(0).addAll(Arrays.asList("Hospitals","Hospital ID","Gmail","State","City","Request Time","Approval Time"));
	     	
	     	for(int i=0;i<approvedCallData.size();i++)
	    	{
	     		approvalList.add(new ArrayList<Object>());
	     		 
	      		approvalList.get(i+1).addAll(Arrays.asList(approvedCallData.get(i).getHospital(),approvedCallData.get(i).getHospitalId(),approvedCallData.get(i).getGmail(),approvedCallData.get(i).getState(),approvedCallData.get(i).getCity(),approvedCallData.get(i).getRequestTime(),approvedCallData.get(i).getApprovedTime()));
	     		
	    	}
	   	      	
	     	session.setAttribute("approvedexceldownload", approvalList);
	     	
	     	Object approvedExcelDownloadAttribute = session.getAttribute("approvedexceldownload");
	     	
	     	
	     	return new OPDCustomEntity(200,approvedExcelDownloadAttribute,"success");
	     	
	     	}catch (Exception e) {
	     		System.out.println("---- approved Excel Downloader Error -----");
	     		return new OPDCustomEntity(400,"non-success");
			}
	 }
	 
	 public OPDCustomEntity rejectExcelDownloader(HttpSession session)
	 {
		
	      	try {
	    	List<OPDRejectsRequest>  rejectsUserData= OPDRejectRepoData.findAll();
	    	
	    	List<ArrayList<Object>> rejectlList
            = new ArrayList<ArrayList<Object>>();
     	 
     	    // One space allocated for R0	        
	    	rejectlList.add(new ArrayList<Object>());
     	 
	    	rejectlList.get(0).addAll(Arrays.asList("Hospitals","Hospital ID","Gmail","State","City","Request Time","Reject Time"));
     	
     	for(int i=0;i<rejectsUserData.size();i++)
    	{
     		rejectlList.add(new ArrayList<Object>());
     		 
     		rejectlList.get(i+1).addAll(Arrays.asList(rejectsUserData.get(i).getHospital(),rejectsUserData.get(i).getHospital_id(),rejectsUserData.get(i).getGmail(),rejectsUserData.get(i).getState(),rejectsUserData.get(i).getCity(),rejectsUserData.get(i).getRequest_time(),rejectsUserData.get(i).getReject_time()));
     	}
   	      	
     	session.setAttribute("rejectexceldownload", rejectlList);
     	
     	Object rejectExcelDownloadAttribute = session.getAttribute("rejectexceldownload");
     	
     	return new OPDCustomEntity(200,rejectExcelDownloadAttribute,"success");
	    	
	     	
	      	}catch (Exception e) {
	      		System.out.println("---- Reject Excel Downloader Error ----");
	      		return new OPDCustomEntity(400,"non-success");
			}
	 }
}
