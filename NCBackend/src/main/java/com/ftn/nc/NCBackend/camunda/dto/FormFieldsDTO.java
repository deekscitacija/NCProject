package com.ftn.nc.NCBackend.camunda.dto;

import java.util.List;

public class FormFieldsDTO {
	
	private String taskId;
	private List<FormFieldDTO> formFields;
	private String processInstanceId;
	
	public FormFieldsDTO() {
		super();
	}

	public FormFieldsDTO(String taskId, List<FormFieldDTO> formFields, String processInstanceId) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<FormFieldDTO> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<FormFieldDTO> formFields) {
		this.formFields = formFields;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
}
