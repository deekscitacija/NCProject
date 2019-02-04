package com.ftn.nc.NCBackend.web.service;

import com.ftn.nc.NCBackend.web.model.NaucniRad;

public interface NaucniRadService {

	public NaucniRad getById(Long id);
	
	public NaucniRad save(NaucniRad rad);
	
}
