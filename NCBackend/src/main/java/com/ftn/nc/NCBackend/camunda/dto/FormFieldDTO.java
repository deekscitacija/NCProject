package com.ftn.nc.NCBackend.camunda.dto;

public class FormFieldDTO {
	
	private String key;
	private String dataType;

	
	public FormFieldDTO() {
		super();
	}

	public FormFieldDTO(String key, String dataType) {
		super();
		this.key = key;
		this.dataType = dataType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
