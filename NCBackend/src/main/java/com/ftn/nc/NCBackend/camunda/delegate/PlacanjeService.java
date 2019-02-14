package com.ftn.nc.NCBackend.camunda.delegate;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Autor;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Pretplata;
import com.ftn.nc.NCBackend.web.repository.AutorRepository;
import com.ftn.nc.NCBackend.web.repository.CasopisRepository;
import com.ftn.nc.NCBackend.web.repository.PretplataRepository;

@Service
public class PlacanjeService implements JavaDelegate {


	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("*** TO DO: Placanje ***");
	
	}

}
