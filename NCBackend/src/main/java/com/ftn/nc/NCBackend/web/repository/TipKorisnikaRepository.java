package com.ftn.nc.NCBackend.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.TipKorisnika;

public interface TipKorisnikaRepository extends JpaRepository<TipKorisnika, Long>{

	public TipKorisnika getByKod(String kod);
	
}
