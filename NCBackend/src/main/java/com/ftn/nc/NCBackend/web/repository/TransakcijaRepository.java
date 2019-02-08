package com.ftn.nc.NCBackend.web.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.enums.TransakcijaStatus;
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
	
	List<Transakcija> findByVrsiPlacanjeAndCasopisAndStatusIn(Korisnik vrsiPlacanje, Casopis casopis, Collection<TransakcijaStatus> statusi);
	
	List<Transakcija> findByVrsiPlacanjeAndIzdanjeAndStatusIn(Korisnik vrsiPlacanje, Izdanje izdanje, Collection<TransakcijaStatus> statusi);
	
	List<Transakcija> findByVrsiPlacanjeAndNaucniRadAndStatusIn(Korisnik vrsiPlacanje, NaucniRad naucniRad, Collection<TransakcijaStatus> statusi);
	
}
