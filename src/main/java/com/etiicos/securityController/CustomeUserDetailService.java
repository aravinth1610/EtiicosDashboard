package com.etiicos.securityController;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.etiicos.entity.EtiicosLogin;
import com.etiicos.repository.EtiicosLoginRepository;

@Component
public class CustomeUserDetailService implements UserDetailsService {

	@Autowired
	private EtiicosLoginRepository opdRegistered;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		EtiicosLogin register =opdRegistered.findByGmail(username);
	
		if(register!=null)
		{
			return new CustomeUserDetails(register);
		}
		else
		{
			throw new UsernameNotFoundException("Not found User");
		}
		
	}
	
	
}
