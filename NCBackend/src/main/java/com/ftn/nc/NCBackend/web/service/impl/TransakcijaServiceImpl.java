package com.ftn.nc.NCBackend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.Transakcija;
import com.ftn.nc.NCBackend.web.repository.TransakcijaRepository;
import com.ftn.nc.NCBackend.web.service.TransakcijaService;

@Service
public class TransakcijaServiceImpl implements TransakcijaService {
	
	@Autowired
	private TransakcijaRepository transakcijaRepository;

	@Override
	public Transakcija save(Transakcija transakcija) {
		
		return transakcijaRepository.save(transakcija);
	}

	@Override
	public List<Transakcija> getAllForKorisnik(Korisnik korisnik) {
		
		return transakcijaRepository.findByVrsiPlacanje(korisnik);	
	}

	@Override
	public List<Transakcija> getAllForKorisnikAndCasopisNotNull(Korisnik korisnik) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndCasopisNotNull(korisnik);
	}

	@Override
	public List<Transakcija> getAllForKorisnikAndIzdanjeNotNull(Korisnik korisnik) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndIzdanjeNotNull(korisnik);
	}

	@Override
	public List<Transakcija> getAllForKorisnikAndRadNotNull(Korisnik korisnik) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndNaucniRadNotNull(korisnik);
	}

	@Override
	public List<Transakcija> getAllForKorisnikAndCasopis(Korisnik korisnik, Casopis casopis) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndCasopis(korisnik, casopis);
	}

	@Override
	public List<Transakcija> getAllForKorisnikAndIzdanje(Korisnik korisnik, Izdanje izdanje) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndIzdanje(korisnik, izdanje);
	}

	@Override
	public List<Transakcija> getAllForKorisnikAndRad(Korisnik korisnik, NaucniRad naucniRad) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndNaucniRad(korisnik, naucniRad);
	}

}
