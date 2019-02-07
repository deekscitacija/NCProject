package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.Transakcija;

public interface TransakcijaService {

	public Transakcija save(Transakcija transakcija);
	
	public List<Transakcija> getAllForKorisnik(Korisnik korisnik);
	
}
