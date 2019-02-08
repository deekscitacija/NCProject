package com.ftn.nc.NCBackend.web.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.nc.NCBackend.web.enums.TransakcijaStatus;
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
	public List<Transakcija> ifExsistsKorisnikAndCasopis(Korisnik korisnik, Casopis casopis) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndCasopisAndStatusIn(korisnik, casopis, buildStatusi());
	}

	@Override
	public List<Transakcija> ifExsistsKorisnikAndIzdanje(Korisnik korisnik, Izdanje izdanje) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndIzdanjeAndStatusIn(korisnik, izdanje, buildStatusi());
	}

	@Override
	public List<Transakcija> ifExsistsKorisnikAndRad(Korisnik korisnik, NaucniRad naucniRad) {
		
		return transakcijaRepository.findByVrsiPlacanjeAndNaucniRadAndStatusIn(korisnik, naucniRad, buildStatusi());
	}

	@Override
	public Transakcija findById(Long id) {
		
		return transakcijaRepository.getOne(id);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Transakcija saveNewStatus(Transakcija transakcija, TransakcijaStatus noviStatus) {
		transakcija.setStatus(noviStatus);
		return transakcijaRepository.save(transakcija);
	}
	
	private Collection<TransakcijaStatus> buildStatusi(){
		Collection<TransakcijaStatus> statusi = new ArrayList<>();
		statusi.add(TransakcijaStatus.U);
		statusi.add(TransakcijaStatus.C);
		return statusi;
	}

}
