
OPDTokenAnalyer();

$("#btnreload").on('click',function(){
	OPDTokenAnalyer();
})


function OPDTokenAnalyer()
{

$.get("innerprocess/etiicos/installedcount",function(installedResult){
		
	if(installedResult.status == 200 && installedResult.message == 'success')
	{
	var installedAppend;
	
		
		 installedAppend += "<tr onclick=statemodel()>";
		 installedAppend += "<td>State</td>";
		 installedAppend += "<td >"+installedResult.content.stateinstalledcount+"</td>";
		 installedAppend += "</tr>"
		 
		 installedAppend += "<tr onclick=citymodel()>";
		 installedAppend += "<td>Cities</td>";
		 installedAppend += "<td>"+installedResult.content.citiesinstalledcount+"</td>";
		 installedAppend += "</tr>"
	
	     installedAppend += "<tr onclick=departmentmodel()>";
		 installedAppend += "<td>Departments</td>";
		 installedAppend += "<td>"+installedResult.content.installeddepartment+"</td>";
		 installedAppend += "</tr>"	
	
	$("#homedashboardinstalled").html(installedAppend)
	}else{}
});
}

function statemodel()
{
		
	$.get("innerprocess/etiicos/statecount",function(stateResultData){
		
		
		if(stateResultData.status == 200 && stateResultData.message == 'success')
		{
			var stateTable="";
	
	stateTable += '<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">';
    stateTable += '<div class="modal-dialog modal-lg modal-dialog-centered"><div class="modal-content"><div class="modal-header"><h5 class="modal-title" id="staticBackdropLabel">State<span onclick="stateDwn()" style="padding: 10px;font-size: 23px;padding-left: 35px;font-weight: 600;cursor: pointer;"><i class="bi bi-download"></i></span></h5>';
    stateTable += '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div><div class="modal-body"><table  class="table table-bordered table-hover mytable"><thead>';
    stateTable += '<tr><th scope="col">S.No</th><th>State</th><th>Count</th></tr></thead><tbody style="cursor: pointer;font-size=17px;">';
    
    $.each(stateResultData.content.state,function(stateIndex,stateResult){
	
	var sno = stateIndex+1;
	
	stateTable += "<tr onclick=listOfModeltstate(this)>";
	stateTable += "<td>"+sno+"</td>";
	
	$.each(stateResultData.content.state,function(stateDataIndex,stateDataResult){
		
		if(stateIndex == stateDataIndex)
		{
	        stateTable += "<td class='pd-name'>"+stateDataResult+"</td>";
		}	
	})
	
	
	$.each(stateResultData.content.count,function(stateCountIndex,stateCountResult){
		
		if(stateIndex == stateCountIndex)
		{
	        stateTable += "<td>"+stateCountResult+"</td>";
		}	
	})
	stateTable += "</tr>";
})

  stateTable += '</tbody></table></div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
  stateTable += '</div></div></div></div>';
  $("#statemodel").html(stateTable);
  $('.mytable').DataTable();
  modelStateShow();  
		}
	})

}
	function modelStateShow()
	{
			$('#staticBackdrop').modal('show');	
	}
	
function listOfModeltstate(state)
{
	
	    var sateCurrentRow=$(state).closest("tr"); 
	    var statecurrentValue=sateCurrentRow.find(".pd-name").html(); 
        var stateValue=statecurrentValue;
        
   
	$.post("innerprocess/etiicos/astatedetails",{state:stateValue},function(stateDetailsListResult){
		
		if(stateDetailsListResult.status == 200 && stateDetailsListResult.message == 'success')
		{
			var stateListTable="";
			
	stateListTable += '<div class="modal fade" id="liststaticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">';
    stateListTable += '<div class="modal-dialog modal-xl modal-dialog-centered"><div class="modal-content"><div class="modal-header"><h5 class="modal-title">'+stateValue+' Details<span style="padding: 10px;font-size: 23px;padding-left: 35px;font-weight: 600;cursor: pointer;" onclick="statelistDwn(this)" value="'+stateValue+'"><i class="bi bi-download"></i></span></h5>';
    stateListTable += '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div><div class="modal-body"><table  class="table table-bordered table-hover mytable"><thead>';
    stateListTable += '<tr><th scope="col">S.No</th><th>Cities</th><th>Hospitals</th></tr></thead><tbody style="cursor: pointer;font-size=17px;">';
    
    $.each(stateDetailsListResult.content.cities,function(citysnoIndex,citysnoResult){
	
	var sno = citysnoIndex+1;
	stateListTable += "<tr>";
	stateListTable +="<td>"+sno+"</td>";
	
	 $.each(stateDetailsListResult.content.cities,function(cityIndex,cityResult){

    if(citysnoIndex == cityIndex)
		{
	stateListTable +="<td>"+cityResult+"</td>";
	}
	})
	
	 $.each(stateDetailsListResult.content.hospitals,function(hospitalIndex,hospitalResult){

    if(citysnoIndex == hospitalIndex)
		{
	stateListTable +="<td>"+hospitalResult+"</td>";
	}
	})
	
	stateListTable += "</tr>";	
})

  stateListTable += '</tbody></table></div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
  stateListTable += '</div></div></div></div>';
  
  $("#statemodellist").html(stateListTable);
  $('.mytable').DataTable();
  listOfModelStateShow();  

		}
		
	})
}	
	
	function listOfModelStateShow()
	{
			$('#liststaticBackdrop').modal('show');	
	}
	
	//////////////
	
