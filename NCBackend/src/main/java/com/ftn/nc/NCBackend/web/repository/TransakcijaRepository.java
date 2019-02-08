package com.ftn.nc.NCBackend.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.Transakcija;

public interface TransakcijaRepository extends JpaRepository<Transakcija, Long>{

	List<Transakcija> findByVrsiPlacanje(Korisnik vrsiPlacanje);
	
	List<Transakcija> findByVrsiPlacanjeAndCasopisNotNull(Korisnik vrsiPlacanje);
	
	List<Transakcija> findByVrsiPlacanjeAndIzdanjeNotNull(Korisnik vrsiPlacanje);
	
	List<Transakcija> findByVrsiPlacanjeAndNaucniRadNotNull(Korisnik vrsiPlacanje);
	
	List<Transakcija> findByVrsiPlacanjeAndCasopis(Korisnik vrsiPlacanje, Casopis casopis);
	
	List<Transakcija> findByVrsiPlacanjeAndIzdanje(Korisnik vrsiPlacanje, Izdanje izdanje);
	
	List<Transakcija> findByVrsiPlacanjeAndNaucniRad(Korisnik vrsiPlacanje, NaucniRad naucniRad);
	
}
