package com.ftn.nc.NCBackend.camunda.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.nc.NCBackend.camunda.service.CommonCamundaService;
import com.ftn.nc.NCBackend.helpClasses.PDFUtils;
import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.dto.NaucniRadDTO;
import com.ftn.nc.NCBackend.web.model.Autor;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Koautor;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.service.AutorService;
import com.ftn.nc.NCBackend.web.service.CasopisService;
import com.ftn.nc.NCBackend.web.service.KoautorService;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.RevizijaService;


@RestController
@RequestMapping(value = "/app/")
public class NaucniRadCamundaController {
	
	@Value("${process-engine.roothPath}")
	private String processEngineRootPath;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private AutorService autorService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private CasopisService casopisService;
	
	@Autowired
	private RevizijaService revizijaService;
	
	@Autowired
	private KoautorService koautorService;
	
	@Autowired
	private CommonCamundaService commonCamundaService;
	
	
	@RequestMapping(value = "posaljiRad", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> posaljiRad(@ModelAttribute NaucniRadDTO newPaper, HttpServletRequest request){
		
		Korisnik korisnik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(korisnik == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Autor autor = autorService.getById(korisnik.getId());
		
		if(autor == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Casopis casopis = casopisService.getById(newPaper.getCasopisId());
		
		if(casopis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<Koautor> koautori = new ArrayList<Koautor>();
		
		for(Koautor koautor : newPaper.getKoautori()) {
			List<Koautor> koautorHits = koautorService.findByEmail(koautor.getEmail());
			
			if(koautorHits.isEmpty()) {
				koautori.add(koautorService.save(koautor));
			}else {
				koautori.add(koautorHits.get(0));
			}
		}
		
		for (MultipartFile file : newPaper.getFajlovi()) {

            if (file.isEmpty()) {
                continue;
            }
            
            String putanja = "";
			try {
				putanja = PDFUtils.saveUploadedFile(file, PDFUtils.REPO_DIR_PATH);
			} catch (IOException e) {
				System.out.println("*** Puklo cuvanje fajle! ***");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
            		
            RevizijaRada revizija = new RevizijaRada(null, newPaper.getNaslov(), koautori, 
            	newPaper.getApstrakt(), newPaper.getKljucne(), putanja, false, false, false, 
            	autor, casopis, newPaper.getNaucnaOblast(), null, null);
            
            revizija = revizijaService.save(revizija);
            break;
		}
		
		String taskInfo = commonCamundaService.getTasksByProcessInstanceIdAndAssignee(autor.getId().toString(), newPaper.getProcesId()).getBody();
		
		JSONArray taskList = null;
		String taskId = "";
		try {
			taskList = new JSONArray(taskInfo);
            taskId = (String) taskList.getJSONObject(0).get("id");
            return commonCamundaService.compliteTaskNoForm(taskId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	

}