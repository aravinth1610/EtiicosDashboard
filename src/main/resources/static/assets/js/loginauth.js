
$("#otptagsubmit").on('click',function(){
	
     var authGmail = $("#yourUsername").val();
	 $("#yourUsername").prop("disabled",true)
	
	$("#successerrormessage").html("Please wait to send OTP");
	$("#successerrormessage").css("color","mediumseagreen");
	
	$.post("otpauth",{gmail:authGmail},function(otpTagSubmitResult){
		
	$("#successerrormessage").html("Please check in Inbox or Spam");
	$("#successerrormessage").css("color","mediumseagreen");
	
	if(otpTagSubmitResult.status == 200 && otpTagSubmitResult.message == 'success')
	{
		$("#successerrormessage").html("");
		$("#otpsubmit").css("display","none")
		$("#yourUsername").prop("disabled",true);
		$("#otphtmltag").html(otpTagSubmitResult.content);
		
	}
	else if(otpTagSubmitResult.status == 101 && otpTagSubmitResult.message == 'InvalidMail-non-success')
	{
		$("#successerrormessage").html("Invalid Email Address");
	    $("#successerrormessage").css("color","indianred");
	    $("#yourUsername").prop("disabled",false);
		
	}
	else
	{
		$("#successerrormessage").html("Please Check your Internet Connection");
	    $("#successerrormessage").css("color","indianred");
	    $("#yourUsername").prop("disabled",false);
	}
	
	})
	
})


function loginview()
{
	
	var AuthOTP = $("#yourotp").val();
	
	
	$.post("loginauth",{otp:AuthOTP},function(loginOTPResult){
		
	
	if(loginOTPResult.status == 200 && loginOTPResult.message== 'success')
		{
		window.location.replace(loginOTPResult.content);
		}
		else if(loginOTPResult.status == 202 && loginOTPResult.message== 'otpnotvalid-non-success')
		{
			$("#successerrormessage").html("Not Valid OTP");
	        $("#successerrormessage").css("color","indianred");
		}
		else
		{
		$("#successerrormessage").html("Please Check your Internet Connection");
	    $("#successerrormessage").css("color","indianred");
		}
		
	})
	
}
