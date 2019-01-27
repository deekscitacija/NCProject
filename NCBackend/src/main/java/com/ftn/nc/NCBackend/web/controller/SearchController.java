package com.ftn.nc.NCBackend.web.controller;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.elastic.dto.QueryDTO;
import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.web.service.SearchService;

@RestController
@RequestMapping(value = "/app/")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<IndexUnit>> search(@RequestBody @NotNull QueryDTO searchParams, BindingResult result){
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(searchParams.getParams() == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		if(searchParams.getParams().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		if(searchParams.isAllFields()) {
			return new ResponseEntity<>(searchService.executeSearchAll(searchParams), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(searchService.executeSearch(searchParams), HttpStatus.OK);
	}

}
