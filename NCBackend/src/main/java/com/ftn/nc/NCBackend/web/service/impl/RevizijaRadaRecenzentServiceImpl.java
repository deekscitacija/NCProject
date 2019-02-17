package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRecenzentRepository;
import com.ftn.nc.NCBackend.web.service.RevizijaRadaRecenzentService;

@Service
public class RevizijaRadaRecenzentServiceImpl implements RevizijaRadaRecenzentService {
	
	@Autowired
	private RevizijaRadaRecenzentRepository revizijaRadaRecenzentReposiotry;

	@Override
	public RevizijaRadaRecenzent save(RevizijaRadaRecenzent revizijaRadaRecenzent) {
		
		return revizijaRadaRecenzentReposiotry.save(revizijaRadaRecenzent);
	}

	@Override
	public RevizijaRadaRecenzent findById(Long id) {
		
		return revizijaRadaRecenzentReposiotry.getOne(id);
	}

	@Override
	public RevizijaRadaRecenzent findByRevizijaAndRecenzent(RevizijaRada revizija, Recenzent recenzent) {
		
		return revizijaRadaRecenzentReposiotry.findByRevizijaAndRecenzent(revizija, recenzent);
	}

}
