package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
