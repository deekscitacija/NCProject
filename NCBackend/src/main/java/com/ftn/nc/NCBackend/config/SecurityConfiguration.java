package com.ftn.nc.NCBackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ftn.nc.NCBackend.security.AuthenticationTokenFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	
	@Autowired
	public SecurityConfiguration(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService);
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception 
    {
    	System.out.println("USLOEEE");
    	
    	httpSecurity
	        .csrf()
	        .disable()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeRequests()
	        .antMatchers("/rest/*")
	        .permitAll();
	        /*
	        .antMatchers("/secured/*").authenticated()
	        .antMatchers("/rest/secured/saveCert/*").access("hasAnyAuthority('KREIRAJ_SERT')")
	        .antMatchers("/rest/secured/issuers/*").access("hasAuthority('CITAJ_KEYSTORE')")
	        .antMatchers("/rest/secured/getCerts/*").access("hasAuthority('CITAJ_KEYSTORE')")
	        .antMatchers("/rest/secured/withdrawCert/*").access("hasAuthority('POVUCI')")
	        .antMatchers("/rest/secured/keystore/newKeystore").access("hasAuthority('KREIRAJ_KEYSTORE')")
	        .antMatchers("/rest/secured/keystore/getKeystores").access("hasAuthority('CITAJ_KEYSTORE')")
    		.antMatchers("/rest/secured/getCsrs").access("hasAuthority('UPRAVLJAJ_CSR')")
    		.antMatchers("/rest/secured/denyCsr").access("hasAuthority('UPRAVLJAJ_CSR')")
    		.antMatchers("/rest/secured/acceptCsr").access("hasAuthority('UPRAVLJAJ_CSR')");
    		*/
        
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

	}

}


