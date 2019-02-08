package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import com.ftn.nc.NCBackend.web.enums.TransakcijaStatus;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.Transakcija;

public interface TransakcijaService {

	public Transakcija save(Transakcija transakcija);
	
	public List<Transakcija> getAllForKorisnik(Korisnik korisnik);
	
	public List<Transakcija> getAllForKorisnikAndCasopisNotNull(Korisnik korisnik);
	
	public List<Transakcija> getAllForKorisnikAndIzdanjeNotNull(Korisnik korisnik);
	
	public List<Transakcija> getAllForKorisnikAndRadNotNull(Korisnik korisnik);
	
	public List<Transakcija> ifExsistsKorisnikAndCasopis(Korisnik korisnik, Casopis casopis);
	
	public List<Transakcija> ifExsistsKorisnikAndIzdanje(Korisnik korisnik, Izdanje izdanje);
	
	public List<Transakcija> ifExsistsKorisnikAndRad(Korisnik korisnik, NaucniRad naucniRad);
	
	public Transakcija findById(Long id);
	
	public Transakcija saveNewStatus(Transakcija transakcija, TransakcijaStatus noviStatus);
	
}
