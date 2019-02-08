package com.ftn.nc.NCBackend.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.dto.payment.PaymentRequestDTO;
import com.ftn.nc.NCBackend.web.dto.payment.PaymentResponseDTO;
import com.ftn.nc.NCBackend.web.enums.TransakcijaStatus;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.Transakcija;
import com.ftn.nc.NCBackend.web.service.IzdanjeService;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.NaucniRadService;
import com.ftn.nc.NCBackend.web.service.PaymentService;
import com.ftn.nc.NCBackend.web.service.TransakcijaService;

@RestController
@RequestMapping(value = "/app/")
public class PaymentContoller {
	
	@Value("${payment-gateway.request-path}")
	private String kpRequestPath;
	
	@Autowired
	private IzdanjeService izdanjeService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private TransakcijaService transakcijaService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private NaucniRadService naucniRadService;
	
	@RequestMapping(value = "pretplata", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> pretplata(){
		
		return null;
	}
	
	@RequestMapping(value = "kupiIzdanje", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaymentResponseDTO> kupiIzdanje(@RequestParam(value = "izdanjeId", required = true) int izdanjeId, HttpServletRequest request){
		
		Korisnik korisnik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Izdanje izdanje = izdanjeService.findById(new Long(izdanjeId));
		
		if(izdanje == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<Transakcija> kupio = transakcijaService.getAllForKorisnikAndIzdanje(korisnik, izdanje);
		
		if(!kupio.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		Transakcija transakcija = new Transakcija(null, TransakcijaStatus.C, new Date(System.currentTimeMillis()), null, korisnik, false, null, izdanje, null, izdanje.getCenaIzdanja());
		transakcija = transakcijaService.save(transakcija);
		
		PaymentRequestDTO kpRequest = paymentService.buildPaymentRequest(izdanje.getKoncentratorKod(), izdanje.getCenaIzdanja(), false, transakcija.getId());
		
		return paymentService.sendPaymentRequest(kpRequest, kpRequestPath);
	}
	
	@RequestMapping(value = "kupiRad", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> kupiRad(@RequestParam(value = "radId", required = true) int radId, HttpServletRequest request){
		
		Korisnik korisnik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		NaucniRad rad = naucniRadService.getById(new Long(radId));
		
		if(rad == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<Transakcija> kupio = transakcijaService.getAllForKorisnikAndRad(korisnik, rad);
		
		if(!kupio.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		Transakcija transakcija = new Transakcija(null, TransakcijaStatus.C, new Date(System.currentTimeMillis()), null, korisnik, false, null, null, rad, rad.getCena());
		transakcija = transakcijaService.save(transakcija);
		
		PaymentRequestDTO kpRequest = paymentService.buildPaymentRequest(rad.getKoncentratorKod(), rad.getCena(), false, transakcija.getId());
		
		return paymentService.sendPaymentRequest(kpRequest, kpRequestPath);
	}

}
