function listDepartment() {
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("a")[0];
        txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}

gettingPerDoctorRupees();

function gettingPerDoctorRupees()
{
	$.get("innerprocess/etiicos/getdoctorprice",function(rupeesGettingResult){
		
		if(rupeesGettingResult.status == 200 && rupeesGettingResult.message == 'success')
		{
			$("#perdoctor").val(rupeesGettingResult.content);
			listOfAllDepartments();
		}
		
		});
}


$("#editpricebtn").on('click',function(){
	
	$("#perdoctor").prop('disabled', false);

})

$("#updatepricebtn").on('click',function(){
	
	var priceValue = $("#perdoctor").val();
	
	$.post("innerprocess/etiicos/updateprice/"+priceValue+"",function(isUpdatedResult){
		
		if(isUpdatedResult.status == 200 && isUpdatedResult.message == 'success')
		{
			$("#perdoctorupate").html("Per Doctor Price is Confirm");
			$("#perdoctor").prop('disabled', true);
			gettingPerDoctorRupees();
		}
		else
		{
			$("#perdoctorupate").html("Per Doctor Price is not Confirm");
		}
		})
	
})


function  listOfAllDepartments()
{
$.get("innerprocess/etiicos/listdepartments",function(departmentResult){
    
    if(departmentResult.status == 200 && departmentResult.message == "success")
    {
	$("#listdepartments").val(" ")
	$("#myUL").html(departmentResult.content)
    }
    else
    {
        $("#successerrormessage").html("contact Etiicos Team")
        
    }
});
}
$("#adddepartmentbtn").on("click",function(){
	
var departmentsData = $("#listdepartments").val();
	
	
	$.post("innerprocess/etiicos/adddepartment",{departments:departmentsData},function(departmentaddResult){
    
    
    if(departmentaddResult.status == 200 && departmentaddResult.message == "success")
    {
	listOfAllDepartments();
    }
    else
    {
        
    }
	});
})


function deletedepartment(departmentValue)
{
	var department = departmentValue.getAttribute("value");
	
	
	$.post("innerprocess/etiicos/deletedepartment",{departmentData:department},function(deleteDepartmentResult){
		
		if(deleteDepartmentResult.status == 200 && deleteDepartmentResult.message == 'success')
		{
			listOfAllDepartments();
		}
		else{}
	})
	
	
}



