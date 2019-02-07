package com.ftn.nc.NCBackend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Korisnik;
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

}
