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

listOfAllDepartments();

function  listOfAllDepartments()
{
$.get("innerprocess/etiicos/listdepartments",function(departmentResult){
    
    if(departmentResult.status == 200 && departmentResult.message == "success")
    {
	$("#myUL").html(departmentResult.content)
    }
    else
    {
        $("#successerrormessage").html("contact Etiicos Team")
        
    }
});
}
$("#adddepartmentbtn").on("click",function(){
	console.log("dfer23")
var departmentsData = $("#listdepartments").val();
	
	console.log(departmentsData)
	
	$.post("innerprocess/etiicos/adddepartment",{departments:departmentsData},function(departmentaddResult){
    
    console.log(departmentaddResult)
    
    if(departmentaddResult.status == 200 && departmentaddResult.message == "success")
    {
	listOfAllDepartments();
    }
    else
    {
        
    }
	});
})






