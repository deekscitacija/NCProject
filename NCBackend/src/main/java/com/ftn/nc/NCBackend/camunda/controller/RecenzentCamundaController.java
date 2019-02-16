package com.ftn.nc.NCBackend.camunda.controller;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.nc.NCBackend.camunda.dto.RecenzentDTO;
import com.ftn.nc.NCBackend.camunda.service.CommonCamundaService;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.service.CasopisService;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.RevizijaService;

@RestController
@RequestMapping(value = "/app/")
public class RecenzentCamundaController {
	
	@Autowired
	private CasopisService casopisService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private RevizijaService revizijaService;
	
	@Autowired
	private CommonCamundaService commonCamundaService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@RequestMapping(value = "getRecenzentiCasopis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RecenzentDTO>> getRecenzenti(@RequestParam (required = true, value = "casopisId") Long casopisId){
		
		Casopis casopis = casopisService.getById(casopisId);
		
		if(casopis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<RecenzentDTO> retVal = new ArrayList<>();
		
		for(Recenzent recenzent : casopis.getRecenzenti()) {
			Korisnik korisnik = korisnikService.getById(recenzent.getId());
			retVal.add(new RecenzentDTO(korisnik));
		}
		
		return new ResponseEntity<List<RecenzentDTO>>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "getRecenzentiCasopisAndNaucna", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RecenzentDTO>> getRecenzentiForNaucna(@RequestParam (required = true, value = "casopisId") Long casopisId,
												@RequestParam (required = true, value = "revizijaId") Long revizijaId){
		
		Casopis casopis = casopisService.getById(casopisId);
		
		if(casopis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		NaucnaOblast naucnaOblast = revizija.getNaucnaOblast();
		
		return new ResponseEntity<List<RecenzentDTO>>(revizijaService.getRecenzentiForNaucnaOblast(casopis, naucnaOblast), HttpStatus.OK);
	}
	
	@RequestMapping(value = "izaberiRecenzente", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRecenzentiForNaucna(@RequestParam (required = true, value = "revizijaId") Long revizijaId, @RequestParam (required = true, value = "casopisId") Long casopisId,
						@RequestParam (required = true, value = "taskId") String taskId, @RequestParam (required = true, value = "processId") String processId,
						@RequestBody List<RecenzentDTO> recenzenti){
		
		Casopis casopis = casopisService.getById(casopisId);
		
		if(casopis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			if(revizijaService.getRecenzentiForNaucnaOblast(casopis, revizija.getNaucnaOblast()).isEmpty()) {
				commonCamundaService.setProcessVariable(processId, "imaZaNaucnu", "false", "boolean");
				return commonCamundaService.compliteTaskNoForm(taskId);
			}else {
				commonCamundaService.setProcessVariable(processId, "imaZaNaucnu", "true", "boolean");
			}	
		} catch (JSONException e) {
			System.out.println("\nPuklo ImaZaNaucnu");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(recenzenti.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<String> recStr = new ArrayList<String>();
		for(RecenzentDTO recenzent : recenzenti) {
			recStr.add(recenzent.getId().toString());
		}
		
		runtimeService.setVariable(processId,"listaRecenzenata", recStr);
		return commonCamundaService.compliteTaskNoForm(taskId);
	}

}
