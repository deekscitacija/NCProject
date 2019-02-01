package com.ftn.nc.NCBackend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.repository.RecenzentRepository;
import com.ftn.nc.NCBackend.web.service.RecenzentService;

@Service
public class RecenzentServiceImpl implements RecenzentService{
	
	@Autowired
	private RecenzentRepository recenzentRepository;

	@Override
	public List<Recenzent> findAllByCasopis(Casopis casopis) {
		
		return recenzentRepository.findByCasopisiContaining(casopis);
	}

}
