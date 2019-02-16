package com.ftn.nc.NCBackend.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.camunda.dto.RecenzentDTO;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;
import com.ftn.nc.NCBackend.web.service.RevizijaService;

@Service
public class RevizijaRadaServiceImpl implements RevizijaService {
	
	@Autowired
	private RevizijaRadaRepository revizijaRadaRepository;
	
	@Autowired
	private KorisnikRepository korisnikRepository;

	@Override
	public RevizijaRada save(RevizijaRada revizijaRada) {
		
		return revizijaRadaRepository.save(revizijaRada);
	}

	@Override
	public RevizijaRada getById(Long id) {
		
		return revizijaRadaRepository.findById(id).get();
	}

	@Override
	public List<RecenzentDTO> getRecenzentiForNaucnaOblast(Casopis casopis, NaucnaOblast naucnaOblast) {
		List<RecenzentDTO> retVal = new ArrayList<>();
		
		for(Recenzent recenzent : casopis.getRecenzenti()) {
			Korisnik korisnik = korisnikRepository.getOne(recenzent.getId());
			
			for(NaucnaOblast tempNaucna : recenzent.getNaucneOblasti()) {
				if(tempNaucna.getKod().equals(naucnaOblast.getKod())) {
					retVal.add(new RecenzentDTO(korisnik));
					break;
				}
			}
		}
		
		return retVal;
	}

}
