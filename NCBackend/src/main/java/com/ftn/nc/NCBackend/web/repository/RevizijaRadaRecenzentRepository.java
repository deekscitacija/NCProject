package com.ftn.nc.NCBackend.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;

public interface RevizijaRadaRecenzentRepository extends JpaRepository<RevizijaRadaRecenzent, Long> {

	public RevizijaRadaRecenzent findByRevizijaAndRecenzent(RevizijaRada revizija, Recenzent recenzent);
	
}
