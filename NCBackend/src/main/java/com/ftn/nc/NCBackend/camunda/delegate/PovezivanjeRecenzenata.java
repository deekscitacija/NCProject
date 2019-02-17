package com.ftn.nc.NCBackend.camunda.delegate;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;
import com.ftn.nc.NCBackend.web.repository.RecenzentRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRecenzentRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;

@Service
public class PovezivanjeRecenzenata implements JavaDelegate {
	
	@Autowired
	private RecenzentRepository recenzentRepository;
	
	@Autowired
	private RevizijaRadaRecenzentRepository revizijaRadaRecenzentRepository;
	
	@Autowired
	private RevizijaRadaRepository revizijaRadaRepository;
	

	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<String> recenzentiId = (List<String>) execution.getVariable("listaRecenzenata");
		Long revizijaId = Long.parseLong((String) execution.getVariable("revizijaId"));
		RevizijaRada revizija = revizijaRadaRepository.getOne(revizijaId);
		
		for(String recId : recenzentiId) {
			Long recenzentId = Long.parseLong(recId);
			Recenzent recenzent = recenzentRepository.getOne(recenzentId);
			RevizijaRadaRecenzent rrr = new RevizijaRadaRecenzent(null, recenzent, revizija, false, null);
			rrr = revizijaRadaRecenzentRepository.save(rrr);
			revizija.getRecenzentiRevizija().add(rrr);
			revizijaRadaRepository.save(revizija);
		}
		
	}

}
