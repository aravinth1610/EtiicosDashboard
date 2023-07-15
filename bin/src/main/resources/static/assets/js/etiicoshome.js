  
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
	         DashboardDetails+="<td><a href='#' class='text-primary'>"+approvedgmailResult+"</td>";
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
	 if (pedingCallDataResult.status == 200 && pedingCallDataResult.message == "success") {
		
		
		
		$.each(pedingCallDataResult.content.pendinggmail,function(pendingIndex,pendingResult)
		{
			sno=pendingIndex+1;
		
		   pendingdetails+="<tr >";
           pendingdetails+="<th scope='row'><a href='#'>"+sno+"</a></th>";
           
           
           $.each(pedingCallDataResult.content.pendinghospial,function(pendingHospitalIndex,pendingHospitalResult)
           {
           if(pendingIndex == pendingHospitalIndex)
           {
	         approvalRejectbtn=pendingHospitalResult
	         pendingdetails+="<td><strong class='pdapprovalReject-btn'>"+pendingHospitalResult+"</strong></td>";
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
	         pendingdetails+="<td id='textdisplay'><div id='hidebtn'><button type='button' class='btn btn-outline-success btnSelect approvalbtn'>Approved</button>"+"  "+"<button type='button' class='btn btn-outline-danger btnSelect rejectbtn'>Reject</button></div></td>"
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
		
		document.getElementById(currenttableID).innerHTML  = "<span class='badge bg-success'>Approved</span>";
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
           var gmailrejectCurrent=CurrentapprovalRow.find(".pd-name").html(); // get current row 2nd table cell TD value
           var gmailCurrentRejectValue=gmailrejectCurrent;  
        
         
         var hospitalapprovalCurrent=CurrentapprovalRow.find(".pdapprovalReject-btn").html(); // get current row 2nd table cell TD value
           var hospitalCurrentapprovalValue=hospitalapprovalCurrent;  
           
           var currenttableID="rejectdisplaybtn"+hospitalCurrentapprovalValue
         
         $.post("innerprocess/etiicos/rejectrequestdata/",{"gmail":gmailCurrentRejectValue},function(rejectResult)
         {

	   if (rejectResult.status == 200 && rejectResult.message == "success") {
		document.getElementById(currenttableID).innerHTML  = "<span class='badge bg-danger'>Reject</span>";

		}
           })
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
	 if (approvalCallDataResult.status == 200 && approvalCallDataResult.message == "success") {
		
		
		
		$.each(approvalCallDataResult.content.approvedgmail,function(approvalIndex,approvalResult)
		{
			sno=approvalIndex+1;
		
		   approvaldetails+="<tr>";
           approvaldetails+="<th scope='row'><a href='#'>"+sno+"</a></th>";
           
           $.each(approvalCallDataResult.content.approvedhospial,function(approvalHospitalIndex,approvalHospitalResult)
           {
           if(approvalIndex == approvalHospitalIndex)
           {
	         approvalReject=approvalHospitalResult;
	         approvaldetails+="<td><strong class='pdhospital-approvalreject'>"+approvalHospitalResult+"</strong></td>";
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
              
          $.each(approvalCallDataResult.content.approvedrequesttime,function(approvalrequestIndex,approvalrequestResult)
           {
           if(approvalIndex == approvalrequestIndex)
           {
	         approvaldetails+="<td>"+approvalrequestResult+"</td>";
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
	         approvaldetails+="<td id='rejectapprovalappend'><div id='rejectapprovalbtn'><button type='button' class='btn btn-outline-danger approvalrejectbtn'>Reject</button></div></td>"
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
           rejectdetails+="<th scope='row'><a href='#'>"+sno+"</a></th>";
           
           $.each(rejectCallDataResult.content.rejecthospial,function(rejectHospitalIndex,rejectHospitalResult)
           {
           if(rejectIndex == rejectHospitalIndex)
           {
	         rejectApproval=rejectHospitalResult
	         rejectdetails+="<td><strong class='pdhospital-rejectapproval'>"+rejectHospitalResult+"</strong></td>";
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
              
          $.each(rejectCallDataResult.content.rejectrequesttime,function(rejectrequestIndex,rejectrequestResult)
           {
           if(rejectIndex == rejectrequestIndex)
           {
	         rejectdetails+="<td>"+rejectrequestResult+"</td>";
             } 
              })
              
           $.each(rejectCallDataResult.content.rejectrequesttime,function(rejectrequestIndex,rejectrequestResult)
           {
           if(rejectIndex == rejectrequestIndex)
           {
	         rejectdetails+='<td><div <div id="approvaldisplaybtn'+rejectApproval+'"><span class="badge bg-danger">Reject</span></div></td>'
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
           var hospitalIDrejectCurrent=CurrentapprovalRow.find(".pd-approvalreject").html(); // get current row 2nd table cell TD value
           var hospitalIDCurrentRejectValue=hospitalIDrejectCurrent;  
           
            var hospitalrejectCurrent=CurrentapprovalRow.find(".pdhospital-approvalreject").html(); // get current row 2nd table cell TD value
           var hospitalCurrentRejectValue=hospitalrejectCurrent;  
        
         
         $.post("innerprocess/etiicos/approvalrejectrequestdata/",{"hospitalID":hospitalIDCurrentRejectValue},function(rejectResult)
         {
	
	 var currenttableID="rejectdisplaybtn"+hospitalCurrentRejectValue
	   if (rejectResult.status == 200 && rejectResult.message == "success") {
		document.getElementById(currenttableID).innerHTML  = "<span class='badge bg-danger'>Reject</span>";
		

		}
           })
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
		    
		    