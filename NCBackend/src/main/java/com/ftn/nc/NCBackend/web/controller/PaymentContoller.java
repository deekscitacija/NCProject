package com.ftn.nc.NCBackend.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.dto.payment.PaymentRequestDTO;
import com.ftn.nc.NCBackend.web.dto.payment.PaymentResponseDTO;
import com.ftn.nc.NCBackend.web.enums.SyncStatus;
import com.ftn.nc.NCBackend.web.enums.TransakcijaStatus;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.TipKorisnika;
import com.ftn.nc.NCBackend.web.model.Transakcija;
import com.ftn.nc.NCBackend.web.service.CasopisService;
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
	
	@Autowired
	private CasopisService casopisService;

	
	@RequestMapping(value = "pretplata", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaymentResponseDTO> pretplata(@RequestParam(value = "magazineId", required = true) int magazineId, HttpServletRequest request){
		
		Korisnik korisnik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Casopis casopis = casopisService.getById(new Long(magazineId));
		
		if(casopis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<Transakcija> kupio = transakcijaService.ifExsistsKorisnikAndCasopis(korisnik, casopis);
		
		if(!kupio.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		boolean autor = false;
		
		for(TipKorisnika tip : korisnik.getTip()) {
			if(tip.getKod().equals("AU")) {
				autor = true;
				break;
			}
		}
		
		Double cena = (autor) ? casopis.getCenaClanarine() : casopis.getCenaPretplate();  
		
		Transakcija transakcija = new Transakcija(null, TransakcijaStatus.C, new Date(System.currentTimeMillis()), null, korisnik, false, casopis, null, null, cena);
		transakcija = transakcijaService.save(transakcija);
		
		PaymentRequestDTO kpRequest = paymentService.buildPaymentRequest(casopis.getKoncentratorKod(), cena, true, transakcija.getId());
		
		return paymentService.sendPaymentRequest(kpRequest, kpRequestPath);
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
		
		List<Transakcija> kupio = transakcijaService.ifExsistsKorisnikAndIzdanje(korisnik, izdanje);
		
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
		
		List<Transakcija> kupio = transakcijaService.ifExsistsKorisnikAndRad(korisnik, rad);
		
		if(!kupio.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		Transakcija transakcija = new Transakcija(null, TransakcijaStatus.C, new Date(System.currentTimeMillis()), null, korisnik, false, null, null, rad, rad.getCena());
		transakcija = transakcijaService.save(transakcija);
		
		PaymentRequestDTO kpRequest = paymentService.buildPaymentRequest(rad.getKoncentratorKod(), rad.getCena(), false, transakcija.getId());
		
		return paymentService.sendPaymentRequest(kpRequest, kpRequestPath);
	}
	
	@RequestMapping(value = "syncKP", method =  RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SyncStatus> syncKP(@RequestBody Map<Long, TransakcijaStatus> transakcije){
		
		for (Map.Entry<Long, TransakcijaStatus> entry : transakcije.entrySet())
		{
		    System.out.println(entry.getKey() + "/" + entry.getValue());
		    Transakcija zaIzmenu = transakcijaService.findById(entry.getKey());
		    
		    if(zaIzmenu != null) {
		    	transakcijaService.saveNewStatus(zaIzmenu, entry.getValue());
		    }
		}
		
		return new ResponseEntity<SyncStatus>(SyncStatus.SUCCESS, HttpStatus.OK);
	}
}
