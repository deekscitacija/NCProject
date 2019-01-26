package com.ftn.nc.NCBackend.web.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.camunda.bpm.engine.IdentityService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftn.nc.NCBackend.security.TokenUtils;
import com.ftn.nc.NCBackend.web.dto.FileMessageResourceDTO;
import com.ftn.nc.NCBackend.web.dto.FormFieldDTO;
import com.ftn.nc.NCBackend.web.dto.FormFieldsDTO;
import com.ftn.nc.NCBackend.web.dto.RegistracijaDTO;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.service.KorisnikService;

@RestController
@RequestMapping(value = "/app/")
public class ProcessEngineController {
	
	@Value("${process-engine.roothPath}")
	private String processEngineRootPath;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private IdentityService identityService;
	
	@RequestMapping(value = "deploySve", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deploySve() {
		
		File dir = new File("src/main/resources");

        File[] files = dir.listFiles(new FilenameFilter() { 
                 
				@Override
				public boolean accept(java.io.File arg0, String arg1) {
					return arg1.endsWith(".bpmn");
				}
        });
        
        for(File f : files) {
        	
        	MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
            parts.add("deployment-name", "mjok-"+f.getName().replace(".bmpn", ""));
            parts.add("deployment-source", "mjok-source-"+f.getName().replace(".bmpn", ""));
           
            try {
    			parts.add("data", new FileMessageResourceDTO(Files.readAllBytes(f.toPath()), f.getName()));
    		} catch (IOException e) {
    			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    		}
            
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(parts, headers);

            restTemplate.postForEntity(processEngineRootPath+"deployment/create", requestEntity, JSONObject.class);
        }
        
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@RequestMapping(value = "pokreniObjavu", method = RequestMethod.GET)
	public ResponseEntity<?> pokreniObjavu(HttpServletRequest request) {
		
		Korisnik korisnik = korisnikService.getUserFromToken(request, tokenUtils);
		
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        JSONObject requestVariable = new JSONObject();
        JSONObject variableValue = new JSONObject();
        JSONObject requestVariables = new JSONObject();
        
        try {
        	if(korisnik == null) {
                variableValue.put("value", "undefinedUser");
            }else {
            	variableValue.put("value", korisnik.getId().toString());
            }
            
            variableValue.put("type", "String");
            requestVariables.put("autorRada", variableValue);
            requestVariable.put("variables", requestVariables);
        }catch(Exception e) {
        	new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		
		HttpEntity<String> entity = new HttpEntity<String>(requestVariable.toString(), headers);
		restTemplate.postForEntity(processEngineRootPath+"process-definition/key/ObjavaRadaProcess/start", entity, JSONObject.class);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "dobaviFormuTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> dobaviFormuTask(@RequestParam (value="taskId", required = true) String taskId) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<?> response = restTemplate.getForEntity(processEngineRootPath+"task/"+taskId+"/form-variables", String.class);
		
		JSONObject theForm = null;
		try {
			theForm = new JSONObject(response.getBody().toString());
		} catch (JSONException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		FormFieldsDTO retVal = new FormFieldsDTO(taskId, new ArrayList<>(), null);
		
		Iterator<String> keys = theForm.keys();

		try {
			while(keys.hasNext()) {
			    String key = keys.next();
				if (theForm.get(key) instanceof JSONObject) {
					JSONObject theKey = (JSONObject) theForm.get(key);
					String type = theKey.getString("type");
					FormFieldDTO field = new FormFieldDTO(key, type);
					retVal.getFormFields().add(field);
				}
			}
		} catch (JSONException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<FormFieldsDTO>(retVal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "registrujTask", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registrujTask(@RequestBody @Valid RegistracijaDTO params, BindingResult result) {
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        JSONObject requestVariable = new JSONObject();
        
        try {
        	
        	JSONObject variables = new JSONObject();
        	variables.put("ime", this.buildObject(params.getIme()));
        	variables.put("prezime", this.buildObject(params.getPrezime()));
        	variables.put("password", this.buildObject(params.getLozinka()));
        	variables.put("drzava", this.buildObject(params.getDrzava()));
        	variables.put("grad",  this.buildObject(params.getGrad()));
        	variables.put("username", this.buildObject(params.getEmail()));
        	
        	requestVariable.put("variables", variables);
		} catch (JSONException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
        
        HttpEntity<String> entity = new HttpEntity<String>(requestVariable.toString(), headers);
        restTemplate.postForEntity(processEngineRootPath+"task/"+params.getTaskId()+"/complete", entity, String.class);
        
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	private JSONObject buildObject(String newValue) throws JSONException {
        JSONObject objValue = new JSONObject();
        objValue.put("value", newValue);
        return objValue;
	}

}
