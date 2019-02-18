package com.ftn.nc.NCBackend.web.service;

import javax.servlet.ServletRequest;

import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.model.Korisnik;

public interface KorisnikService {

	public Korisnik getById(Long id);
	
	public Korisnik getByEmail(String email);
	
	public Korisnik getUserFromToken(ServletRequest request, TokenUtils tokenUtils);
	
	public Korisnik save(Korisnik korisnik);
	
}
