package com.ftn.nc.NCBackend.camunda.delegate;

import java.io.File;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.helpClasses.PDFUtils;
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
		
		System.out.println("\n *** INDEKSIRANJE ***\n");
		
		Long revizijaId = Long.parseLong((String) execution.getVariable("revizijaId"));
		RevizijaRada revizija = revizijaRadaRepository.getOne(revizijaId);
		
		File file = new File(revizija.getPutanja());
		file.renameTo(new File(PDFUtils.LIBRARY_DIR_PATH+"\\"+file.getName()));
		
		revizija.setPrihvacen(true);
		NaucniRad rad = new NaucniRad(revizija, 10.00, PDFUtils.LIBRARY_DIR_PATH+"\\"+file.getName(), null);
		naucniRadRepository.save(rad);
	}

}
