package com.ftn.nc.NCBackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.model.Casopis;
import com.ftn.nc.NCBackend.repository.CasopisRepository;
import com.ftn.nc.NCBackend.service.CasopisService;

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

}
