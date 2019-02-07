package com.ftn.nc.NCBackend.web.service;

import com.ftn.nc.NCBackend.web.dto.payment.PaymentRequestDTO;

public interface PaymentService {
	
	public PaymentRequestDTO buildPaymentRequest(String entitetKod, double iznos, boolean pretplata, Long transakcijaId);

}
