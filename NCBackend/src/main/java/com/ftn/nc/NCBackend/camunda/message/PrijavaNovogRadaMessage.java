package com.ftn.nc.NCBackend.camunda.message;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class PrijavaNovogRadaMessage implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("\n *** SALJE PORUKU OVIMA ***");
		
	}

}
