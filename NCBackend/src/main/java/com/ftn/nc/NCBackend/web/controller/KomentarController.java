package com.ftn.nc.NCBackend.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.dto.KomentarDTO;
import com.ftn.nc.NCBackend.web.model.Komentar;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.service.KomentarService;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.RevizijaService;

@RestController
@RequestMapping(value = "/app/")
public class KomentarController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private RevizijaService revizijaService;
	
	@Autowired
	private KomentarService komentarService;
	
	@RequestMapping(value = "komentarisi", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> komentarisi(@RequestBody KomentarDTO komentarInfo, @RequestParam(value = "revizijaId", required = true) Long revizijaId, HttpServletRequest request){
		
		Korisnik posiljalac = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(posiljalac == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik primalac = korisnikService.getById(revizija.getAutor().getId());
		
		Komentar komentar = new Komentar(null, posiljalac, primalac, komentarInfo.getTekst(), false, komentarInfo.getVidljivost());
		komentar = komentarService.save(komentar);
		
		revizija.getKomentari().add(komentar);
		revizijaService.save(revizija);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "getKomentari", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KomentarDTO>> getKomentariForRecenzija(@RequestParam(value = "revizijaId", required = true) Long revizijaId){
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<KomentarDTO> komentari = new ArrayList<>();
		
		for(Komentar komentar : revizija.getKomentari()) {
			komentari.add(new KomentarDTO(komentar.getTekst(), komentar.getVidljivost(), komentar.getPosiljalac().getIme()+" "+komentar.getPosiljalac().getPrezime()));
		}
		
		return new ResponseEntity<List<KomentarDTO>>(komentari, HttpStatus.OK);
	}
	

}
