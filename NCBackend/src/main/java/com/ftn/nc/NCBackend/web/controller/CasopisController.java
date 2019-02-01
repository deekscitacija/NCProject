package com.ftn.nc.NCBackend.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.web.dto.CasopisDTO;
import com.ftn.nc.NCBackend.web.dto.KorisnikDTO;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.service.CasopisService;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.NaucneOblastiService;
import com.ftn.nc.NCBackend.web.service.RecenzentService;

@RestController
@RequestMapping(value = "/app/")
public class CasopisController {
	
	@Autowired
	private CasopisService casopisService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private RecenzentService recenzentService;
	
	@Autowired
	private NaucneOblastiService naucneOblastiService;
	
	@RequestMapping(value = "getPageMagazine", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Casopis>> getPage(@RequestParam(value = "pageNum", required = true) int pageNum){
		
		if(pageNum < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Page<Casopis>>(casopisService.getAll(pageNum, 4), HttpStatus.OK);
	}
	
	@RequestMapping(value = "getMagazine", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CasopisDTO> getMagazine(@RequestParam(value = "magazineId", required = true) int magazineId){
		
		if(magazineId < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Casopis retVal = casopisService.getById(new Long(magazineId));
		
		if(retVal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik korisnik = korisnikService.getById(retVal.getUrednik().getId());
		
		return new ResponseEntity<CasopisDTO>(new CasopisDTO(retVal, korisnik), HttpStatus.OK);
	}
	
	@RequestMapping(value = "getNaucneOblasti", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NaucnaOblast>> getNaucneOblasti(){
		
		return new ResponseEntity<List<NaucnaOblast>>(naucneOblastiService.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "getRecenzenti", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KorisnikDTO>> getRecenzenti(@RequestParam(value = "magazineId", required = true) int magazineId){
		
		if(magazineId < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Casopis casopis = casopisService.getById(new Long(magazineId));
		
		if(casopis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<Recenzent> recenzenti = recenzentService.findAllByCasopis(casopis);
		List<KorisnikDTO> retVal = new ArrayList<>();
		
		for(Recenzent recenzent : recenzenti) {
			retVal.add(new KorisnikDTO(korisnikService.getById(recenzent.getId())));
		}
		
		return new ResponseEntity<List<KorisnikDTO>>(retVal, HttpStatus.OK);
	}

}
