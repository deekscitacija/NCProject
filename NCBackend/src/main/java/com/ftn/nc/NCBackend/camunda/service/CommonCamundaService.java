package com.ftn.nc.NCBackend.camunda.service;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ftn.nc.NCBackend.camunda.dto.RecenzentDTO;
import com.ftn.nc.NCBackend.camunda.dto.TaskDTO;
import com.ftn.nc.NCBackend.camunda.dto.VariablesDTO;

@Service
public class CommonCamundaService {
	
	@Value("${process-engine.roothPath}")
	private String processEngineRootPath;

	public ResponseEntity<String> getTasksByProcessInstanceIdAndAssignee(String assignee, String processInstanceId){
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(processEngineRootPath + "task")
		        .queryParam("assignee", assignee)
		        .queryParam("processInstanceId", processInstanceId);
		       
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
		return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
	}
	
	public ResponseEntity<List<TaskDTO>> getTasksByAssignee(String assignee){
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(processEngineRootPath + "task")
		        .queryParam("assignee", assignee);
		       
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
		String tasksInfo = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class).getBody();
		
		JSONArray taskList = null;
		try {
			taskList = new JSONArray(tasksInfo);
			
			if(taskList.length() <= 0) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
			
			List<TaskDTO> retVal = new ArrayList<>();
			
			for(int i = 0; i<taskList.length(); i++) {
				JSONObject taskInfo = taskList.getJSONObject(i);
				String taskId = (String) taskInfo.get("id");
		        String processInstanceId = (String) taskInfo.get("processInstanceId");
		        String taskName = (String) taskInfo.get("name");
		        
		        retVal.add(new TaskDTO(taskId, processInstanceId, assignee, taskName));
			}
			
            return new ResponseEntity<List<TaskDTO>>(retVal, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<String> compliteTaskNoForm(String taskId){
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("{}", headers);
		
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
        return restTemplate.postForEntity(processEngineRootPath + "task/" + taskId + "/complete", entity, String.class);
	}
	
	public void setProcessVariable(String processInstanceId, String varName, String varValue, String varType) throws JSONException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		JSONObject varInfo = new JSONObject();
		varInfo.put("value", varValue);
		varInfo.put("type", varType);
		
		HttpEntity<String> entity = new HttpEntity<String>(varInfo.toString(), headers);
		
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
        restTemplate.exchange(processEngineRootPath + "process-instance/"+processInstanceId+"/variables/"+varName, HttpMethod.PUT, entity, String.class);
	}
	
	public ResponseEntity<VariablesDTO> getVariablesForProcess(String processInstanceId){
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
        String variablesInfoStr = restTemplate.exchange(processEngineRootPath + "process-instance/"+processInstanceId+"/variables", HttpMethod.GET, entity, String.class).getBody();
		
        JSONObject variablesInfo = null;
        VariablesDTO retVal = new VariablesDTO();    	
    	try {
			variablesInfo = new JSONObject(variablesInfoStr);
			System.out.println(variablesInfo);
		} catch (JSONException e1) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

        try {
        	JSONObject casopisVar = new JSONObject(variablesInfo.get("casopis").toString());
        	retVal.setCasopisId(casopisVar.getString("value"));
		} catch (JSONException e) {
			
		}
        
        try {
        	JSONObject revizijaVar = new JSONObject(variablesInfo.get("revizijaId").toString());
    		retVal.setRevizijaId(revizijaVar.getString("value"));
		} catch (JSONException e) {
			
		}
        
        return new ResponseEntity<VariablesDTO>(retVal, HttpStatus.OK);
	}
	
	public ResponseEntity<String> setRecenzentiVariableCollection(List<RecenzentDTO> recenzenti, String processId) throws JSONException{
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String listContent = "[";
		
		for(RecenzentDTO recenzent : recenzenti) {
			listContent += recenzent.getId().toString()+",";
		}
		
		listContent = listContent.substring(0, listContent.length()-1);
		listContent = listContent+="]";
		
		JSONObject listInfo = new JSONObject();
		listInfo.put("value", listContent);
		listInfo.put("type", "Object");
		
		HttpEntity<String> entity = new HttpEntity<String>(listInfo.toString(), headers);
		
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
        return restTemplate.exchange(processEngineRootPath + "process-instance/"+processId+"/variables/listaRecenzenata", HttpMethod.PUT, entity, String.class);
	}
}
