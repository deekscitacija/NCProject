package com.ftn.nc.NCBackend.camunda.service;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
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
	
	public ResponseEntity<String> compliteTaskNoForm(String taskId){
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>("{}", headers);
		
		HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
        return restTemplate.postForEntity(processEngineRootPath + "task/" + taskId + "/complete", entity, String.class);
	}
	
}
