package com.ftn.nc.NCBackend.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.Transakcija;

public interface TransakcijaRepository extends JpaRepository<Transakcija, Long>{

	List<Transakcija> findByVrsiPlacanje(Korisnik vrsiPlacanje);
	
}
