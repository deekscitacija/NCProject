package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.repository.CasopisRepository;
import com.ftn.nc.NCBackend.web.service.CasopisService;

@Service
public class CasopisServiceImpl implements CasopisService{
	
	@Autowired
	private CasopisRepository casopisRepository;

	@SuppressWarnings("deprecation")
	@Override
	public Page<Casopis> getAll(int offset, int num) {
		PageRequest request = new PageRequest(offset, num, Direction.ASC, "naziv");
		return casopisRepository.findAll(request);
	}

	@Override
	public Casopis getById(Long id) {
		
		return casopisRepository.getOne(id);
	}

}
