package com.ftn.nc.NCBackend.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class IndeksiranjeService implements JavaDelegate{

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		
		System.out.println("\n *** INDEKSIRANJE ***\n");
		
	}

}