function citymodel()
{
	$.get("innerprocess/etiicos/citiescount",function(citiesResultData){
		
		
		if(citiesResultData.status == 200 && citiesResultData.message == 'success')
		{
			var cityTable="";
	
	cityTable += '<div class="modal fade" id="citystaticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">';
    cityTable += '<div class="modal-dialog modal-lg modal-dialog-centered"><div class="modal-content"><div class="modal-header"><h5 class="modal-title">State<span onclick="cityDwn()" style="padding: 10px;font-size: 23px;padding-left: 35px;font-weight: 600;cursor: pointer;"><i class="bi bi-download"></i></span></h5>';
    cityTable += '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div><div class="modal-body"><table  class="table table-bordered table-hover mytable"><thead>';
    cityTable += '<tr><th scope="col">S.No</th><th>Cities</th><th>Count</th></tr></thead><tbody style="cursor: pointer;font-size=17px;">';
    
    $.each(citiesResultData.content.city,function(cityIndex,cityeResult){
	
	var sno = cityIndex+1;
	
	
	cityTable += "<tr onclick=listOfModeltCities(this)>";
	cityTable += "<td>"+sno+"</td>";
	
	$.each(citiesResultData.content.city,function(cityeDataIndex,cityDataResult){
		
		if(cityIndex == cityeDataIndex)
		{
	        cityTable += "<td class='pd-name'>"+cityDataResult+"</td>";
		}	
	})
	
	
	$.each(citiesResultData.content.count,function(cityCountIndex,cityCountResult){
		
		if(cityIndex == cityCountIndex)
		{
	        cityTable += "<td>"+cityCountResult+"</td>";
		}	
	})
	cityTable += "</tr>";
})

  cityTable += '</tbody></table></div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
  cityTable += '</div></div></div></div>';
  
  $("#citiesmodel").html(cityTable);
  
  $('.mytable').DataTable();
  
  modelCitiesShow();  
		}
	})

}
	function modelCitiesShow()
	{
			$('#citystaticBackdrop').modal('show');	
	}
	
	
	////--////
	
function listOfModeltCities(city)
{
	
	    var cityCurrentRow=$(city).closest("tr"); 
	    var citycurrentValue=cityCurrentRow.find(".pd-name").html(); 
        var cityValue=citycurrentValue;
     
	$.post("innerprocess/etiicos/acitydetails",{city:cityValue},function(cityDetailsListResult){
		
		if(cityDetailsListResult.status == 200 && cityDetailsListResult.message == 'success')
		{
			var cityListTable="";
			
	cityListTable += '<div class="modal fade" id="statestaticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">';
    cityListTable += '<div class="modal-dialog modal-xl modal-dialog-centered"><div class="modal-content"><div class="modal-header"><h5 class="modal-title">'+cityValue+' Details<span style="padding: 10px;font-size: 23px;padding-left: 35px;font-weight: 600;cursor: pointer;" onclick="cityListDwn(this)" value="'+cityValue+'"><i class="bi bi-download"></i></span></h5>';
    cityListTable += '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div><div class="modal-body"><table  class="table table-bordered table-hover mytable"><thead>';
    cityListTable += '<tr><th scope="col">S.No</th><th>State</th><th>Hospitals</th></tr></thead><tbody style="cursor: pointer;font-size=17px;">';
    
    $.each(cityDetailsListResult.content.state,function(statesnoIndex,statesnoResult){
	
	var sno = statesnoIndex+1;
	cityListTable += "<tr>";
	cityListTable +="<td>"+sno+"</td>";
	
	 $.each(cityDetailsListResult.content.state,function(stateIndex,stateResult){

    if(statesnoIndex == stateIndex)
		{
	cityListTable +="<td>"+stateResult+"</td>";
	}
	})
	
	 $.each(cityDetailsListResult.content.hospitals,function(hospitalIndex,hospitalResult){

    if(statesnoIndex == hospitalIndex)
		{
	cityListTable +="<td>"+hospitalResult+"</td>";
	}
	})
	
	cityListTable += "</tr>";	
})

  cityListTable += '</tbody></table></div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
  cityListTable += '</div></div></div></div>';
  
  $("#citymodellist").html(cityListTable);
  $('.mytable').DataTable();
  listOfModelCitiesShow();  

		}
		
	})
}	
	
	function listOfModelCitiesShow()
	{
			$('#statestaticBackdrop').modal('show');	
	}
	
	//Departmet Start Model Start//
	
	function departmentmodel()
	{
		
	$.get("innerprocess/etiicos/departmentcounts",function(DepartmentsResultData){
		
		
		if(DepartmentsResultData.status == 200 && DepartmentsResultData.message == 'success')
		{
			var departmentsTable="";
	
	departmentsTable += '<div class="modal fade" id="departmentBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">';
    departmentsTable += '<div class="modal-dialog modal-lg modal-dialog-centered"><div class="modal-content"><div class="modal-header"><h5 class="modal-title" id="staticBackdropLabel">State<span onclick="departmentDwn()" style="padding: 10px;font-size: 23px;padding-left: 35px;font-weight: 600;cursor: pointer;"><i class="bi bi-download"></i></span></h5>';
    departmentsTable += '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div><div class="modal-body"><table  class="table table-bordered table-hover mytable"><thead>';
    departmentsTable += '<tr><th scope="col">S.No</th><th>Department</th><th>Count</th></tr></thead><tbody style="cursor: pointer;font-size=17px;">';
    
    $.each(DepartmentsResultData.content.departments,function(departmentIndex,departmentResult){
	
	var sno = departmentIndex+1;
	
	departmentsTable += "<tr onclick=listOfModeltdepartment(this)>";
	departmentsTable += "<td>"+sno+"</td>";
	
	$.each(DepartmentsResultData.content.departments,function(departmentDataIndex,departmentDataResult){
		
		if(departmentIndex == departmentDataIndex)
		{
	        departmentsTable += "<td class='pd-namedepartment'>"+departmentDataResult+"</td>";
		}	
	})
	
	
	$.each(DepartmentsResultData.content.departmentcounts,function(departmentCountIndex,departmentCountResult){
		
		if(departmentIndex == departmentCountIndex)
		{
	        departmentsTable += "<td>"+departmentCountResult+"</td>";
		}	
	})
	departmentsTable += "</tr>";
})

  departmentsTable += '</tbody></table></div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
  departmentsTable += '</div></div></div></div>';
  $("#departmentsmodel").html(departmentsTable);
  $('.mytable').DataTable();
  modeldepartmentShow();  
		}
	})

}
	function modeldepartmentShow()
	{
			$('#departmentBackdrop').modal('show');	
	}
	
	//Departmet Start Model End//
	
	//Department List Of All Details  Start 
		
