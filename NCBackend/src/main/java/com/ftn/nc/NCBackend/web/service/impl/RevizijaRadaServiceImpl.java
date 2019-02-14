package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;
import com.ftn.nc.NCBackend.web.service.RevizijaService;

@Service
public class RevizijaRadaServiceImpl implements RevizijaService {
	
	@Autowired
	private RevizijaRadaRepository revizijaRadaRepository;

	@Override
	public RevizijaRada save(RevizijaRada revizijaRada) {
		
		return revizijaRadaRepository.save(revizijaRada);
	}

	@Override
	public RevizijaRada getById(Long id) {
		
		return revizijaRadaRepository.findById(id).get();
	}

}
