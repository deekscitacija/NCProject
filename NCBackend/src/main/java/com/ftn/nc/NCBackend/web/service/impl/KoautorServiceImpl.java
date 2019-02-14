package com.ftn.nc.NCBackend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Koautor;
import com.ftn.nc.NCBackend.web.repository.KoautorRepository;
import com.ftn.nc.NCBackend.web.service.KoautorService;

@Service
public class KoautorServiceImpl implements KoautorService {
	
	@Autowired
	private KoautorRepository koautorReposiotry;

	@Override
	public List<Koautor> findByEmail(String email) {
		
		return koautorReposiotry.findByEmail(email);
	}

	@Override
	public Koautor findById(Long id) {
		
		return koautorReposiotry.findById(id).get();
	}

	@Override
	public Koautor save(Koautor koautor) {
		
		return koautorReposiotry.save(koautor);
	}

}
