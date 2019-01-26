package com.ftn.nc.NCBackend.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.securityBeans.CustomUserDetailsFactory;
import com.ftn.nc.NCBackend.web.dto.KorisnikDTO;
import com.ftn.nc.NCBackend.web.dto.LoginDTO;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.service.KorisnikService;

@RestController
@RequestMapping(value = "/app/")
public class LoginRegisterController {
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> login(@RequestBody LoginDTO loginParams){
		
		if(loginParams == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(loginParams.getEmail() == null || loginParams.getPassword() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik korisnik = korisnikService.getByEmail(loginParams.getEmail());
		HttpHeaders headers = new HttpHeaders();
		
		if(korisnik == null) {
			headers.add("message", "Neispravni kredencijali.");
			return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
		}
		
		if(!korisnik.getLozinka().equals(loginParams.getPassword())) {
			headers.add("message", "Neispravni kredencijali.");
			return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
		}
		
		String token = tokenUtils.generateToken(CustomUserDetailsFactory.createCustomUserDetails(korisnik));
		
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
	
	@RequestMapping(value = "getUserFromToken", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KorisnikDTO> getUserFromToken(HttpServletRequest request){
		
		Korisnik korisnik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(korisnik), HttpStatus.OK);
	}
}
