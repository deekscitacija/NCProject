package com.ftn.nc.NCBackend.web.service;

import com.ftn.nc.NCBackend.web.model.Korisnik;

public interface KorisnikService {

	public Korisnik getById(Long id);
	
	public Korisnik getByEmail(String email);
	
}
