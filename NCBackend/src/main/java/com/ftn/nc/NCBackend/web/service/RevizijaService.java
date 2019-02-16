package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import com.ftn.nc.NCBackend.camunda.dto.RecenzentDTO;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;

public interface RevizijaService {

	public RevizijaRada save(RevizijaRada revizijaRada);
	
	public RevizijaRada getById(Long id);
	
	public List<RecenzentDTO> getRecenzentiForNaucnaOblast(Casopis casopis, NaucnaOblast naucnaOblast);	
}
