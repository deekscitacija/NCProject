package com.ftn.nc.NCBackend.web.controller;

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

import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.web.service.SearchService;

@RestController
@RequestMapping(value = "/app/")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<IndexUnit>> search(
			@RequestParam(value = "pageNum", required = true) int pageNum,
			@RequestParam(value = "autor", required = false) String autor,
			@RequestParam(value = "casopis", required = false) String casopis,
			@RequestParam(value = "naslov", required = false) String naslov,
			@RequestParam(value = "kljucne", required = false) String kljucne,
			@RequestParam(value = "tekst", required = false) String tekst,
			@RequestParam(value = "naucne", required = false) List<String> naucneOblasti){
		
		if(pageNum < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(autor == null && casopis == null && naslov == null && kljucne == null && tekst == null && naucneOblasti == null) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		
		
		return new ResponseEntity<Page<IndexUnit>>(searchService.executeSearch(pageNum, autor, casopis, naslov, kljucne, tekst, null), HttpStatus.OK);
	}

}
