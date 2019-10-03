package com.krishantha.rentcloud.auth.authserver.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
public class AuthServerConfigurations extends WebSecurityConfigurerAdapter implements AuthorizationServerConfigurer{
	
	@Bean
	//new spring boot version does not provide the AuthenticationManager Bean thats why we ned create bean for that
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Autowired 
	AuthenticationManager authenticationManager;

	//use password encoder 
	PasswordEncoder passwordEncoder=PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		security.checkTokenAccess("permitAll()");
		
	}

	@Override
	//this identify client details(web site details)
	public void configure(ClientDetailsServiceConfigurer client) throws Exception {
		//used the inmemory data
		client.inMemory().withClient("web").secret(passwordEncoder.encode("web123")).scopes("READ","WRITE").authorizedGrantTypes("password","authorization_code");
		
	}

	@Override
	//we need to tell authorization server end point configurer
	public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception {
		//we said authorization server to end point of authentication manager 
		endpoint.authenticationManager(authenticationManager);
		
	}

}
