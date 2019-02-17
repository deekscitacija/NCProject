package com.ftn.nc.NCBackend.camunda.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.dto.KomentarDTO;
import com.ftn.nc.NCBackend.web.enums.KomentarVidljivost;
import com.ftn.nc.NCBackend.web.enums.RecenzijaStatus;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Komentar;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;
import com.ftn.nc.NCBackend.web.service.CasopisService;
import com.ftn.nc.NCBackend.web.service.KorisnikService;
import com.ftn.nc.NCBackend.web.service.RevizijaRadaRecenzentService;
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
	private RevizijaRadaRecenzentService revizijaRadaRecenzentService; 
	
	@Autowired
	private CommonCamundaService commonCamundaService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
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
	
	@RequestMapping(value = "getRecenzentiCasopisExpired", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RecenzentDTO>> getRecenzentiCasopisExpired(@RequestParam (required = true, value = "casopisId") Long casopisId,
																		  @RequestParam (required = true, value = "revizijaId") Long revizijaId){
		
		Casopis casopis = casopisService.getById(casopisId);
		
		if(casopis == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<RecenzentDTO> retVal = new ArrayList<>();
		
		for(Recenzent recenzent : casopis.getRecenzenti()) {
			boolean contains = false;
			for(RevizijaRadaRecenzent rrr : revizija.getRecenzentiRevizija()) {
				if(rrr.getRecenzent().getId().equals(recenzent.getId())) {
					contains = true;
					break;
				}
			}
			if(!contains) {
				Korisnik korisnik = korisnikService.getById(recenzent.getId());
				retVal.add(new RecenzentDTO(korisnik));
			}
		}
		
		return new ResponseEntity<List<RecenzentDTO>>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "izaberiRecenzente", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRecenzentiForNaucna(@RequestParam (required = true, value = "revizijaId") Long revizijaId, @RequestParam (required = true, value = "casopisId") Long casopisId,
						@RequestParam (required = true, value = "taskId") String taskId, @RequestParam (required = true, value = "processId") String processId,
						@RequestBody List<RecenzentDTO> recenzenti, HttpServletRequest request){
		
		Korisnik korisnik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(korisnik == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if(korisnik.getId() != commonCamundaService.getAssigneeId(taskId)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
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
		
		if(recenzenti.size() < 2) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<String> recStr = new ArrayList<String>();
		for(RecenzentDTO recenzent : recenzenti) {
			recStr.add(recenzent.getId().toString());
		}
		
		runtimeService.setVariable(processId,"listaRecenzenata", recStr);
		return commonCamundaService.compliteTaskNoForm(taskId);
	}
	
	@RequestMapping(value = "submitRecenzija", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> submitRecenzija(@RequestParam (required = true, value = "processId") String processId, @RequestParam (required = true, value = "taskId") String taskId,
			   								@RequestParam (required = true, value = "revizijaId") Long revizijaId, @RequestParam (required = true, value = "revizijaStatus") RecenzijaStatus revizijaStatus,
			   							    HttpServletRequest request){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik recenzent = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(recenzent == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		RevizijaRadaRecenzent recenzijaInfo = null;
		
		for(RevizijaRadaRecenzent tempR : revizija.getRecenzentiRevizija()) {
			if(tempR.getRecenzent().getId() == recenzent.getId() && tempR.isAktuelno()) {
				recenzijaInfo = tempR;
				break;
			}
		}
		
		if(recenzijaInfo != null) {
			recenzijaInfo.setStatus(revizijaStatus);
			recenzijaInfo.setZavrseno(true);
			revizijaRadaRecenzentService.save(recenzijaInfo);
			return commonCamundaService.compliteTaskNoForm(taskId);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "submitAnalizaRecenzija", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> submitAnalizaRecenzija(@RequestParam (required = true, value = "taskId") String taskId, @RequestParam (required = true, value = "revizijaId") Long revizijaId, 
													@RequestParam (required = true, value = "revizijaStatus") RecenzijaStatus revizijaStatus, @RequestParam (required = true, value = "processId") String processId, HttpServletRequest request){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik urednik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(urednik == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if(urednik.getId() != commonCamundaService.getAssigneeId(taskId)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			revizija.setStatus(revizijaStatus);
			if(revizijaStatus.equals(RecenzijaStatus.P)) {
				revizija.setPrihvacen(true);
			}
			revizija = revizijaService.save(revizija);
			commonCamundaService.setProcessVariable(processId, "revizijaStatus ", revizijaStatus.toString(), "string");
			commonCamundaService.compliteTaskNoForm(taskId);
		} catch (JSONException e) {
			System.out.println("*** Parsiranje JSON ***");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "getKomentariAutor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KomentarDTO>> getKomentariAutor(@RequestParam (required = true, value = "revizijaId") Long revizijaId){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<KomentarDTO> retVal = new ArrayList<>();
		
		for(Komentar komentar : revizija.getKomentari()) {
			if(komentar.getVidljivost().equals(KomentarVidljivost.SVI)) {
				Korisnik recenzent = korisnikService.getById(komentar.getPosiljalac().getId());
				retVal.add(new KomentarDTO(komentar.getTekst(), komentar.getVidljivost(), recenzent.getIme()+" "+recenzent.getPrezime()));
			}
		}
		
		return new ResponseEntity<List<KomentarDTO>>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "submitPonovnoPregledanje", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> submitPonovnoPregledanje(@RequestParam (required = true, value = "taskId") String taskId, @RequestParam (required = true, value = "revizijaId") Long revizijaId, 
													  @RequestParam (required = true, value = "processId") String processId, @RequestParam (required = true, value = "ishod") boolean ishod, HttpServletRequest request){
		
		RevizijaRada revizija = revizijaService.getById(revizijaId);
		
		if(revizija == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Korisnik urednik = korisnikService.getUserFromToken(request, tokenUtils);
		
		if(urednik == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if(urednik.getId() != commonCamundaService.getAssigneeId(taskId)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			commonCamundaService.setProcessVariable(processId, "zadovoljavajuce ", (ishod) ? "true" : "false", "boolean");
			commonCamundaService.compliteTaskNoForm(taskId);
		} catch (JSONException e) {
			System.out.println("*** Parsiranje JSON ***");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
