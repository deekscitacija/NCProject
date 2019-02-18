package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.TipKorisnika;
import com.ftn.nc.NCBackend.web.repository.TipKorisnikaRepository;
import com.ftn.nc.NCBackend.web.service.TipKorisnikaService;

@Service
public class TipKorisnikaServiceImpl implements TipKorisnikaService {
	
	@Autowired
	private TipKorisnikaRepository tipKorisnikaRepository;

	@Override
	public TipKorisnika getByKod(String kod) {
		
		return tipKorisnikaRepository.getByKod(kod);
	}

}
