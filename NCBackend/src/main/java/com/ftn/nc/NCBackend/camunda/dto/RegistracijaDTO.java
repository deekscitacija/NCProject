package com.ftn.nc.NCBackend.camunda.dto;

import javax.validation.constraints.NotNull;

public class RegistracijaDTO {
	
	@NotNull
	private String ime;
	
	@NotNull
	private String prezime;
	
	@NotNull
	private String lozinka;
	
	@NotNull
	private String drzava;
	
	@NotNull
	private String grad;
	
	@NotNull
	private String email;
	
	@NotNull
	private String taskId;
	
	@NotNull
	private String processId;
	
	public RegistracijaDTO() {
		super();
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
