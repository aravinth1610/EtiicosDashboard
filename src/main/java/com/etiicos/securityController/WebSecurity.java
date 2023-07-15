package com.etiicos.securityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity{

	 
	  
	  @Autowired
	  UserDetailsService service;

		@Bean
	    protected BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    protected DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(service);
	        authProvider.setPasswordEncoder(passwordEncoder());
	         
	        return authProvider;
	    }
	   
	    @Bean
	    protected SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
	    	
	    	 http
	    	.cors().and().csrf().disable()
	    	.authorizeRequests()
	    	.antMatchers("/resources/**").permitAll()
	    	.antMatchers("/etiicos/innerprocess/**").permitAll()
	    	.antMatchers("/etiicosHome").authenticated()
	    	.antMatchers("/Dashboard").authenticated()
	    	.antMatchers("/Pending").authenticated()
	    	.antMatchers("/Approved").authenticated()
	    	.antMatchers("/Reject").authenticated()
	    	.antMatchers("/Token-Dashboard").authenticated()
	    	.antMatchers("/Token-Pending").authenticated()
	    	.antMatchers("/Token-Approved").authenticated()
	    	.antMatchers("/Token-Reject").authenticated()
	    	.antMatchers("/departments").authenticated()
	    	.anyRequest().permitAll()
	        .and()
	        .formLogin().loginPage("/login").usernameParameter("gmail").passwordParameter("password")
	        .defaultSuccessUrl("/etiicosHome")
	        .permitAll()
	    	.and()
	        .logout().logoutSuccessUrl("/login").permitAll()
	        .invalidateHttpSession(true)
	         ;
	       return http.build();
	    }
	
}
