package com.ftn.nc.NCBackend.web.service.impl;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;
import com.ftn.nc.NCBackend.web.service.KorisnikService;

@Service
public class KorisnikServiceImpl implements KorisnikService{
	
	@Autowired
	private KorisnikRepository korisnikRepository;

	@Override
	public Korisnik getById(Long id) {
		
		return korisnikRepository.getOne(id);
	}

	@Override
	public Korisnik getByEmail(String email) {
		
		return korisnikRepository.findByEmail(email);
	}
	
	@Override
	public Korisnik getUserFromToken(ServletRequest request, TokenUtils tokenUtils) {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");
		
		if(token == null) {
			System.out.println("TOKEN JE NULL!");
			return null;
		}
		
		String email = tokenUtils.getUsernameFromToken(token);

		return korisnikRepository.findByEmail(email);
	}

	@Override
	public Korisnik save(Korisnik korisnik) {
		
		return korisnikRepository.save(korisnik);
	}

}
