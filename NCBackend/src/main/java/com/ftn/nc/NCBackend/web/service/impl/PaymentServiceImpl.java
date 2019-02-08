package com.ftn.nc.NCBackend.web.service.impl;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ftn.nc.NCBackend.web.dto.payment.EntitetPlacanjaDTO;
import com.ftn.nc.NCBackend.web.dto.payment.PaymentRequestDTO;
import com.ftn.nc.NCBackend.web.dto.payment.PaymentResponseDTO;
import com.ftn.nc.NCBackend.web.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Value("${payment-gateway.code}")
	private String ncKod;
	
	@Value("${payment-gateway.success}")
	private String successPage;
	
	@Value("${payment-gateway.error}")
	private String errorPage;
	
	@Value("${payment-gateway.success}")
	private String failPage;

	@Override
	public PaymentRequestDTO buildPaymentRequest(String entitetKod, double iznos, boolean pretplata, Long transakcijaId) {
		
		EntitetPlacanjaDTO naucnaCentrala = new EntitetPlacanjaDTO(ncKod, null);
		EntitetPlacanjaDTO entitetPlacanja = new EntitetPlacanjaDTO(entitetKod, naucnaCentrala);
		
		return new PaymentRequestDTO(entitetPlacanja, iznos, pretplata, transakcijaId, successPage, failPage, errorPage);
	}

	@Override
	public ResponseEntity<PaymentResponseDTO> sendPaymentRequest(PaymentRequestDTO kpRequest, String kpRequestPath) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<PaymentRequestDTO> requestEntity = new HttpEntity<>(kpRequest);
		
		ResponseEntity<PaymentResponseDTO> response = null;
		
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
		
		try {
			response = restTemplate.exchange(kpRequestPath, HttpMethod.POST, requestEntity, PaymentResponseDTO.class); 
		}catch(Exception e) {
			System.out.println("PUKLO PLACANJE");
			//e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
