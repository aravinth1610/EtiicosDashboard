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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiicos.customeEntity.OPDCustomEntity;
import com.etiicos.etiicosCustomeDB.DBOperator;
import com.etiicos.localException.LocalException;
import com.etiicos.properties.PropertiesClassPath;
import com.etiicos.repository.PerDoctorPriceRepository;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;


@Service
public class DepartmentsServices {

	@Autowired
	private PropertiesClassPath propertiesFiles;
	
	@Autowired
	private PerDoctorPriceRepository perDoctorPriceRepo;
	
	public OPDCustomEntity gettingPerDoctorPrice(HttpSession session)
	{
		try
		{
		String rupees =	perDoctorPriceRepo.perDoctorRupees();
			
		session.setAttribute("rupees", rupees);
		
		return new OPDCustomEntity(200,session.getAttribute("rupees"),"success");
				
		}catch (Exception e) {
			System.out.println("---- Getting rupees Error ----");
        	return new OPDCustomEntity(400, "non-success");
		}
	}
	
	

	public OPDCustomEntity updatePerDoctorPrice(String price)
	{
		try
		{
			Integer isUpdated = perDoctorPriceRepo.updateDoctorRupees(price);
	
			System.out.println(isUpdated);
			
			if(isUpdated == 1)
			{
				return new OPDCustomEntity(200,"success");
			}
			else
			{
				return new OPDCustomEntity(400, "non-success");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("----Update rupees Error ----");
        	return new OPDCustomEntity(400, "non-success");
		}
	}
	
	
	public OPDCustomEntity AddDepartmentsData(String departmentsData)
	{
		
		String departments=departmentsData.toUpperCase();
		
		Boolean tokenMulipleTableResultValue=false;
		Boolean doctorMulipleTableResultValue=false;
		String departmentList="";
		 try {
			 
			 String lastValue = departments.substring(departments.length()-1,departments.length());
		
			 if(lastValue.equals(","))
			 {
			   departmentList = departments.concat(",");
			 }
			 else
			 {
				 departmentList =  departments;
			 }
			 
				DBOperator CustomeDataBase = new DBOperator(propertiesFiles.getRequestHost(),propertiesFiles.getRequestPort(),propertiesFiles.getRequestUsername(),propertiesFiles.getRequestPassword()); 
			
					String departmentData = departmentList.substring(0,departmentList.length());
					
					String[] splitOfDepartment = departmentData.split(",");
					
					for(String singleDepartment:splitOfDepartment)
					{
						tokenMulipleTableResultValue = CustomeDataBase.insertTableValues("tokenaggregatedspace","token_department",Arrays.asList("depatments"),singleDepartment.trim());
						
						if(tokenMulipleTableResultValue)
						{
						doctorMulipleTableResultValue = CustomeDataBase.insertTableValues("opdaggregatedspace","doctor_department",Arrays.asList("depatments"),singleDepartment.trim());
						}
					}
					
					if(doctorMulipleTableResultValue)
					{
						return new OPDCustomEntity(200,"success"); 	
					}
					else
					{
						return new OPDCustomEntity(400,"non-success"); 
					}
		 
    	} 
 		 catch (Exception e) {
 	    		System.out.println("----Add Department Data Error ----");
        	return new OPDCustomEntity(400, "non-success");
    	}
  	} 
			
	
	public OPDCustomEntity listAllDepartments(HttpSession session)
	{
		PreparedStatement dbStatement = null;
 		PreparedStatement selectTableStatement = null;
 		Connection tokenDBConnections = null;
 		Statement tokenDBStatement = null;	
 		
 		 String departmentList = "";
 		
		 try {
    		
    		 DBOperator tokenCustomeDataBaseResult = new DBOperator();
    		 String tokenDBURL = "jdbc:mysql://".concat(propertiesFiles.getHost()).concat(":").concat(propertiesFiles.getPort()).concat("/?enabledTLSProtocols=TLSv1.2");
    		 tokenDBConnections = DriverManager.getConnection(tokenDBURL, propertiesFiles.getUsername(), propertiesFiles.getPassword());
    	
    		 String listingDepartmentQuery = "select depatments from token_department order by depatments ASC;";
    		 
    	     ResultSet tableResultValue = tokenCustomeDataBaseResult.excecuteOPDQuery(tokenDBConnections,tokenDBStatement,dbStatement,selectTableStatement,"tokenaggregatedspace",listingDepartmentQuery);
    	
     	while(tableResultValue.next())
 		{
     		 departmentList+="<li><a href=\"#\"><div style=\"display: flex;justify-content: space-between;\"><label for=\"dept\">"+tableResultValue.getString("depatments")+"</label><span style='color:red;cursor:pointer;' onclick=deletedepartment(this) value='"+tableResultValue.getString("depatments")+"'><i class=\"bi bi-trash3\"></i></span></div></a></li>";
      	}
     	
     	session.setAttribute("alldepartments", departmentList);
     
     	Map<String,Object> listOfDepartments = new LinkedHashMap<String,Object>();
     	
     	listOfDepartments.put("departmens",departmentList);
     	
     	return new OPDCustomEntity(200, session.getAttribute("alldepartments"), "success");
     	
    	} 
    	catch(SQLSyntaxErrorException syntaxException)
 		{
 			if(syntaxException.getMessage().contains("already exists"))
 			{
 				throw new LocalException("EXISTING_TABLE");
 			}
 			return new OPDCustomEntity(400, "non-success");
 		}
 		catch(CommunicationsException communicationException)
 		{
 			communicationException.printStackTrace();
 			return new OPDCustomEntity(400, "non-success");
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
  			return new OPDCustomEntity(400, "non-success");
 		}
 		 catch (Exception e) {
        		System.out.println("---- Department List Data Error ----");
        	return new OPDCustomEntity(400, "non-success");
    	}
    	finally 
  		{
  		    if (tokenDBConnections != null) {
  		        try {
  		        	tokenDBConnections.close();
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
		    if (tokenDBStatement != null) 
		    {
		        try 
		        {
		        	tokenDBStatement.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
  		}
	}
	
	public OPDCustomEntity deleteDepartment(String department)
	{
		try
		{
			DBOperator CustomeDataBase = new DBOperator(propertiesFiles.getRequestHost(),propertiesFiles.getRequestPort(),propertiesFiles.getRequestUsername(),propertiesFiles.getRequestPassword()); 

		Boolean OPDDoctorDelete = CustomeDataBase.deleteTableValues("opdaggregatedspace","doctor_department","depatments",department);
		
		
		if(OPDDoctorDelete)
		{
			Boolean tokenDoctorDelete = CustomeDataBase.deleteTableValues("tokenaggregatedspace","token_department","depatments",department);
			
			if(tokenDoctorDelete)
			{
				return new OPDCustomEntity(200, "success");
			}else {return new OPDCustomEntity(400, "non-success");}
			
		}
		else
		{
			return new OPDCustomEntity(400, "non-success");
		}
			
		}catch (Exception e) {
			System.out.println("-- Delete Department Error --");
			return new OPDCustomEntity(400, "non-success");
		}
	}
	
}
