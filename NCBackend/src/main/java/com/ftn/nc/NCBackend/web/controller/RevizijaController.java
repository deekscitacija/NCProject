package com.ftn.nc.NCBackend.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.camunda.dto.RevizijaDTO;
import com.ftn.nc.NCBackend.web.dto.KomentarDTO;
import com.ftn.nc.NCBackend.web.dto.RevizijaRadaRecenzentDTO;
import com.ftn.nc.NCBackend.web.enums.KomentarVidljivost;
import com.ftn.nc.NCBackend.web.model.Komentar;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.RevizijaService;

@RestController
@RequestMapping(value = "/app/")
public class RevizijaController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private RevizijaService revizijaService;
	
	@RequestMapping(value = "getRevizija", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RevizijaDTO> getRevizija(@RequestParam(value = "revizijaId", required = true) Long revizijaId){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik autor = korisnikService.getById(revizija.getAutor().getId());
		
		List<KomentarDTO> retVal = new ArrayList<>();
		
		for(Komentar komentar : revizija.getKomentari()) {
			Korisnik recenzent = korisnikService.getById(komentar.getPosiljalac().getId());
			retVal.add(new KomentarDTO(komentar.getTekst(), komentar.getVidljivost(), recenzent.getIme()+" "+recenzent.getPrezime()));
		}
		
		return new ResponseEntity<RevizijaDTO>(new RevizijaDTO(revizija, autor, "", retVal), HttpStatus.OK);
	}
	
	@RequestMapping(value = "getRevizijaAutor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RevizijaDTO> getRevizijaAutor(@RequestParam(value = "revizijaId", required = true) Long revizijaId){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik autor = korisnikService.getById(revizija.getAutor().getId());
		
		List<KomentarDTO> retVal = new ArrayList<>();
		
		for(Komentar komentar : revizija.getKomentari()) {
			if(komentar.getVidljivost().equals(KomentarVidljivost.SVI)) {
				Korisnik recenzent = korisnikService.getById(komentar.getPosiljalac().getId());
				retVal.add(new KomentarDTO(komentar.getTekst(), komentar.getVidljivost(), recenzent.getIme()+" "+recenzent.getPrezime()));
			}
		}
		
		return new ResponseEntity<RevizijaDTO>(new RevizijaDTO(revizija, autor, "", retVal), HttpStatus.OK);
	}
	
	@RequestMapping(value = "getGotovaRevizijaInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RevizijaRadaRecenzentDTO>> getMagazine(@RequestParam(value = "revizijaId", required = true) Long revizijaId){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<RevizijaRadaRecenzentDTO> retVal = new ArrayList<>();
		
		for(RevizijaRadaRecenzent rrr : revizija.getRecenzentiRevizija()) {
			if(rrr.isAktuelno()) {
				Korisnik recenzent = korisnikService.getById(rrr.getRecenzent().getId());
				retVal.add(new RevizijaRadaRecenzentDTO(rrr, recenzent));
			}
		}
	
		return new ResponseEntity<List<RevizijaRadaRecenzentDTO>>(retVal, HttpStatus.OK);
	}

}
