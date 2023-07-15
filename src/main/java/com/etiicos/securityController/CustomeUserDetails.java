package com.etiicos.securityController;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.etiicos.entity.EtiicosLogin;


public class CustomeUserDetails implements UserDetails {

	@Autowired
	private EtiicosLogin registeredData;
	
	public CustomeUserDetails(EtiicosLogin registeredData)
	{
		super();
		this.registeredData=registeredData;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return registeredData.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return registeredData.getGmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
