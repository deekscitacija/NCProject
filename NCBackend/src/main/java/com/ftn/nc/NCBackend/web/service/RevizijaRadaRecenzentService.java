package com.ftn.nc.NCBackend.web.service;

import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;

public interface RevizijaRadaRecenzentService {
	
	public RevizijaRadaRecenzent save(RevizijaRadaRecenzent revizijaRadaRecenzent);
	
	public RevizijaRadaRecenzent findById(Long id);
	
	public RevizijaRadaRecenzent findByRevizijaAndRecenzent(RevizijaRada revizija, Recenzent recenzent);

}
