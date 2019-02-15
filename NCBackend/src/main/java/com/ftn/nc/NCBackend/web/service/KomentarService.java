package com.ftn.nc.NCBackend.web.service;

import com.ftn.nc.NCBackend.web.model.Komentar;

public interface KomentarService {
	
	public Komentar getById(Long id);
	
	public Komentar save(Komentar komentar);
}
