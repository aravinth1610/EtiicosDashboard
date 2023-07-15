package com.etiicos.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiicos.customeEntity.OPDCustomEntity;
import com.etiicos.etiicosServices.EtiicosAnalyzerServices;

@RestController
public class EtiicosAnalyzerController {

	@Autowired
	private EtiicosAnalyzerServices etiicosAnalyzerServi;
	
	@GetMapping("innerprocess/etiicos/installedcount")
	private OPDCustomEntity InstalledStateCityDepartmentCount(HttpSession session)
	{
		return etiicosAnalyzerServi.registerCityStateDepartmentCount(session);
	}
	
	
	@GetMapping("innerprocess/etiicos/statecount")
	private OPDCustomEntity ListOfStateCount(HttpSession session)
	{
		return etiicosAnalyzerServi.ListOfStateACount(session);
	}
	
	
	@PostMapping("innerprocess/etiicos/astatedetails")
	private OPDCustomEntity ListOfStateDetails(String state,HttpSession session)
	{
		session.setAttribute("statevalue", state);
		
		String stateValue = (String) session.getAttribute("statevalue");
		
		return etiicosAnalyzerServi.listOfState(stateValue);
	}
	
	@GetMapping("innerprocess/etiicos/citiescount")
	private OPDCustomEntity ListOfCitiesCount(HttpSession session)
	{
		return etiicosAnalyzerServi.ListOfCitiesACount(session);
	}
	
	
	@PostMapping("innerprocess/etiicos/acitydetails")
	private OPDCustomEntity ListOfCitiesDetails(String city,HttpSession session)
	{
		session.setAttribute("cityvalue", city);
		
		String cityValue = (String) session.getAttribute("cityvalue");
		
		return etiicosAnalyzerServi.listOfCities(cityValue);
	}
	
	@GetMapping("innerprocess/etiicos/departmentcounts")
	private OPDCustomEntity ListOfDepartmentsCount(HttpSession session)
	{
		return etiicosAnalyzerServi.departmentListACount(session);
	}
	
	@PostMapping("innerprocess/etiicos/adepartmentdetails")
	private OPDCustomEntity ListOfDepartmentsDetails(String department,HttpSession session)
	{
		session.setAttribute("departmentvalue", department);
		
		String departmentValue = (String) session.getAttribute("departmentvalue");
		
		return etiicosAnalyzerServi.DepartmentDetails(departmentValue);
	}
	
	
	@GetMapping("innerprocess/etiicos/stateexceldownloader")
	private OPDCustomEntity stateExceldownloader(HttpSession session)
	{
		return etiicosAnalyzerServi.analyzerExcelStateDownloard(session);
	}
	
	@PostMapping("innerprocess/etiicos/statelistexceldownloader")
	private OPDCustomEntity stateListExceldownloader(String state,HttpSession session)
	{
		session.setAttribute("excelstatevalue", state);
		
		String excelStateValue = (String) session.getAttribute("excelstatevalue");
		
		return etiicosAnalyzerServi.analyzerExcelStateListDownloard(excelStateValue);
	}
	
	@GetMapping("innerprocess/etiicos/cityexceldownloader")
	private OPDCustomEntity cityExceldownloader(HttpSession session)
	{
		return etiicosAnalyzerServi.analyzerExcelCityeDownloard(session);
	}
	
	@PostMapping("innerprocess/etiicos/citylistexceldownloader")
	private OPDCustomEntity cityListExceldownloader(String city,HttpSession session)
	{
		session.setAttribute("excelcityvalue", city);
		
		String excelCityValue = (String) session.getAttribute("excelcityvalue");
		
		return etiicosAnalyzerServi.analyzerExcelCityeListDownloard(excelCityValue);
	}
	
	@GetMapping("innerprocess/etiicos/analyzerdepartmexceldownloader")
	private OPDCustomEntity analyzerDepartmentExceldownloader(HttpSession session)
	{
		return etiicosAnalyzerServi.anaylzerDepartmentDwnListACount(session);
	}
	
	@PostMapping("innerprocess/etiicos/analyzerdepartmeListxceldownloader")
	private OPDCustomEntity analyzerDepartmentListExceldownloader(String department,HttpSession session)
	{
		session.setAttribute("excelDepartmentvalue", department);
		
		String exceldepartmentValue = (String) session.getAttribute("excelDepartmentvalue");
		
		return etiicosAnalyzerServi.anaylzerDepartmentListDwnList(exceldepartmentValue);
	}
}
