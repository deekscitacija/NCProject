package com.ftn.nc.NCBackend.camunda.dto;

public class VariablesDTO {

	private String casopisId;
	
	private String revizijaId;

	public VariablesDTO() {
		super();
	}

	public VariablesDTO(String casopisId, String revizijaId) {
		super();
		this.casopisId = casopisId;
		this.revizijaId = revizijaId;
	}

	public String getCasopisId() {
		return casopisId;
	}

	public void setCasopisId(String casopisId) {
		this.casopisId = casopisId;
	}

	public String getRevizijaId() {
		return revizijaId;
	}

	public void setRevizijaId(String revizijaId) {
		this.revizijaId = revizijaId;
	}
	
}
