package com.ftn.nc.NCBackend.service;

import org.springframework.data.domain.Page;

import com.ftn.nc.NCBackend.model.Casopis;

public interface CasopisService {

	public Page<Casopis> getAll(int offset, int num);
	
}
