package com.ftn.nc.NCBackend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.repository.NaucnaOblastRepository;
import com.ftn.nc.NCBackend.web.service.NaucneOblastiService;

@Service
public class NaucneOblastiServiceImpl implements NaucneOblastiService{

	@Autowired
	private NaucnaOblastRepository naucnaOblastRepository;
	
	@Override
	public List<NaucnaOblast> getAll() {
		
		return naucnaOblastRepository.findAll();
	}

}