function listOfModeltdepartment(department)
{
	    var departmetnCurrentRow=$(department).closest("tr"); 
	    var departementcurrentValue=departmetnCurrentRow.find(".pd-namedepartment").html(); 
        var departmentValue=departementcurrentValue;
        
     	
	$.post("innerprocess/etiicos/adepartmentdetails",{department:departmentValue},function(departmentDetailsListResult){
		
		
		if(departmentDetailsListResult.status == 200 && departmentDetailsListResult.message == 'success')
		{
			var departmentListTable="";
			
	departmentListTable += '<div class="modal fade" id="listdepartmentliststaticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">';
    departmentListTable += '<div class="modal-dialog modal-xl modal-dialog-centered"><div class="modal-content"><div class="modal-header"><h5 class="modal-title">'+departmentValue+' Details<span style="padding: 10px;font-size: 23px;padding-left: 35px;font-weight: 600;cursor: pointer;" onclick="departmentlistDwn(this)" value="'+departmentValue+'"><i class="bi bi-download"></i></span></h5>';
    departmentListTable += '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div><div class="modal-body"><table  class="table table-bordered table-hover mytable"><thead>';
    departmentListTable += '<tr><th scope="col">S.No</th><th>Doctor Name</th><th>Doctor Phone Number</th><th>Doctor Ward</th></tr></thead><tbody style="cursor: pointer;font-size=17px;">';
    
    $.each(departmentDetailsListResult.content.doctorname,function(doctornamesnoIndex,doctornamesnoResult){
	
	var sno = doctornamesnoIndex+1;
	departmentListTable += "<tr>";
	departmentListTable +="<td>"+sno+"</td>";
	
	 $.each(departmentDetailsListResult.content.doctorname,function(doctornameIndex,doctornameResult){

    if(doctornamesnoIndex == doctornameIndex)
		{
	departmentListTable +="<td>"+doctornameResult+"</td>";
	}
	})
	
	 $.each(departmentDetailsListResult.content.doctorphoneno,function(doctorphonenoIndex,doctorphonenoResult){

    if(doctornamesnoIndex == doctorphonenoIndex)
		{
	departmentListTable +="<td>"+doctorphonenoResult+"</td>";
	}
	})
	
	 $.each(departmentDetailsListResult.content.doctorward,function(doctorwardIndex,doctorwardResult){

    if(doctornamesnoIndex == doctorwardIndex)
		{
	departmentListTable +="<td>"+doctorwardResult+"</td>";
	}
	})
	
	departmentListTable += "</tr>";	
})

  departmentListTable += '</tbody></table></div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
  departmentListTable += '</div></div></div></div>';
  
  $("#departmentsmodellist").html(departmentListTable);
  $('.mytable').DataTable();
  listOfModelDepartmentListShow();  

		}
		
	})
}	
	
	function listOfModelDepartmentListShow()
	{
			$('#listdepartmentliststaticBackdrop').modal('show');	
	}
	
	//Department List Of All Details  End
	
	