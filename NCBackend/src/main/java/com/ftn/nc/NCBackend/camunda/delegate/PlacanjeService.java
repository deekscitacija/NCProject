package com.ftn.nc.NCBackend.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class PlacanjeService implements JavaDelegate {


	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("*** TO DO: Placanje ***");
	
	}

}
