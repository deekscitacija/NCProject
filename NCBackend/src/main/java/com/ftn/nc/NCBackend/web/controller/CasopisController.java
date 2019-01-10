package com.ftn.nc.NCBackend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.elastic.repository.NaucnaOblastInfoRepository;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.service.CasopisService;

@RestController
@RequestMapping(value = "/app/")
public class CasopisController {
	
	@Autowired
	private CasopisService casopisService;
	
	@RequestMapping(value = "getPageMagazine", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Casopis>> getPage(@RequestParam(value = "pageNum", required = true) int pageNum){
		
		if(pageNum < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		System.out.println(pageNum);
		
		return new ResponseEntity<Page<Casopis>>(casopisService.getAll(pageNum, 4), HttpStatus.OK);
	}

}