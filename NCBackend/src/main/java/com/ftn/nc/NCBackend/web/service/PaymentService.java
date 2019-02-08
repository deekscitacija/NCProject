package com.ftn.nc.NCBackend.web.service;

import org.springframework.http.ResponseEntity;

import com.ftn.nc.NCBackend.web.dto.payment.PaymentRequestDTO;
import com.ftn.nc.NCBackend.web.dto.payment.PaymentResponseDTO;

public interface PaymentService {
	
	public PaymentRequestDTO buildPaymentRequest(String entitetKod, double iznos, boolean pretplata, Long transakcijaId);
	
	public ResponseEntity<PaymentResponseDTO> sendPaymentRequest(PaymentRequestDTO kpRequest, String kpRequestPath);

}
