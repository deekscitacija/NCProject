package com.ftn.nc.NCBackend.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.web.model.Grad;
import com.ftn.nc.NCBackend.web.service.GradService;

@RestController
@RequestMapping(value = "/app/")
public class GradController {
	
	@Autowired
	private GradService gradService;
	
	@RequestMapping(value = "getAllCities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Grad>> findAllCities(){
		
		return new ResponseEntity<List<Grad>>(gradService.getAll(), HttpStatus.OK);
	}

}
