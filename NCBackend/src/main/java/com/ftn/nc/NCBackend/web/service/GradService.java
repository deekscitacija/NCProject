package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import com.ftn.nc.NCBackend.web.model.Grad;

public interface GradService {

	public Grad getById(Long id);
	
	public List<Grad> getAll();
}
