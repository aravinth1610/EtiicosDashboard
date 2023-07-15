package com.etiicos.etiicosServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiicos.customeEntity.OPDCustomEntity;
import com.etiicos.entity.TokenPendingApprovalRequest;
import com.etiicos.etiicosCustomeDB.DBOperator;
import com.etiicos.localException.LocalException;
import com.etiicos.properties.PropertiesClassPath;
import com.etiicos.repository.OPDApprovalRepository;
import com.etiicos.repository.OPDRejectRepository;
import com.etiicos.repository.OPDRequestRepository;
import com.etiicos.repository.TokenPendingApprovalRequestRepository;
import com.etiicos.repository.TokenRejectRequestRepository;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

@Service
public class EtiicosAnalyzerServices {
	
	@Autowired
	private OPDRequestRepository OPDRequestRepoData;
	
	@Autowired
	private OPDRejectRepository OPDRejectRepoData;
	
	@Autowired
	private TokenPendingApprovalRequestRepository tokenPendingApprovalRequestRepo;
	
	@Autowired
	private TokenRejectRequestRepository tokenRejectRequestRepo;
	
	@Autowired
	private PropertiesClassPath PropertiesFiles;

		
	public OPDCustomEntity registerCityStateDepartmentCount(HttpSession session)
	{
	
		PreparedStatement dbStatement = null;
  		PreparedStatement selectTableStatement = null;
  		Connection OPDDBConnections = null;
  		Statement OPDDBStatement = null;
  		
  		Integer NoFDepartment=0;
		
		 try {
    		 DBOperator OPDCustomeDataBase = new DBOperator(); 
    	    
    		 String OPDDBURL = "jdbc:mysql://".concat(PropertiesFiles.getHost()).concat(":").concat(PropertiesFiles.getPort()).concat("/?enabledTLSProtocols=TLSv1.2");
    		 OPDDBConnections = DriverManager.getConnection(OPDDBURL, PropertiesFiles.getUsername(), PropertiesFiles.getPassword());
    	
    		 String doctorCount = "select count(distinct doctor_department) from user_doctor;";
    		 
    	 ResultSet gettingTableValus = OPDCustomeDataBase.excecuteOPDQuery(OPDDBConnections,OPDDBStatement,dbStatement,selectTableStatement,"opdaggregatedspace",doctorCount);
    		
         gettingTableValus.next();
		
         NoFDepartment  = gettingTableValus.getInt("count(distinct doctor_department)");
	 	
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
    		System.out.println("---- Department Dashboard Count Error ----");
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
		
		try
		{
			
		Integer overAllRegistertStateCount = OPDRequestRepoData.registerOverAllStateCount();
	    Integer overAllstateTotalCount = overAllRegistertStateCount;
	    
	    Integer overAllRegistertCitiesCount =  OPDRequestRepoData.registerOverAllCitiesCount();
	    
	    Integer overAllcitiesTotalCount = overAllRegistertCitiesCount;
	    
	    Map<String,Object> totalInstalledCount = new LinkedHashMap<String, Object>();
	    
	    totalInstalledCount.put("stateinstalledcount",overAllstateTotalCount);
	    totalInstalledCount.put("citiesinstalledcount",overAllcitiesTotalCount);
	    totalInstalledCount.put("installeddepartment",NoFDepartment);
	    
	    session.setAttribute("totalInstalledCountsession", totalInstalledCount);
	    
	    return new OPDCustomEntity(200,session.getAttribute("totalInstalledCountsession"),"success");
	    
		}catch (Exception e) {
			System.out.println("-- Dashboard Installed Count Error--");
			return new OPDCustomEntity(400,"non-success");
		}
	    
	}
	
	public OPDCustomEntity ListOfStateACount(HttpSession session)
	{
	
		List<String> overAllState = new ArrayList<String>();
		
		try
		{
		
		List<Integer> StateCount = new ArrayList<>();
		
		
		
		List<String> OPDState = OPDRequestRepoData.findStatesList();
		List<String> OPDRejectState = OPDRejectRepoData.rejectListOState();
		List<String> tokenState = tokenPendingApprovalRequestRepo.listOfState();
		List<String> tokenRejectState = tokenRejectRequestRepo.listOfStates();
		
		OPDState.addAll(OPDRejectState);
		OPDState.addAll(tokenState);
		OPDState.addAll(tokenRejectState);
		
		overAllState.addAll(0, OPDState);
		
		Set<String> overAllStateDistinct = new LinkedHashSet<String>();
		
		for(String aOverAllStateDistinct : overAllState)
		{
			overAllStateDistinct.add(aOverAllStateDistinct);
		}
		
		for(String aState : overAllStateDistinct)
		{
			
		int OPDStateCount =	OPDRequestRepoData.stateCount(aState);
		int OPDRejectStateCount = OPDRejectRepoData.listRejectCount(aState);
		int tokenStateCount = tokenPendingApprovalRequestRepo.stateCount(aState);
		int tokenRejectStateCoubt = tokenRejectRequestRepo.stateCount(aState);
		
		StateCount.add(OPDStateCount+OPDRejectStateCount+tokenStateCount+tokenRejectStateCoubt);
		
		}
		
		Map<String,Object> listOfStateCountData = new LinkedHashMap<String,Object>();
		
		listOfStateCountData.put("state", overAllStateDistinct);
		listOfStateCountData.put("count", StateCount);
		
		session.setAttribute("statelistcount", listOfStateCountData);
		
		return new OPDCustomEntity(200,session.getAttribute("statelistcount"),"success");
		
		}catch (Exception e) {
			System.out.println("-- List Of State Count Error --");
		return new OPDCustomEntity(400,"non-success");
		
		}	
	}
	
       public OPDCustomEntity listOfState(String state)
       {
    	   try {
    	   
    		   
    	 List<String> OPDacitiesDetailsState = OPDRequestRepoData.aCityDetailsIState(state);
    	 
    	 List<String> OPDaHospitalDetailsState =  OPDRequestRepoData.aHospitalDetailsIState(state);
    	 
         List<String> OPDRejectacitiesDetailsState = OPDRejectRepoData.aCityDetailsIState(state);
    	 
    	 List<String> OPDRejectaHospitalDetailsState =  OPDRejectRepoData.aHospitalDetailsIState(state);
    	 
         List<String> tokenacitiesDetailsState = tokenPendingApprovalRequestRepo.aCityDetailsIState(state);
    	 
    	 List<String> tokenaHospitalDetailsState =  tokenPendingApprovalRequestRepo.aHospitalDetailsIState(state);
    	 
         List<String> tokenRejectacitiesDetailsState = tokenRejectRequestRepo.aCityDetailsIState(state);
    	 
    	 List<String> tokenRejectaHospitalDetailsState =  tokenRejectRequestRepo.aHospitalDetailsIState(state);
    	     	 
         OPDacitiesDetailsState.addAll(OPDRejectacitiesDetailsState);
         OPDacitiesDetailsState.addAll(tokenacitiesDetailsState);
         OPDacitiesDetailsState.addAll(tokenRejectacitiesDetailsState);
    	 
         OPDaHospitalDetailsState.addAll(OPDRejectaHospitalDetailsState);
    	 OPDaHospitalDetailsState.addAll(tokenaHospitalDetailsState);
    	 OPDaHospitalDetailsState.addAll(tokenRejectaHospitalDetailsState);
    	 
    	 
    	Map<String,Object> listOfStateDetails = new LinkedHashMap<String,Object>();
 		
    	listOfStateDetails.put("cities", OPDacitiesDetailsState);
    	listOfStateDetails.put("hospitals", OPDaHospitalDetailsState);
    	 
    	return new OPDCustomEntity(200,listOfStateDetails,"success");
    	
    	   }
    	   catch (Exception e) {
    		   System.out.println("-- List Of State Details Error --");
    			return new OPDCustomEntity(400,"non-success");
		}
       }
	
   	public OPDCustomEntity ListOfCitiesACount(HttpSession session)
   	{
   	
   		List<String> overAllCities = new ArrayList<String>();
   		
   		try
   		{
   		
   		List<Integer> CitiesCount = new ArrayList<>();
   		
   		
   			
   		List<String> OPDCities = OPDRequestRepoData.findCitiesList();
   		List<String> OPDRejectCities = OPDRejectRepoData.rejectListOCities();
   		List<String> tokencities = tokenPendingApprovalRequestRepo.listOfCities();
   		List<String> tokenRejectCities = tokenRejectRequestRepo.listOfCities();
   		
   		OPDCities.addAll(OPDRejectCities);
   		OPDCities.addAll(tokencities);
   		OPDCities.addAll(tokenRejectCities);
   		
   		
   		overAllCities.addAll(0, OPDCities);
   		
   		Set<String> overAllCitiesDistinct = new LinkedHashSet<String>();
   		
   		for(String aOverAllCitiesDistinct : overAllCities)
   		{
   			overAllCitiesDistinct.add(aOverAllCitiesDistinct);
   		}
   		
   		for(String aState : overAllCitiesDistinct)
   		{
   			
   		int OPDStateCount =	OPDRequestRepoData.citiesCount(aState);
   		int OPDRejectStateCount = OPDRejectRepoData.listRejectCitiesCount(aState);
   		int tokenStateCount = tokenPendingApprovalRequestRepo.citiesCount(aState);
   		int tokenRejectStateCoubt = tokenRejectRequestRepo.citiesCount(aState);
   		
   		CitiesCount.add(OPDStateCount+OPDRejectStateCount+tokenStateCount+tokenRejectStateCoubt);
   		
   		}
   	  
   		Map<String,Object> listOfCitiesCountData = new LinkedHashMap<String,Object>();
   		
   		listOfCitiesCountData.put("city", overAllCitiesDistinct);
   		listOfCitiesCountData.put("count", CitiesCount);
   		
   		session.setAttribute("citylistcount", listOfCitiesCountData);
   		
   		return new OPDCustomEntity(200,session.getAttribute("citylistcount"),"success");
   		
   		}catch (Exception e) {
   		System.out.println("-- List Of Cities Count Error --");
   		return new OPDCustomEntity(400,"non-success");
   		
   		}	
   	}
       
   	
    public OPDCustomEntity listOfCities(String city)
    {
 	   try {
 	   
 		   
 	 List<String> OPDaStateDetailsCity = OPDRequestRepoData.aStateDetailsICity(city);
 	 
 	 List<String> OPDaHospitalDetailsCity =  OPDRequestRepoData.aHospitalDetailsICity(city);
 	 
      List<String> OPDRejectacitiesDetailsCity = OPDRejectRepoData.aStateDetailsICity(city);
 	 
 	 List<String> OPDRejectaHospitalDetailsCity =  OPDRejectRepoData.aHospitalDetailsICity(city);
 	 
      List<String> tokenacitiesDetailsCity = tokenPendingApprovalRequestRepo.aStateDetailsICity(city);
 	 
 	 List<String> tokenaHospitalDetailsCity =  tokenPendingApprovalRequestRepo.aHospitalDetailsICity(city);
 	 
      List<String> tokenRejectacitiesDetailsCity = tokenRejectRequestRepo.aStateDetailsICity(city);
 	 
 	 List<String> tokenRejectaHospitalDetailsCity =  tokenRejectRequestRepo.aHospitalDetailsICity(city);
 	     	 
 	OPDaStateDetailsCity.addAll(OPDRejectacitiesDetailsCity);
 	OPDaStateDetailsCity.addAll(tokenacitiesDetailsCity);
 	OPDaStateDetailsCity.addAll(tokenRejectacitiesDetailsCity);
 	 
 	OPDaHospitalDetailsCity.addAll(OPDRejectaHospitalDetailsCity);
 	OPDaHospitalDetailsCity.addAll(tokenaHospitalDetailsCity);
 	OPDaHospitalDetailsCity.addAll(tokenRejectaHospitalDetailsCity);
 	 
 	 
 	Map<String,Object> listOfCitiesDetails = new LinkedHashMap<String,Object>();
		
 	listOfCitiesDetails.put("state", OPDaStateDetailsCity);
 	listOfCitiesDetails.put("hospitals", OPDaHospitalDetailsCity);
 	 
 	return new OPDCustomEntity(200,listOfCitiesDetails,"success");
 	
 	   }
 	   catch (Exception e) {
 		   System.out.println("-- List Of Cities Details Error --");
 			return new OPDCustomEntity(400,"non-success");
		}
    }
       public OPDCustomEntity departmentListACount(HttpSession session)
       {

   		    PreparedStatement dbStatement = null;
     		PreparedStatement selectTableStatement = null;
     		Connection OPDDBConnections = null;
     		Statement OPDDBStatement = null;
     		
     		List<String> department = new ArrayList<String>();
     		List<String> departmentCount = new ArrayList<String>();
   		
   		 try {
       		 DBOperator OPDCustomeDataBase = new DBOperator(); 
       	    
       		 String OPDDBURL = "jdbc:mysql://".concat(PropertiesFiles.getHost()).concat(":").concat(PropertiesFiles.getPort()).concat("/?enabledTLSProtocols=TLSv1.2");
       		 OPDDBConnections = DriverManager.getConnection(OPDDBURL, PropertiesFiles.getUsername(), PropertiesFiles.getPassword());
       	
       		 String doctorDepartments = "select distinct doctor_department from user_doctor order by doctor_department ASC;";
       		 
       	 ResultSet doctorDepartmentQuery = OPDCustomeDataBase.excecuteOPDQuery(OPDDBConnections,OPDDBStatement,dbStatement,selectTableStatement,"opdaggregatedspace",doctorDepartments);
       		
          while(doctorDepartmentQuery.next())
          {
        	  department.add(doctorDepartmentQuery.getString("doctor_department"));
          }
   		
   
          for(String departmentList : department)
          {
        		  
        	  String doctorDepartmentsCount = "select count(doctor_department) from user_doctor where doctor_department='"+departmentList+"';";
     		 
              ResultSet doctorDepartmentCountQuery = OPDCustomeDataBase.excecuteOPDQuery(OPDDBConnections,OPDDBStatement,dbStatement,selectTableStatement,"opdaggregatedspace",doctorDepartmentsCount);
            		
               while(doctorDepartmentCountQuery.next())
               {
            	   departmentCount.add(doctorDepartmentCountQuery.getString("count(doctor_department)"));
               }
          }
          
          Map<String,Object> DepartmentsListACount = new LinkedHashMap<String, Object>();
          
          DepartmentsListACount.put("departments",department);
          DepartmentsListACount.put("departmentcounts",departmentCount);
          
          session.setAttribute("departmentlistcount", DepartmentsListACount);
          
          return new OPDCustomEntity(200,session.getAttribute("departmentlistcount"),"success");
          
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
       		System.out.println("---- Department List Count Error ----");
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
       }
       
        public  OPDCustomEntity DepartmentDetails(String department)
        {
        	
        	PreparedStatement dbStatement = null;
      		PreparedStatement selectTableStatement = null;
      		Connection OPDDBConnections = null;
      		Statement OPDDBStatement = null;
      		
      		List<String> doctorWard = new ArrayList<String>();
      		List<String> doctorName = new ArrayList<String>();
      		List<String> doctorPhoneNo = new ArrayList<String>();
      		
    		 try {
        		 DBOperator OPDCustomeDataBase = new DBOperator();
        	    
        		 String OPDDBURL = "jdbc:mysql://".concat(PropertiesFiles.getHost()).concat(":").concat(PropertiesFiles.getPort()).concat("/?enabledTLSProtocols=TLSv1.2");
        		 OPDDBConnections = DriverManager.getConnection(OPDDBURL, PropertiesFiles.getUsername(), PropertiesFiles.getPassword());
        	
        		 String doctorCount = "select doctor_ward,doctor_name,doctor_phoneno from user_doctor where doctor_department='"+department+"';";
        		 
        	 ResultSet gettingTableValus = OPDCustomeDataBase.excecuteOPDQuery(OPDDBConnections,OPDDBStatement,dbStatement,selectTableStatement,"opdaggregatedspace",doctorCount);
        		
             while(gettingTableValus.next())
             {
            	 doctorWard.add(gettingTableValus.getString("doctor_ward"));
                 doctorName.add(gettingTableValus.getNString("doctor_name"));
                 doctorPhoneNo.add(gettingTableValus.getNString("doctor_phoneno"));
             }
            
             Map<String,Object> DepartmentsDetails = new LinkedHashMap<String, Object>();
             
             DepartmentsDetails.put("doctorward",doctorWard);
             DepartmentsDetails.put("doctorname",doctorName);
             DepartmentsDetails.put("doctorphoneno",doctorPhoneNo);
             
             return new OPDCustomEntity(200,DepartmentsDetails,"success");
             
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
        		System.out.println("---- Department Details Error ----");
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
        }
        
        public OPDCustomEntity analyzerExcelStateDownloard(HttpSession session)
        {
        	
        	List<String> overAllState = new ArrayList<String>();
    		
    		try
    		{
    	
    			List<ArrayList<Object>> stateList
            = new ArrayList<ArrayList<Object>>();
    		
    		stateList.add(new ArrayList<Object>());
	     	 
    		stateList.get(0).addAll(Arrays.asList("State","Count"));
    			
    		List<String> OPDState = OPDRequestRepoData.findStatesList();
    		List<String> OPDRejectState = OPDRejectRepoData.rejectListOState();
    		List<String> tokenState = tokenPendingApprovalRequestRepo.listOfState();
    		List<String> tokenRejectState = tokenRejectRequestRepo.listOfStates();
    		
    		OPDState.addAll(OPDRejectState);
    		OPDState.addAll(tokenState);
    		OPDState.addAll(tokenRejectState);
    		
    		
    		overAllState.addAll(0, OPDState);
    		
    		Set<String> overAllStateDistinct = new LinkedHashSet<String>();
    		
    		for(String aOverAllStateDistinct : overAllState)
    		{
    			overAllStateDistinct.add(aOverAllStateDistinct);
    		}
    		
    		int sno=1;
    		
    		for(String aState : overAllStateDistinct)
    		{
    			
    		int OPDStateCount =	OPDRequestRepoData.stateCount(aState);
    		int OPDRejectStateCount = OPDRejectRepoData.listRejectCount(aState);
    		int tokenStateCount = tokenPendingApprovalRequestRepo.stateCount(aState);
    		int tokenRejectStateCoubt = tokenRejectRequestRepo.stateCount(aState);
    		
    		Integer stateCount = OPDStateCount+OPDRejectStateCount+tokenStateCount+tokenRejectStateCoubt;
    		
    		stateList.add(new ArrayList<Object>());
    		
    		stateList.get(sno).addAll(Arrays.asList(aState,stateCount));
    		
    		sno++;
    		}
    		
    		session.setAttribute("statelistcount", stateList);
    		
    		return new OPDCustomEntity(200,session.getAttribute("statelistcount"),"success");
    		
    		}catch (Exception e) {
    		System.out.println("-- Analyzer Excel State Downloard Error --");
    		return new OPDCustomEntity(400,"non-success");
    		}
        }
        
        public OPDCustomEntity analyzerExcelStateListDownloard(String stateDwnExcel)
        {
        	try {
         	   
        		List<ArrayList<Object>> stateOverAllList
                = new ArrayList<ArrayList<Object>>();
     		   
     	 List<String> OPDacitiesDetailsState = OPDRequestRepoData.aCityDetailsIState(stateDwnExcel);
     	 
     	 List<String> OPDaHospitalDetailsState =  OPDRequestRepoData.aHospitalDetailsIState(stateDwnExcel);
     	 
          List<String> OPDRejectacitiesDetailsState = OPDRejectRepoData.aCityDetailsIState(stateDwnExcel);
     	 
     	 List<String> OPDRejectaHospitalDetailsState =  OPDRejectRepoData.aHospitalDetailsIState(stateDwnExcel);
     	 
          List<String> tokenacitiesDetailsState = tokenPendingApprovalRequestRepo.aCityDetailsIState(stateDwnExcel);
     	 
     	 List<String> tokenaHospitalDetailsState =  tokenPendingApprovalRequestRepo.aHospitalDetailsIState(stateDwnExcel);
     	 
          List<String> tokenRejectacitiesDetailsState = tokenRejectRequestRepo.aCityDetailsIState(stateDwnExcel);
     	 
     	 List<String> tokenRejectaHospitalDetailsState =  tokenRejectRequestRepo.aHospitalDetailsIState(stateDwnExcel);
     	     	 
          OPDacitiesDetailsState.addAll(OPDRejectacitiesDetailsState);
          OPDacitiesDetailsState.addAll(tokenacitiesDetailsState);
          OPDacitiesDetailsState.addAll(tokenRejectacitiesDetailsState);
     	 
          OPDaHospitalDetailsState.addAll(OPDRejectaHospitalDetailsState);
     	 OPDaHospitalDetailsState.addAll(tokenaHospitalDetailsState);
     	 OPDaHospitalDetailsState.addAll(tokenRejectaHospitalDetailsState);
     	 
     	stateOverAllList.add(new ArrayList<Object>());
    	 
     	stateOverAllList.get(0).addAll(Arrays.asList("Cities","Hospitals"));
     	 
     	 for(int i=0;i<OPDacitiesDetailsState.size();i++)
     	 {
     		 for(int j=0;j<OPDaHospitalDetailsState.size();j++)
     		 {
     			 if(i==j)
     			 {
     			stateOverAllList.add(new ArrayList<Object>());
     			stateOverAllList.get(i+1).addAll(Arrays.asList(OPDacitiesDetailsState.get(i),OPDaHospitalDetailsState.get(j)));
     			 
     			 }
     		 }
     			 
     		 }
     	 
     	return new OPDCustomEntity(200,stateOverAllList,"success");
     	
     	   }
     	   catch (Exception e) {
     		   System.out.println("-- List Of State Excel Dwn Error --");
     			return new OPDCustomEntity(400,"non-success");
 		}
        }
        
       
        	public OPDCustomEntity analyzerExcelCityeDownloard(HttpSession session)
           	{
           	
           		List<String> overAllCities = new ArrayList<String>();
           		
           		try
           		{
           		
           			List<ArrayList<Object>> citiesList
                    = new ArrayList<ArrayList<Object>>();
           		
           			citiesList.add(new ArrayList<Object>());
       	     	 
           			citiesList.get(0).addAll(Arrays.asList("Cities","Count"));
           			
           		List<String> OPDCities = OPDRequestRepoData.findCitiesList();
           		List<String> OPDRejectCities = OPDRejectRepoData.rejectListOCities();
           		List<String> tokencities = tokenPendingApprovalRequestRepo.listOfCities();
           		List<String> tokenRejectCities = tokenRejectRequestRepo.listOfCities();
           		
           		OPDCities.addAll(OPDRejectCities);
           		OPDCities.addAll(tokencities);
           		OPDCities.addAll(tokenRejectCities);
           		
           		overAllCities.addAll(0, OPDCities);
           		
           		Set<String> overAllCitiesDistinct = new LinkedHashSet<String>();
           		
           		for(String aOverAllCitiesDistinct : overAllCities)
           		{
           			overAllCitiesDistinct.add(aOverAllCitiesDistinct);
           		}
           		
           		int sno=1;
           		
           		for(String aCitites : overAllCitiesDistinct)
           		{
           			
           		int OPDStateCount =	OPDRequestRepoData.citiesCount(aCitites);
           		int OPDRejectStateCount = OPDRejectRepoData.listRejectCitiesCount(aCitites);
           		int tokenStateCount = tokenPendingApprovalRequestRepo.citiesCount(aCitites);
           		int tokenRejectStateCoubt = tokenRejectRequestRepo.citiesCount(aCitites);
           		
          	    Integer	CitiesCount = OPDStateCount+OPDRejectStateCount+tokenStateCount+tokenRejectStateCoubt;
          	   
          	    citiesList.add(new ArrayList<Object>());
        		
             	citiesList.get(sno).addAll(Arrays.asList(aCitites,CitiesCount));
        		
        		sno++;
           		}
           	  	
           		session.setAttribute("cityexcellistcount", citiesList);
           		
           		return new OPDCustomEntity(200,session.getAttribute("cityexcellistcount"),"success");
           		
           		}catch (Exception e) {
           		System.out.println("-- Analyzer Excel Citye Downloard Error --");
           		return new OPDCustomEntity(400,"non-success");
           		
           		}	
           	}
        
        	 public OPDCustomEntity analyzerExcelCityeListDownloard(String cityDwn)
        	    {
        	 	   try {
         	 		   
        	 		  List<ArrayList<Object>> citiesOverAllList
                      = new ArrayList<ArrayList<Object>>();
        	 		   
        	 	 List<String> OPDaStateDetailsCity = OPDRequestRepoData.aStateDetailsICity(cityDwn);
        	 	 
        	 	 List<String> OPDaHospitalDetailsCity =  OPDRequestRepoData.aHospitalDetailsICity(cityDwn);
        	 	 
        	      List<String> OPDRejectacitiesDetailsCity = OPDRejectRepoData.aStateDetailsICity(cityDwn);
        	 	 
        	 	 List<String> OPDRejectaHospitalDetailsCity =  OPDRejectRepoData.aHospitalDetailsICity(cityDwn);
        	 	 
        	      List<String> tokenacitiesDetailsCity = tokenPendingApprovalRequestRepo.aStateDetailsICity(cityDwn);
        	 	 
        	 	 List<String> tokenaHospitalDetailsCity =  tokenPendingApprovalRequestRepo.aHospitalDetailsICity(cityDwn);
        	 	 
        	      List<String> tokenRejectacitiesDetailsCity = tokenRejectRequestRepo.aStateDetailsICity(cityDwn);
        	 	 
        	 	 List<String> tokenRejectaHospitalDetailsCity =  tokenRejectRequestRepo.aHospitalDetailsICity(cityDwn);
        	 	     	 
        	 	OPDaStateDetailsCity.addAll(OPDRejectacitiesDetailsCity);
        	 	OPDaStateDetailsCity.addAll(tokenacitiesDetailsCity);
        	 	OPDaStateDetailsCity.addAll(tokenRejectacitiesDetailsCity);
        	 	 
        	 	OPDaHospitalDetailsCity.addAll(OPDRejectaHospitalDetailsCity);
        	 	OPDaHospitalDetailsCity.addAll(tokenaHospitalDetailsCity);
        	 	OPDaHospitalDetailsCity.addAll(tokenRejectaHospitalDetailsCity);
        	 	 
        	 	citiesOverAllList.add(new ArrayList<Object>());
           	 
        	 	citiesOverAllList.get(0).addAll(Arrays.asList("State","Hospitals"));
        	 	 
        	 	for(int i=0;i<OPDaStateDetailsCity.size();i++)
            	 {
            		 for(int j=0;j<OPDaHospitalDetailsCity.size();j++)
            		 {
            			 if(i==j)
            			 {
            				 citiesOverAllList.add(new ArrayList<Object>());
            				 citiesOverAllList.get(i+1).addAll(Arrays.asList(OPDaStateDetailsCity.get(i),OPDaHospitalDetailsCity.get(j)));
            			 
            			 }
            		 }
            			 
            		 }
        	 	 
        	 	return new OPDCustomEntity(200,citiesOverAllList,"success");
        	 	
        	 	   }
        	 	   catch (Exception e) {
        	 		   System.out.println("-- Analyzer Excel Citye List Downloard Error --");
        	 			return new OPDCustomEntity(400,"non-success");
        			}
        	    }
        
        	 public OPDCustomEntity anaylzerDepartmentDwnListACount(HttpSession session)
             {

         		PreparedStatement dbStatement = null;
           		PreparedStatement selectTableStatement = null;
           		Connection OPDDBConnections = null;
           		Statement OPDDBStatement = null;
           		
           		int sno=1;
           		
           		List<String> department = new ArrayList<String>();
         		
         		 try {
         			 
         			List<ArrayList<Object>> departmentCountList
                    = new ArrayList<ArrayList<Object>>();
            		
         			departmentCountList.add(new ArrayList<Object>());
        	     	 
         			departmentCountList.get(0).addAll(Arrays.asList("Departments","Count"));
         			 
             		 DBOperator OPDCustomeDataBase = new DBOperator(); 
             	    
             		 String OPDDBURL = "jdbc:mysql://".concat(PropertiesFiles.getHost()).concat(":").concat(PropertiesFiles.getPort()).concat("/?enabledTLSProtocols=TLSv1.2");
             		 OPDDBConnections = DriverManager.getConnection(OPDDBURL, PropertiesFiles.getUsername(), PropertiesFiles.getPassword());
             	
             		 String doctorDepartments = "select doctor_department from user_doctor order by doctor_department ASC;";
             		 
             	 ResultSet doctorDepartmentQuery = OPDCustomeDataBase.excecuteOPDQuery(OPDDBConnections,OPDDBStatement,dbStatement,selectTableStatement,"opdaggregatedspace",doctorDepartments);
             		
                while(doctorDepartmentQuery.next())
                {
              	  department.add(doctorDepartmentQuery.getString("doctor_department"));
                }
         		
         
                for(String departmentList : department)
                {
              	  
              	  String doctorDepartmentsCount = "select count(doctor_department) from user_doctor where doctor_department='"+departmentList+"';";
           		 
                   ResultSet doctorDepartmentCountQuery = OPDCustomeDataBase.excecuteOPDQuery(OPDDBConnections,OPDDBStatement,dbStatement,selectTableStatement,"opdaggregatedspace",doctorDepartmentsCount);
                  		
                     while(doctorDepartmentCountQuery.next())
                     {
                   	   
                  	 departmentCountList.add(new ArrayList<Object>());
             		
                  	departmentCountList.get(sno).addAll(Arrays.asList(departmentList,doctorDepartmentCountQuery.getString("count(doctor_department)")));
                  	sno++;
                  	   
                     }
                }
                
                
                session.setAttribute("departmentlistcount", departmentCountList);
                
                return new OPDCustomEntity(200,session.getAttribute("departmentlistcount"),"success");
                
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
             		System.out.println("---- Department List Count Error ----");
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
             }
        
        public OPDCustomEntity anaylzerDepartmentListDwnList(String department)
        {

        	PreparedStatement dbStatement = null;
      		PreparedStatement selectTableStatement = null;
      		Connection OPDDBConnections = null;
      		Statement OPDDBStatement = null;
      		
      		List<String> doctorWard = new ArrayList<String>();
      		List<String> doctorName = new ArrayList<String>();
      		List<String> doctorPhoneNo = new ArrayList<String>();
      		
      		List<ArrayList<Object>> departmentList
            = new ArrayList<ArrayList<Object>>();
      		
    		 try {
        		 DBOperator OPDCustomeDataBase = new DBOperator(); 
        	    
        		 String OPDDBURL = "jdbc:mysql://".concat(PropertiesFiles.getHost()).concat(":").concat(PropertiesFiles.getPort()).concat("/?enabledTLSProtocols=TLSv1.2");
        		 OPDDBConnections = DriverManager.getConnection(OPDDBURL, PropertiesFiles.getUsername(), PropertiesFiles.getPassword());
        	
        		 String doctorCount = "select doctor_ward,doctor_name,doctor_phoneno from user_doctor where doctor_department='"+department+"';";
        		 
        	 ResultSet gettingTableValus = OPDCustomeDataBase.excecuteOPDQuery(OPDDBConnections,OPDDBStatement,dbStatement,selectTableStatement,"opdaggregatedspace",doctorCount);
        		
             while(gettingTableValus.next())
    		 {
             doctorWard.add(gettingTableValus.getString("doctor_ward"));
             doctorName.add(gettingTableValus.getNString("doctor_name"));
             doctorPhoneNo.add(gettingTableValus.getNString("doctor_phoneno"));
    		 }
             departmentList.add(new ArrayList<Object>());
              	 
             departmentList.get(0).addAll(Arrays.asList("Doctor Name","Doctor Ward","Doctor Phone Number"));
        	 	 
        	 	
            		 for(int i=0;i<doctorName.size();i++)
            		 {
            			 for(int j=0;j<doctorWard.size();j++)
                    	 {
            				 
            			 if(i==j)
            			 {
            				 for(int k=0;k<doctorPhoneNo.size();k++)
            				 {
            					 if(i==k)
            					 {
            						 departmentList.add(new ArrayList<Object>());
                    				 departmentList.get(i+1).addAll(Arrays.asList(doctorName.get(i),doctorWard.get(j),doctorPhoneNo.get(k)));
            					 }
            				 }
            			
            			 }
            		 }
            			 
            		 }
              
             return new OPDCustomEntity(200,departmentList,"success");
             
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
        		System.out.println("---- Department Details List Excel Downloader Error ----");
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
        }
}
