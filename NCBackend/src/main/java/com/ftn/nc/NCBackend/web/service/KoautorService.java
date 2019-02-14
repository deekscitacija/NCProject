package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import com.ftn.nc.NCBackend.web.model.Koautor;

public interface KoautorService {

	public List<Koautor> findByEmail(String email);
	
	public Koautor findById(Long id);
	
	public Koautor save(Koautor koautor);
	
}
