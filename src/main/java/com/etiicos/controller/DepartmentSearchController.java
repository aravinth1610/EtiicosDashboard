package com.etiicos.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiicos.customeEntity.OPDCustomEntity;
import com.etiicos.etiicosServices.DepartmentsServices;

@RestController
public class DepartmentSearchController {

	@Autowired
	DepartmentsServices  viewDepartmentsServices;
	
	
	@GetMapping("/innerprocess/etiicos/getdoctorprice")
	public OPDCustomEntity gettingPerDoctor(HttpSession session)
	{
		return viewDepartmentsServices.gettingPerDoctorPrice(session);
	}
	
	@PostMapping("/innerprocess/etiicos/updateprice/{price}")
	private OPDCustomEntity updatePerDoctor(@PathVariable("price") String price,HttpSession session)
	{
		session.setAttribute("updateprice", price);
		
		String updatePrice = (String) session.getAttribute("updateprice");
		
		System.out.println(updatePrice);
		
		return viewDepartmentsServices.updatePerDoctorPrice(updatePrice);
	}
	
	
	@PostMapping("/innerprocess/etiicos/adddepartment")
	private OPDCustomEntity AddDepartments(String departments,HttpSession session)
	{
		session.setAttribute("listdepartments", departments);
		
		String departmentData = (String) session.getAttribute("listdepartments");
		
		return viewDepartmentsServices.AddDepartmentsData(departmentData);
	}
	
	@GetMapping("/innerprocess/etiicos/listdepartments")
	public OPDCustomEntity listDepartments(HttpSession session)
	{
		return viewDepartmentsServices.listAllDepartments(session);
	}
	
	@PostMapping("/innerprocess/etiicos/deletedepartment")
	private OPDCustomEntity deleteDepartmentsData(String departmentData,HttpSession session)
	{
		session.setAttribute("deletedepartments", departmentData);
		
		String deleteDepartment = (String) session.getAttribute("deletedepartments");
		
		return viewDepartmentsServices.deleteDepartment(deleteDepartment);
	}
	
}
