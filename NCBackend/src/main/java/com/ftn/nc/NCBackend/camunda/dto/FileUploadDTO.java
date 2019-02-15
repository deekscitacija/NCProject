package com.ftn.nc.NCBackend.camunda.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDTO {

	private MultipartFile[] fajlovi;
	
	private Long revizijaId;
	
	private String taskId;
	
	private String processId;

	public FileUploadDTO() {
		super();
	}

	public FileUploadDTO(MultipartFile[] fajlovi, Long revizijaId, String taskId, String processId) {
		super();
		this.fajlovi = fajlovi;
		this.revizijaId = revizijaId;
		this.taskId = taskId;
		this.processId = processId;
	}

	public MultipartFile[] getFajlovi() {
		return fajlovi;
	}

	public void setFajlovi(MultipartFile[] fajlovi) {
		this.fajlovi = fajlovi;
	}

	public Long getRevizijaId() {
		return revizijaId;
	}

	public void setRevizijaId(Long revizijaId) {
		this.revizijaId = revizijaId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
}
