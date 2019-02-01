package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Recenzent;

public interface RecenzentService {

	public List<Recenzent> findAllByCasopis(Casopis casopis);
	
}
