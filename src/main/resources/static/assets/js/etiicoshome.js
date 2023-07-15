  
  if(window.location.pathname == "/etiicos/Dashboard")
        {
	dashboardFunctionalCounts();
	
	dashboardFunctional();
	
	
	$("document").ready(function()
   {
	setInterval(function(){
		dashboardFunctionalCounts();

	},5000);
	
});
	
	$("#btnreload").on("click",function(){
		dashboardFunctional();
	});
	
 $("document").ready(function()
 {
	setInterval(function(){
		dashboardFunctional();

	},1000 * 60 * 15);
	
});
  }  


function dashboardFunctionalCounts()
  {
	
 $.get("innerprocess/etiicos/dashboardrequestdatacount/",function(dashboardCallDataResultCount){
	
	if (dashboardCallDataResultCount.status == 200 && dashboardCallDataResultCount.message == "success") {
		
		$("#doctorCount").html(dashboardCallDataResultCount.content.NodoctorCount);
		$("#pendingcallcount").html(dashboardCallDataResultCount.content.pendinghospitalcount);
		$("#approvedcallcount").html(dashboardCallDataResultCount.content.approvedghospitalcount);
		$("#rejectcallcount").html(dashboardCallDataResultCount.content.rejecthospitalcount);
	
	}
	})
	
	}  

  function dashboardFunctional()
  {
	
 $.get("innerprocess/etiicos/dashboardrequestdata/",function(dashboardCallDataResult){
 
 var DashboardDetails="";
 let sno=0;
 
	 if (dashboardCallDataResult.status == 200 && dashboardCallDataResult.message == "success") {
		
		$.each(dashboardCallDataResult.content.pendinggmail,function(pendingIndex,pendingResult)
		{
		   sno=sno+1;
		   DashboardDetails+="<tr>";
           DashboardDetails+="<th scope='row'><a href='#'>"+sno+"</a></th>";
           
           $.each(dashboardCallDataResult.content.pendinghospial,function(pendingHospitalIndex,pendingHospitalResult)
           {
           if(pendingIndex == pendingHospitalIndex)
           {
	         
	         DashboardDetails+="<td>"+pendingHospitalResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.pendinghospialID,function(pendingHospitalIDIndex,pendingHospitalIDResult)
           {
           if(pendingIndex == pendingHospitalIDIndex)
           {
	         
	         DashboardDetails+="<td>"+pendingHospitalIDResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.pendinggmail,function(pendinggmailIndex,pendinggmailResult)
           {
           if(pendingIndex == pendinggmailIndex)
           {
	         DashboardDetails+="<td><a href='#' class='text-primary'>"+pendinggmailResult+"</a></td>";
             } 
              })
              
          $.each(dashboardCallDataResult.content.pendingstate,function(pendingstateIndex,pendingstateResult)
           {
           if(pendingIndex == pendingstateIndex)
           {
	         DashboardDetails+="<td>"+pendingstateResult+"</td>";
             } 
              })
              
        $.each(dashboardCallDataResult.content.pendingcity,function(pendingcityIndex,pendingcityResult)
           {
           if(pendingIndex == pendingcityIndex)
           {
	         DashboardDetails+="<td>"+pendingcityResult+"</td>";
             } 
              })
              
          $.each(dashboardCallDataResult.content.pendingrequesttime,function(pendingrequestIndex,pendingrequestResult)
           {
           if(pendingIndex == pendingrequestIndex)
           {
	         DashboardDetails+="<td>"+pendingrequestResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.pendingrequesttime,function(pendingrequestIndex,pendingrequestResult)
           {
           if(pendingIndex == pendingrequestIndex)
           {
	         DashboardDetails+="<td><span class='badge bg-warning'>Pending</span></td>"
             } 
              })
            DashboardDetails+="</tr>";
      	})
      	
      /*
      *This is used to take the Approved Request calls in Dashboard
      *
      */
      	
      	$.each(dashboardCallDataResult.content.approvedhospial,function(approvedIndex,approvedResult)
		{
		   sno=sno+1;
		     
		   DashboardDetails+="<tr>";
           DashboardDetails+="<th scope='row'><a href='#'>"+sno+"</a></th>";
           
           
           $.each(dashboardCallDataResult.content.approvedhospial,function(approvedHospitalIndex,approvedHospitalResult)
           {
           if(approvedIndex == approvedHospitalIndex)
           {
	         
	         DashboardDetails+="<td>"+approvedHospitalResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.approvedhospialID,function(approvedHospitalIDIndex,approvedHospitalIDResult)
           {
           if(approvedIndex == approvedHospitalIDIndex)
           {
	         
	         DashboardDetails+="<td>"+approvedHospitalIDResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.approvedgmail,function(approvedgmailIndex,approvedgmailResult)
           {
           if(approvedIndex == approvedgmailIndex)
           {
	         DashboardDetails+="<td><a href='#' class='text-primary'>"+approvedgmailResult+"</a></td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.approvedstate,function(approvedstateIndex,approvedstatelResult)
           {
           if(approvedIndex == approvedstateIndex)
           {
	         DashboardDetails+="<td>"+approvedstatelResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.approvedcity,function(approvedcityIndex,approvedcityResult)
           {
           if(approvedIndex == approvedcityIndex)
           {
	         DashboardDetails+="<td>"+approvedcityResult+"</td>";
             } 
              })
              
          $.each(dashboardCallDataResult.content.approvedrequesttime,function(approvedrequestIndex,approvedrequestResult)
           {
           if(approvedIndex == approvedrequestIndex)
           {
	         DashboardDetails+="<td>"+approvedrequestResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.approvedrequesttime,function(approvedrequestIndex,approvedrequestResult)
           {
           if(approvedIndex == approvedrequestIndex)
           {
	         DashboardDetails+="<td><span class='badge bg-success'>Approved</span></td>"
             } 
              })
            DashboardDetails+="</tr>";
      	})
      	
      	/*
      	*Reject List the data Starts
      	*/
      	
      	$.each(dashboardCallDataResult.content.rejectgmail,function(rejectIndex,rejectResult)
		{
			
		   sno=sno+1;
		   DashboardDetails+="<tr>";
           DashboardDetails+="<th scope='row'><a href='#'>"+sno+"</a></th>";
           
           $.each(dashboardCallDataResult.content.rejecthospial,function(rejectHospitalIndex,rejectHospitalResult)
           {
           if(rejectIndex == rejectHospitalIndex)
           {
	         
	         DashboardDetails+="<td>"+rejectHospitalResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.rejecthospialID,function(rejectHospitalIDIndex,rejectHospitalIDResult)
           {
           if(rejectIndex == rejectHospitalIDIndex)
           {
	         
	         DashboardDetails+="<td>"+rejectHospitalIDResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.rejectgmail,function(rejectgmailIndex,rejectgmailResult)
           {
           if(rejectIndex == rejectgmailIndex)
           {
	         DashboardDetails+="<td><a href='#' class='text-primary'>"+rejectgmailResult+"</a></td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.rejectstate,function(rejectstateIndex,rejectstatelResult)
           {
           if(rejectIndex == rejectstateIndex)
           {
	         DashboardDetails+="<td>"+rejectstatelResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.rejectcity,function(rejectcityIndex,rejectcityResult)
           {
           if(rejectIndex == rejectcityIndex)
           {
	         DashboardDetails+="<td>"+rejectcityResult+"</td>";
             } 
              })
              
          $.each(dashboardCallDataResult.content.rejectrequesttime,function(rejectrequestIndex,rejectrequestResult)
           {
           if(rejectIndex == rejectrequestIndex)
           {
	         DashboardDetails+="<td>"+rejectrequestResult+"</td>";
             } 
              })
              
           $.each(dashboardCallDataResult.content.rejectrequesttime,function(rejectrequestIndex,rejectrequestResult)
           {
           if(rejectIndex == rejectrequestIndex)
           {
	         DashboardDetails+="<td><span class='badge bg-danger'>Reject</span></td>"
             } 
              })
          
            DashboardDetails+="</tr>";
      	})
      	
		$("#dashboarddataappend").html(DashboardDetails);
		
		$('.mytable').DataTable();
		
		}
		 });
		    }
		    
		    
    
		    
	 /*
      *This is used to take the Beding Request calls in Bending  starts
      *
      */
	
	
	if(window.location.pathname == "/etiicos/Pending")
     {
	
	   pendingDetails();
	
	$("#pendingbtnreload").on("click",function(){
		pendingDetails();
	});
	
	    $("document").ready(function()
       {
	    setInterval(function(){
		pendingDetails();

	},1000 * 60 * 15);
	 });
	    }
	
	function pendingDetails()
	{
	$.get("innerprocess/etiicos/pendingrequestdata/",function(pedingCallDataResult){

    var pendingdetails="";
    let sno="";
    var approvalRejectbtn="";
    var pendingHospitalName="";
    
	 if (pedingCallDataResult.status == 200 && pedingCallDataResult.message == "success") {
		
		
		
		$.each(pedingCallDataResult.content.pendinggmail,function(pendingIndex,pendingResult)
		{
			sno=pendingIndex+1;
		
		   pendingdetails+="<tr >";
		   approvalRejectbtn=sno;
           pendingdetails+="<th scope='row'><a href='#'><strong class='pdapprovalReject-btn'>"+sno+"</strong></a></th>";
           
           
           $.each(pedingCallDataResult.content.pendinghospial,function(pendingHospitalIndex,pendingHospitalResult)
           {
	
	       pendingHospitalName = pendingHospitalResult;
	       
           if(pendingIndex == pendingHospitalIndex)
           {
	        pendingdetails+="<td>"+pendingHospitalResult+"</td>";
             } 
              })
              
           $.each(pedingCallDataResult.content.pendinghospialID,function(pendingHospitalIDIndex,pendingHospitalIDResult)
           {
           if(pendingIndex == pendingHospitalIDIndex)
           {
	         
	         pendingdetails+="<td>"+pendingHospitalIDResult+"</td>";
             } 
              })
              
           $.each(pedingCallDataResult.content.pendinggmail,function(pendinggmailIndex,pendinggmailResult)
           {
           if(pendingIndex == pendinggmailIndex)
           {
	         pendingdetails+="<td><a href='#' class='text-primary'><strong class='pd-name'>"+pendinggmailResult+"</strong></a></td>";
             } 
              })
              
           $.each(pedingCallDataResult.content.pendingstate,function(pendingstateIndex,pendingstatelResult)
           {
           if(pendingIndex == pendingstateIndex)
           {
	         pendingdetails+="<td>"+pendingstatelResult+"</td>";
             } 
              })
              
           $.each(pedingCallDataResult.content.pendingcity,function(pendingcityIndex,pendingcityResult)
           {
           if(pendingIndex == pendingcityIndex)
           {
	         pendingdetails+="<td>"+pendingcityResult+"</td>";
             } 
              })
              
          $.each(pedingCallDataResult.content.pendingrequesttime,function(pendingrequestIndex,pendingrequestResult)
           {
           if(pendingIndex == pendingrequestIndex)
           {
	         pendingdetails+="<td>"+pendingrequestResult+"</td>";
             } 
              })
              
           $.each(pedingCallDataResult.content.pendingrequesttime,function(pendingrequestIndex,pendingrequestResult)
           {
           if(pendingIndex == pendingrequestIndex)
           {
	         pendingdetails+='<td><div id="rejectdisplaybtn'+approvalRejectbtn+'"><span class="badge bg-warning">Pending</span></div></td>'
             } 
              })
           $.each(pedingCallDataResult.content.pendingrequesttime,function(pendingrequestIndex,pendingrequestResult)
           {
           if(pendingIndex == pendingrequestIndex)
           {
	         pendingdetails+="<td id='textdisplay'><button type='button' class='btn btn-outline-success btnSelect approvalbtn'>Approved</button>"+"  "+"<button type='button' class='btn btn-outline-danger btnSelect' data-bs-toggle='modal' data-bs-target='#staticBackdrop"+approvalRejectbtn+"'>Reject</button>"  //<div id='hidebtn'> </div> </td>
           
             pendingdetails += "<div class='modal fade' id='staticBackdrop"+approvalRejectbtn+"'  data-bs-backdrop='static' data-bs-keyboard='false' tabindex='-1' aria-labelledby='staticBackdropLabel' aria-hidden='true'>";
             pendingdetails += "<div class='modal-dialog modal-dialog-centered'><div class='modal-content'><div class='modal-header'>";
             pendingdetails += "<h5 class='modal-title' id='staticBackdropLabel'>Reject Reason for "+pendingHospitalName+"</h5>";
             pendingdetails += "<button type='button' class='btn-close' data-bs-dismiss='modal' aria-label='Close'></button></div>";
             pendingdetails += "<div class='modal-body'><div id='errormessage"+approvalRejectbtn+"'></div><textarea class='form-control' id='rejectreason"+approvalRejectbtn+"'></textarea></div><div class='modal-footer'>";
             pendingdetails += "<button type='button' class='btn btn-secondary' data-bs-dismiss='modal'>Close</button>";
             pendingdetails += "<button type='button' class='btn btn-primary rejectbtn'>Save</button></div></div></div></div></td>"
           
              } 
              })
               
            pendingdetails+="</tr>";
      	})
      	$("#pendingrequestdata").html(pendingdetails);
      	$('.mytable').DataTable();
           }
		     });
		       } 
		       /*
                *This is used to take the Beding Request calls in Bending  Ends
                *
                */
		       
		    /*
		    * Pending Approval Button Functional Starts
		    */
		 $("#pendinglistbutton").on('click','.approvalbtn',function(){
	 
	       var CurrentapprovalRow=$(this).closest("tr"); 
           var gmailapprovedCurrent=CurrentapprovalRow.find(".pd-name").html(); // get current row 2nd table cell TD value
           var gmailCurrentapprovedValue=gmailapprovedCurrent;  
          
           var hospitalapprovalCurrent=CurrentapprovalRow.find(".pdapprovalReject-btn").html(); // get current row 2nd table cell TD value
           var hospitalCurrentapprovalValue=hospitalapprovalCurrent;  
           
           var currenttableID="rejectdisplaybtn"+hospitalCurrentapprovalValue
           
         const approvalData =
         {
	            "request":true,
	            "gmail":gmailCurrentapprovedValue
            }
         
         $.post("innerprocess/etiicos/request/",approvalData,function(approvedResult)
         {
	   if (approvedResult.status == 200 && approvedResult.message == "success") {
		
		document.getElementById(currenttableID.trim()).innerHTML  = "<span class='badge bg-success'>Approved</span>";
		}
           })
             })
           /*
		    * Pending Approval Button Functional Ends
		    */
         
         
           /*
		    * Pending Reject Button Functional Starts
		    */
         $("#pendinglistbutton").on('click','.rejectbtn',function(){
	 
	       var CurrentapprovalRow=$(this).closest("tr"); 
	       var hospitalapprovalCurrent=CurrentapprovalRow.find(".pdapprovalReject-btn").html(); // get current row 2nd table cell TD value
           var hospitalCurrentapprovalValue=hospitalapprovalCurrent;  
	 
	      var rejectresonID = "#rejectreason"+hospitalCurrentapprovalValue;
	      var errormessageID = "#errormessage"+hospitalCurrentapprovalValue;
	 
	 
	     var rejectReason =  $(rejectresonID).val();
	     
	     
	     if(rejectReason != "")
	       {
           var gmailrejectCurrent=CurrentapprovalRow.find(".pd-name").html(); // get current row 2nd table cell TD value
           var gmailCurrentRejectValue=gmailrejectCurrent;  
        
           
           var currenttableID="rejectdisplaybtn"+hospitalCurrentapprovalValue
         
          var rejectResonData =
        {
			hospitalID:gmailCurrentRejectValue,
			reason:rejectReason
			
		}
         
         
         $.post("innerprocess/etiicos/rejectrequestdata/",rejectResonData,function(rejectResult)
         {

	   if (rejectResult.status == 200 && rejectResult.message == "success") {
		
		 $(errormessageID).html("Successfully Rejected");
		 $(errormessageID).css("color","green");
		
		document.getElementById(currenttableID.trim()).innerHTML  = "<span class='badge bg-danger'>Reject</span>";

		}
           })
           }else{$(errormessageID).html("Enter Reject Reason");$(errormessageID).css("color","indianred")}
             })
         
           /*
		    * Pending Reject Button Functional Ends
		    */
		    
		    
		    /*
		    * Approval Functional Starts
		    */
   if(window.location.pathname == "/etiicos/Approved")
     {
	
	approvalDetails();
	
	$("#approvalbtnreload").on("click",function(){
		approvalDetails();
	});
	    $("document").ready(function()
       {
	    setInterval(function(){
		approvalDetails();

	},1000 * 60 * 15);
	 });
	    }
	
	function approvalDetails()
	{
	$.get("innerprocess/etiicos/approvedrequestdata/",function(approvalCallDataResult){
    var approvaldetails="";
    let sno="";
    var approvalReject="";
    var approvalHospitalName="";
     
	 if (approvalCallDataResult.status == 200 && approvalCallDataResult.message == "success") {
		
		
		
		$.each(approvalCallDataResult.content.approvedgmail,function(approvalIndex,approvalResult)
		{
			sno=approvalIndex+1;
		
		   approvaldetails+="<tr>";
		   approvalReject=sno;
           approvaldetails+="<th scope='row'><a href='#'><strong class='pdhospital-approvalreject'>"+approvalReject+"</strong></a></th>";
           
           $.each(approvalCallDataResult.content.approvedhospial,function(approvalHospitalIndex,approvalHospitalResult)
           {
           if(approvalIndex == approvalHospitalIndex)
           {
	         approvalHospitalName = approvalHospitalResult;
	         approvaldetails+="<td>"+approvalHospitalResult+"</td>";
             } 
              })
              
           $.each(approvalCallDataResult.content.approvedhospialID,function(approvalHospitalIDIndex,approvalHospitalIDResult)
           {
           if(approvalIndex == approvalHospitalIDIndex)
           {
	         
	         approvaldetails+="<td><strong class='pd-approvalreject'>"+approvalHospitalIDResult+"</strong></td>";
             } 
              })
              
           $.each(approvalCallDataResult.content.approvedgmail,function(approvalgmailIndex,approvalgmailResult)
           {
           if(approvalIndex == approvalgmailIndex)
           {
	
	         approvaldetails+="<td><a href='#' class='text-primary'>"+approvalgmailResult+"</a></td>";
             } 
              })
              
           $.each(approvalCallDataResult.content.approvedstate,function(approvalstateIndex,approvalstateResult)
           {
           if(approvalIndex == approvalstateIndex)
           {
	
	         approvaldetails+="<td>"+approvalstateResult+"</td>";
             } 
              })
              
           $.each(approvalCallDataResult.content.approvedcity,function(approvalcityeIndex,approvalcityResult)
           {
           if(approvalIndex == approvalcityeIndex)
           {
	
	         approvaldetails+="<td>"+approvalcityResult+"</td>";
             } 
              })
              
          $.each(approvalCallDataResult.content.approvedrequesttime,function(approvalrequestIndex,approvalrequestResult)
           {
           if(approvalIndex == approvalrequestIndex)
           {
	         approvaldetails+="<td>"+approvalrequestResult+"</td>";
             } 
              })
              
         $.each(approvalCallDataResult.content.approvedtime,function(approvalTimeIndex,approvalTimeResult)
           {
           if(approvalIndex == approvalTimeIndex)
           {
	         approvaldetails+="<td>"+approvalTimeResult+"</td>";
             } 
              })
              
           $.each(approvalCallDataResult.content.approvedrequesttime,function(approvalrequestIndex,approvalrequestResult)
           {
           if(approvalIndex == approvalrequestIndex)
           {
	
	         approvaldetails+='<td><div id="rejectdisplaybtn'+approvalReject+'"><span class="badge bg-success">Approved</span></div></td>'
             } 
              })
          $.each(approvalCallDataResult.content.approvedrequesttime,function(approvalrequestIndex,approvalrequestResult)
           {
           if(approvalIndex == approvalrequestIndex)
           {
	         approvaldetails += "<td id='rejectapprovalappend'><button type='button' class='btn btn-outline-danger' data-bs-toggle='modal' data-bs-target='#staticBackdrop"+approvalReject+"' >Reject</button>"; //<div id='rejectapprovalbtn'></div>
          
             approvaldetails += "<div class='modal fade' id='staticBackdrop"+approvalReject+"'  data-bs-backdrop='static' data-bs-keyboard='false' tabindex='-1' aria-labelledby='staticBackdropLabel' aria-hidden='true'>";
             approvaldetails += "<div class='modal-dialog modal-dialog-centered'><div class='modal-content'><div class='modal-header'>";
             approvaldetails += "<h5 class='modal-title' id='staticBackdropLabel'>Reject Reason for "+approvalHospitalName+"</h5>";
             approvaldetails += "<button type='button' class='btn-close' data-bs-dismiss='modal' aria-label='Close'></button></div>";
             approvaldetails += "<div class='modal-body'><div id='errormessage"+approvalReject+"'></div><textarea class='form-control' id='rejectreason"+approvalReject+"'></textarea></div><div class='modal-footer'>";
             approvaldetails += "<button type='button' class='btn btn-secondary' data-bs-dismiss='modal'>Close</button>";
             approvaldetails += "<button type='button' class='btn btn-primary approvalrejectbtn'>Save</button></div></div></div></div></td>"
             
             } 
              })
               
            approvaldetails+="</tr>";
      	})
      	$("#approvalrequestdata").html(approvaldetails);
      	$('.mytable').DataTable();
           }
		     });
		       } 
		       /*
                *Approval Functional Ends
                *
                */
		       
		    /*
		    *Reject Function Start
		    */
   if(window.location.pathname == "/etiicos/Reject")
     {
	
	rejectDetails();
	
	$("#rejectbtnreload").on("click",function(){
		rejectDetails();
	});
	
	    $("document").ready(function()
       {
	    setInterval(function(){
		rejectDetails();
	
	},1000 * 60 * 15);
	 });
	    }
	
	function rejectDetails()
	{
	$.get("innerprocess/etiicos/rejectListrequestdata/",function(rejectCallDataResult){

    var rejectdetails="";
    let sno="";
 var rejectApproval="";
	 if (rejectCallDataResult.status == 200 && rejectCallDataResult.message == "success") {
		
		$.each(rejectCallDataResult.content.rejectgmail,function(rejectIndex,rejectResult)
		{
			sno=rejectIndex+1;
		
		   rejectdetails+="<tr>";
		   rejectApproval=sno
           rejectdetails+="<th scope='row'><a href='#'><strong class='pdhospital-rejectapproval'>"+sno+"</strong></a></th>";
           
           $.each(rejectCallDataResult.content.rejecthospial,function(rejectHospitalIndex,rejectHospitalResult)
           {
           if(rejectIndex == rejectHospitalIndex)
           {
	         
	         rejectdetails+="<td>"+rejectHospitalResult+"</td>";
             } 
              })
              
           $.each(rejectCallDataResult.content.rejecthospialID,function(rejectHospitalIDIndex,rejectHospitalIDResult)
           {
           if(rejectIndex == rejectHospitalIDIndex)
           {
	         
	         rejectdetails+="<td>"+rejectHospitalIDResult+"</td>";
             } 
              })
              
           $.each(rejectCallDataResult.content.rejectgmail,function(rejectgmailIndex,rejectgmailResult)
           {
           if(rejectIndex == rejectgmailIndex)
           {
	         rejectdetails+="<td><a href='#' class='text-primary'><strong class='pd-rejectapproval'>"+rejectgmailResult+"</strong></a></td>";
             } 
              })
              
           $.each(rejectCallDataResult.content.rejectstate,function(rejectstateIndex,rejectstateDResult)
           {
           if(rejectIndex == rejectstateIndex)
           {
	         
	         rejectdetails+="<td>"+rejectstateDResult+"</td>";
             } 
              })
              
           $.each(rejectCallDataResult.content.rejectcity,function(rejectcityIndex,rejectcityResult)
           {
           if(rejectIndex == rejectcityIndex)
           {
	         
	         rejectdetails+="<td>"+rejectcityResult+"</td>";
             } 
              })
              
          $.each(rejectCallDataResult.content.rejectrequesttime,function(rejectrequestIndex,rejectrequestResult)
           {
           if(rejectIndex == rejectrequestIndex)
           {
	         rejectdetails+="<td>"+rejectrequestResult+"</td>";
             } 
              })
              
           $.each(rejectCallDataResult.content.rejecttime,function(rejecttimeIndex,rejectimetResult)
           {
           if(rejectIndex == rejecttimeIndex)
           {
	         rejectdetails+="<td>"+rejectimetResult+"</td>";
             } 
              })
              
           $.each(rejectCallDataResult.content.rejectrequesttime,function(rejectrequestIndex,rejectrequestResult)
           {
           if(rejectIndex == rejectrequestIndex)
           {
	         rejectdetails+='<td><div <div id="approvaldisplaybtn'+rejectApproval+'"><span class="badge bg-danger">Reject</span></div></td>'
             } 
              })
              
           $.each(rejectCallDataResult.content.rejectreason,function(rejectreasonIndex,rejecresonResult)
           {
           if(rejectIndex == rejectreasonIndex)
           {
	         rejectdetails+="<td>"+rejecresonResult+"</td>";
             } 
              })
              
          $.each(rejectCallDataResult.content.rejectrequesttime,function(rejectrequestIndex,rejectrequestResult)
           {
           if(rejectIndex == rejectrequestIndex)
           {
	    	rejectdetails+="<td id='approvalrejectappend'><div id='approvalrejectbtn'><button type='button' class='btn btn-outline-success rejectapprovaldatabtn'>Approval</button></div></td>"
               } 
              })
          
            rejectdetails+="</tr>";
      	})
      	$("#rejectdataappend").html(rejectdetails);
      	$('.mytable').DataTable();
           }
		     });
		       } 
		   
		    /*
		    *Reject Function Ends
		    */
		    
/*
*approvalReject Funtion starts
*/

         $("#approvallistbutton").on('click','.approvalrejectbtn',function(){
	 
	 var CurrentapprovalRow=$(this).closest("tr"); 
	 var hospitalrejectCurrent=CurrentapprovalRow.find(".pdhospital-approvalreject").html(); // get current row 2nd table cell TD value
     var hospitalCurrentRejectValue=hospitalrejectCurrent;  
	 
	 var rejectresonID = "#rejectreason"+hospitalCurrentRejectValue;
	 var errormessageID = "#errormessage"+hospitalCurrentRejectValue;
	 
	 var rejectReason =  $(rejectresonID).val();
	 
	 
	 if(rejectReason != "")
	 {
	         var hospitalIDrejectCurrent=CurrentapprovalRow.find(".pd-approvalreject").html(); // get current row 2nd table cell TD value
           var hospitalIDCurrentRejectValue=hospitalIDrejectCurrent;  
           
           
        
        var rejectResonData =
        {
			hospitalID:hospitalIDCurrentRejectValue,
			reason:rejectReason
			
		}
         
         $.post("innerprocess/etiicos/approvalrejectrequestdata/",rejectResonData,function(rejectResult)
         {
	
	 var currenttableID="rejectdisplaybtn"+hospitalCurrentRejectValue
	   if (rejectResult.status == 200 && rejectResult.message == "success") {
		   
		   $(errormessageID).html("Successfully Rejected");
		   $(errormessageID).css("color","green");
		   
		document.getElementById(currenttableID).innerHTML  = "<span class='badge bg-danger'>Reject</span>";
		
		}
           })
           }else{$(errormessageID).html("Enter Reject Reason");$(errormessageID).css("color","indianred")}
             })

	/*
*approvalReject Funtion Ends
*/	    
		    
		    
/*
*RejectApproval Funtion starts
*/

         $("#rejectlistbutton").on('click','.rejectapprovaldatabtn',function(){
	 
	       var CurrentapprovalRow=$(this).closest("tr"); 
           var gmailapprovalCurrent=CurrentapprovalRow.find(".pd-rejectapproval").html(); // get current row 2nd table cell TD value
           var gmailCurrentApprovalValue=gmailapprovalCurrent;  
        
          var hospitalrejectCurrent=CurrentapprovalRow.find(".pdhospital-rejectapproval").html(); // get current row 2nd table cell TD value
           var hospitalCurrentRejectValue=hospitalrejectCurrent;  
        
        var currenttableID="approvaldisplaybtn"+hospitalCurrentRejectValue
         
         $.post("innerprocess/etiicos/rejectapprovalrequestdata/",{"gmail":gmailCurrentApprovalValue},function(approvalResult)
         {
	   if (approvalResult.status == 200 && approvalResult.message == "success") {
		document.getElementById(currenttableID).innerHTML  = "<span class='badge bg-success'>Approved</span>";
	
		}
           })
             })

	/*
*RejectApproval Funtion Ends
*/	    
		    
		    