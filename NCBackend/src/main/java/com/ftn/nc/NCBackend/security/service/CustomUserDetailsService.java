package com.ftn.nc.NCBackend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.security.CustomUserDetailsFactory;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikRepository.findByEmail(email);
		
		if(korisnik==null) {
			throw new UsernameNotFoundException("Korisnik ne postoji");
		}else {
			return CustomUserDetailsFactory.createCustomUserDetails(korisnik);
		}
				
	}

}
