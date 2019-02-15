package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Komentar;
import com.ftn.nc.NCBackend.web.repository.KomentarRepository;
import com.ftn.nc.NCBackend.web.service.KomentarService;

@Service
public class KomentarServiceImpl implements KomentarService {
	
	@Autowired
	private KomentarRepository komentarRepository;

	@Override
	public Komentar getById(Long id) {
		
		return komentarRepository.getOne(id);
	}

	@Override
	public Komentar save(Komentar komentar) {
		
		return komentarRepository.save(komentar);
	}

}
