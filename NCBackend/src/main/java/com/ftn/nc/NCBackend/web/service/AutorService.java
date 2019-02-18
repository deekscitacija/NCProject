package com.ftn.nc.NCBackend.web.service;

import com.ftn.nc.NCBackend.web.model.Autor;

public interface AutorService {

	public Autor getById(Long id);
	
	public Autor save(Autor autor);
	
}
