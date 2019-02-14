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
public class ProveriClanarinuService implements JavaDelegate {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private CasopisRepository casopisRepository;
	
	@Autowired
	private PretplataRepository pretplataRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long korisnikId = Long.parseLong((String) execution.getVariable("autorRada"));
		Autor autor = autorRepository.findById(korisnikId).get();
		
		if(autor == null) {
			throw new BpmnError("greska_nepostojeci_entitet");
		}
		
		Long casopisId = Long.parseLong((String) execution.getVariable("casopis"));
		Casopis casopis = casopisRepository.findById(casopisId).get();
		
		if(casopis == null) {
			throw new BpmnError("greska_nepostojeci_entitet");
		}
		
		List<Pretplata> pretplate = pretplataRepository.findByAutorAndCasopis(autor, casopis);
		
		if(pretplate == null) {
			execution.setVariable("imaAktivnuClanarinu", false);
		}else {
			execution.setVariable("imaAktivnuClanarinu", chackIfExpired(pretplate));
		}
		
	}
	
	private boolean chackIfExpired(List<Pretplata> pretplate) {
		Date nowDate = new Date(System.currentTimeMillis());
		
		for(Pretplata pretplata : pretplate) {
			if(pretplata.getDatumOd().before(nowDate) && pretplata.getDatumOd().after(nowDate)) {
				return true;
			}
		}
			
		return false;
	}

}
