package com.ftn.nc.NCBackend.camunda.delegate;

import java.io.File;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.constants.ProjectConstants;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.repository.NaucniRadRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;

@Service
public class IndeksiranjeService implements JavaDelegate{
	
	@Autowired
	private RevizijaRadaRepository revizijaRadaRepository;
	
	@Autowired
	private NaucniRadRepository naucniRadRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long revizijaId = Long.parseLong((String) execution.getVariable("revizijaId"));
		RevizijaRada revizija = revizijaRadaRepository.getOne(revizijaId);
		
		File file = new File(revizija.getPutanja());
		file.renameTo(new File(ProjectConstants.LIBRARY_DIR_PATH+"\\"+file.getName()));
		
		revizija.setPrihvacen(true);
		NaucniRad rad = new NaucniRad(revizija, 10.00, ProjectConstants.LIBRARY_DIR_PATH+"\\"+file.getName(), null);
		revizijaRadaRepository.save(revizija);
		naucniRadRepository.save(rad);
	}

}
