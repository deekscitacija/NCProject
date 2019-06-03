package com.ftn.nc.NCBackend.camunda.service;

import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ftn.nc.NCBackend.camunda.dto.VariablesDTO;

@Service
public class CommonCamundaService {
	
	@Value("${process-engine.roothPath}")
	private String processEngineRootPath;

	public TaskDto getTasksByProcessInstanceIdAndAssignee(String assignee, String processInstanceId){
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(processEngineRootPath + "task")
		        .queryParam("assignee", assignee)
		        .queryParam("processInstanceId", processInstanceId);
		
		ParameterizedTypeReference<List<TaskDto>> returnTypeProcessList = new ParameterizedTypeReference<List<TaskDto>>() {};
		       
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
		
		return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, returnTypeProcessList).getBody().get(0);
	}
	
	public TaskDto getCurrentTaskByProcessInstanceId(String processInstanceId) throws JSONException{
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		UriComponentsBuilder builderProcess = UriComponentsBuilder.fromHttpUrl(processEngineRootPath + "process-instance")
		        .queryParam("superProcessInstance", processInstanceId);
		       
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
		
		ParameterizedTypeReference<List<ProcessInstanceDto>> returnTypeProcessList = new ParameterizedTypeReference<List<ProcessInstanceDto>>() {};
		
		List<ProcessInstanceDto> processesInfo = restTemplate.exchange(builderProcess.toUriString(), HttpMethod.GET, entity, returnTypeProcessList).getBody();
		
		UriComponentsBuilder builderTasks = UriComponentsBuilder.fromHttpUrl(processEngineRootPath + "task")
		        .queryParam("processInstanceId", processesInfo.get(0).getId());
		
		return restTemplate.exchange(builderTasks.toUriString(), HttpMethod.GET, entity, TaskDto.class).getBody();
	}
	
	public List<TaskDto> getTasksByAssignee(String assignee){
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(processEngineRootPath + "task")
		        .queryParam("assignee", assignee);
		       
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
		
		ParameterizedTypeReference<List<TaskDto>> returnTypeTaskList = new ParameterizedTypeReference<List<TaskDto>>() {};
		
		return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, returnTypeTaskList).getBody();
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
	
	public Long getAssigneeId(String taskId){
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
		TaskDto taskInfo = restTemplate.exchange(processEngineRootPath + "task/"+taskId, HttpMethod.GET, entity, TaskDto.class).getBody();
		
		try {
			return Long.parseLong(taskInfo.getAssignee());
		}catch(NumberFormatException e) {
			return null;
		}
		
	}
	
	
}
