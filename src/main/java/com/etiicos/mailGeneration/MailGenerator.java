package com.etiicos.mailGeneration;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.etiicos.properties.PropertiesClassPath;


@Service	
public class MailGenerator {
	 
	@Autowired
	private JavaMailSender sendingMailProcessor;
	
	@Autowired
	private PropertiesClassPath propertiesFilePath;
	
	public static Integer randomOTP;
	
	public boolean adminLoginAuth(String gmailID)
	{
		 randomOTP =((int)(Math.random()*9000) + 1000);
		 
	     SimpleMailMessage messager=new SimpleMailMessage();
	try
    {
 	messager.setFrom(propertiesFilePath.getGmail());
 	messager.setCc(propertiesFilePath.getMailCc1());
 	messager.setCc(propertiesFilePath.getMailCc2());
	messager.setTo(gmailID);
	messager.setSubject("Admin Login");
	messager.setText("Etiicos Admin,This is your One Time Password "+randomOTP);
	this.sendingMailProcessor.send(messager);
	
	return true;
    }catch (Exception e) {
  System.out.println("----Admin Mail Error ----");
    	return false;
	}
	}
	
	
}


