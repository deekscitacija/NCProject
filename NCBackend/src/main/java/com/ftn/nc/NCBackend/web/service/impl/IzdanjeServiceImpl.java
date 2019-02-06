package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.repository.IzdanjeRepository;
import com.ftn.nc.NCBackend.web.service.IzdanjeService;

@Service
public class IzdanjeServiceImpl implements IzdanjeService{
	
	@Autowired
	private IzdanjeRepository izdanjeRepository;

	@Override
	public Page<Izdanje> findByCasopis(Casopis casopis, Pageable pageable) {
		
		return izdanjeRepository.findByCasopis(casopis, pageable);
	}

	@Override
	public Izdanje findById(Long id) {
		
		return izdanjeRepository.getOne(id);
	}
	
}
