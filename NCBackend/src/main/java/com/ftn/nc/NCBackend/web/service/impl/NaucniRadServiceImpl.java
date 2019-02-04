package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.repository.NaucniRadRepository;
import com.ftn.nc.NCBackend.web.service.NaucniRadService;

@Service
public class NaucniRadServiceImpl implements NaucniRadService {

	@Autowired
	private NaucniRadRepository naucniRadRepository;
	
	@Override
	public NaucniRad getById(Long id) {
		
		return naucniRadRepository.getOne(id);
	}

	@Override
	public NaucniRad save(NaucniRad rad) {
		
		return naucniRadRepository.save(rad);
	}

}
