package com.ftn.nc.NCBackend.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.dto.TransakcijaDTO;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.Transakcija;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.TransakcijaService;

@RestController
@RequestMapping(value = "/app/")
public class TransakcijaController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private TransakcijaService transakcijaService;
	
	
	@RequestMapping(value = "getTransakcije", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransakcijaDTO>> getTransakcijeForUser(@RequestParam(value = "mode", required = true) String mode, HttpServletRequest request){
		
		Korisnik korisnik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		System.out.println(mode);
		
		List<Transakcija> transakcije = null;
		
		if(mode.equals("A")) {
			transakcije = transakcijaService.getAllForKorisnik(korisnik);
		}else if(mode.equals("C")) {
			transakcije = transakcijaService.getAllForKorisnikAndCasopisNotNull(korisnik);
		}else if(mode.equals("I")) {
			transakcije = transakcijaService.getAllForKorisnikAndIzdanjeNotNull(korisnik);
		}else if(mode.equals("R")) {
			transakcije = transakcijaService.getAllForKorisnikAndRadNotNull(korisnik);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<TransakcijaDTO> retVal = new ArrayList<>();
		
		for(Transakcija tempTrans : transakcije) {
			TransakcijaDTO tempDTO = new TransakcijaDTO(tempTrans);
			retVal.add(tempDTO);
		}
	
		return new ResponseEntity<List<TransakcijaDTO>>(retVal, HttpStatus.OK);
	}

}
