package com.ftn.nc.NCBackend.web.service;

import com.ftn.nc.NCBackend.web.model.RevizijaRada;

public interface RevizijaService {

	public RevizijaRada save(RevizijaRada revizijaRada);
	
	public RevizijaRada getById(Long id);
	
}
