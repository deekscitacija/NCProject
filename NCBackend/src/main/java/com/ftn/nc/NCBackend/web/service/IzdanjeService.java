package com.ftn.nc.NCBackend.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Izdanje;

public interface IzdanjeService {

	Page<Izdanje> findByCasopis(Casopis casopis, Pageable pageable);
	
	Izdanje findById(Long id);
	
}
