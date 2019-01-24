package com.ftn.nc.NCBackend.web.service;

import org.springframework.data.domain.Page;

import com.ftn.nc.NCBackend.web.model.Casopis;

public interface CasopisService {

	public Page<Casopis> getAll(int offset, int num);
	
	public Casopis getById(Long id);
	
}
