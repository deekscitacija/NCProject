package com.ftn.nc.NCBackend.camunda.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.repository.CasopisRepository;

@Service
public class SelekcijaUrednikaService implements JavaDelegate {
	
	@Autowired
	private CasopisRepository casopisRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long casopisId = Long.parseLong((String) execution.getVariable("casopis"));
		Casopis casopis = casopisRepository.findById(casopisId).get();
		
		if(casopis == null) {
			throw new BpmnError("greska_nepostojeci_entitet");
		}
		
		if(casopis.getUrednik() == null) {
			throw new BpmnError("greska_nepostojeci_entitet");
		}
		
		execution.setVariable("odgovorniUrednik", casopis.getUrednik().getId().toString());
	}

}
