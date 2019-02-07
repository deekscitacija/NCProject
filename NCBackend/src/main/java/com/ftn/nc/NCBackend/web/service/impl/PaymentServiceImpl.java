package com.ftn.nc.NCBackend.web.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.dto.payment.EntitetPlacanjaDTO;
import com.ftn.nc.NCBackend.web.dto.payment.PaymentRequestDTO;
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
}
