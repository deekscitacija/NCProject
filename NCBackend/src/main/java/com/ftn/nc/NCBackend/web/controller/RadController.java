package com.ftn.nc.NCBackend.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.camunda.dto.RevizijaDTO;
import com.ftn.nc.NCBackend.web.dto.IzdanjeDTO;
import com.ftn.nc.NCBackend.web.dto.RadDTO;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.Komentar;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.service.IzdanjeService;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.NaucniRadService;
import com.ftn.nc.NCBackend.web.service.RevizijaService;

@RestController
@RequestMapping(value = "/app/")
public class RadController {

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private NaucniRadService naucniRadService;
	
	@Autowired
	private IzdanjeService izdanjeService;
	
	@Autowired
	private RevizijaService revizijaService;
	
	
	@RequestMapping(value = "download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> download(@RequestParam (value = "paperId", required = true) int paperId, HttpServletResponse response){
		
		NaucniRad rad = naucniRadService.getById(new Long(paperId));
		
		if(rad == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
		
		byte[] content;
		
		try {
			File paperPdf = new File(rad.getPutanja());
			content = Files.readAllBytes(paperPdf.toPath());
			headers.setContentDispositionFormData(paperPdf.getName(), paperPdf.getName());
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		} catch (IOException e) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "downloadRevizija", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadRevizija(@RequestParam (value = "revizijaId", required = true) Long revizijaId, HttpServletResponse response){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
		
		byte[] content;
		
		try {
			File paperPdf = new File(revizija.getPutanja());
			content = Files.readAllBytes(paperPdf.toPath());
			headers.setContentDispositionFormData(paperPdf.getName(), paperPdf.getName());
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		} catch (IOException e) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "getIzdanje", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IzdanjeDTO> getIzdanje(@RequestParam(value = "izdanjeId", required = true) int izdanjeId){
		
		if(izdanjeId < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Izdanje izdanje = izdanjeService.findById(new Long(izdanjeId));
		
		if(izdanje == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<RadDTO> radovi = new ArrayList<>();
		for(NaucniRad rad : izdanje.getRadovi()) {
			Korisnik autor = korisnikService.getById(rad.getRevizija().getAutor().getId());
			RadDTO radDTO = new RadDTO(rad, autor);
			radovi.add(radDTO);
		}
		
		return new ResponseEntity<IzdanjeDTO>(new IzdanjeDTO(izdanje, radovi), HttpStatus.OK);
	}
	

	@RequestMapping(value = "getRevizija", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RevizijaDTO> getMagazine(@RequestParam(value = "revizijaId", required = true) Long revizijaId){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik autor = korisnikService.getById(revizija.getAutor().getId());
		
		String komentar = "";
		if(revizija.getKomentari() != null) {
			if(!revizija.getKomentari().isEmpty()) {
				komentar = revizija.getKomentari().get(0).getTekst();
			}
		}
		
		return new ResponseEntity<RevizijaDTO>(new RevizijaDTO(revizija, autor, komentar), HttpStatus.OK);
	}
	
}
