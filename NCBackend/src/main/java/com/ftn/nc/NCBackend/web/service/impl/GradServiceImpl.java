package com.ftn.nc.NCBackend.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Grad;
import com.ftn.nc.NCBackend.web.repository.GradRepository;
import com.ftn.nc.NCBackend.web.service.GradService;

@Service
public class GradServiceImpl implements GradService{
	
	@Autowired
	private GradRepository gradRepository;

	@Override
	public Grad getById(Long id) {
		
		return gradRepository.getOne(id);
	}

	@Override
	public List<Grad> getAll() {
		
		return gradRepository.findAll();
	}

}
