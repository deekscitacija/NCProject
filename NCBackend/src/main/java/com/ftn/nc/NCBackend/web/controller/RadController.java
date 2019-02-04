package com.ftn.nc.NCBackend.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.NaucniRadService;

@RestController
@RequestMapping(value = "/app/")
public class RadController {

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private NaucniRadService naucniRadService;
	
	
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
			System.out.println(paperPdf.getName());
			headers.setContentDispositionFormData(paperPdf.getName(), paperPdf.getName());
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		} catch (IOException e) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	}
	
	
	
	
	
	

}
