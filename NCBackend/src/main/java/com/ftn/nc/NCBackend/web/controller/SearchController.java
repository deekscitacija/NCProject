package com.ftn.nc.NCBackend.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.elastic.dto.IndexUnitDTO;
import com.ftn.nc.NCBackend.elastic.dto.QueryDTO;
import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.RecenzentInfo;
import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Grad;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.service.CasopisService;
import com.ftn.nc.NCBackend.web.service.GradService;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.SearchService;

@RestController
@RequestMapping(value = "/app/")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private GradService gradService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private CasopisService casopisService;

	
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
	
	@RequestMapping(value = "moreLikeThis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<IndexUnit>> moreLikeThis(@RequestParam(value = "pageNum", required = true) int pageNum,
			@RequestParam(value = "documentId", required = true) String documentId){
		
		if(pageNum < 0 || documentId.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		return new ResponseEntity<List<IndexUnit>>(searchService.moreLikeThis(documentId, pageNum), HttpStatus.OK);
	}
	
	@RequestMapping(value = "geoSearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RecenzentInfo>> geoSearch(@RequestParam(value = "cityId", required = true) int cityId){
		
		if(cityId < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Grad grad = gradService.getById(new Long(cityId));
		
		if(grad == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		return new ResponseEntity<List<RecenzentInfo>>(searchService.geoSearch(grad.getLat(), grad.getLon()), HttpStatus.OK);
	}
	
	@RequestMapping(value = "index", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> uploadAndIndex(@ModelAttribute IndexUnitDTO newPaper, HttpServletRequest request){
		
		Korisnik autor = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(autor == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Casopis casopis = casopisService.getById(newPaper.getCasopisId());
		
		if(casopis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(!searchService.uploadAndIndex(newPaper, autor, casopis)){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

}
