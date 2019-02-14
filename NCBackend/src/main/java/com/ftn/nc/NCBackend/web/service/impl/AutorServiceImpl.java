package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Autor;
import com.ftn.nc.NCBackend.web.repository.AutorRepository;
import com.ftn.nc.NCBackend.web.service.AutorService;

@Service
public class AutorServiceImpl implements AutorService {
	
	@Autowired
	private AutorRepository autorRepository;

	@Override
	public Autor getById(Long id) {
		
		return autorRepository.getOne(id);
	}

}
