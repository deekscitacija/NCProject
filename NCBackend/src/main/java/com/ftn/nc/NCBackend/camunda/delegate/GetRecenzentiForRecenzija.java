package com.ftn.nc.NCBackend.camunda.delegate;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;
import com.ftn.nc.NCBackend.web.repository.RecenzentRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRecenzentRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;

@Service
public class GetRecenzentiForRecenzija implements JavaDelegate {
	
	@Autowired
	private RecenzentRepository recenzentRepository;
	
	@Autowired
	private RevizijaRadaRecenzentRepository revizijaRadaRecenzentRepository;
	
	@Autowired
	private RevizijaRadaRepository revizijaRadaRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long revizijaId = Long.parseLong((String) execution.getVariable("revizijaId"));
		RevizijaRada revizija = revizijaRadaRepository.getOne(revizijaId);
		
		ArrayList<String> listaRecenzenata = new ArrayList<String>();
		
		for(RevizijaRadaRecenzent rrr : revizija.getRecenzentiRevizija()) {
			if(rrr.isZavrseno()) {
				listaRecenzenata.add(rrr.getRecenzent().getId().toString());
			}
		}

		execution.setVariable("listaRecenzenata", listaRecenzenata);
	}

}
