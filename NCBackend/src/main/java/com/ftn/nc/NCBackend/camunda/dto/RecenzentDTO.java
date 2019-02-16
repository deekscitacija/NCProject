package com.ftn.nc.NCBackend.camunda.dto;

import com.ftn.nc.NCBackend.web.model.Korisnik;

public class RecenzentDTO {
	
	public Long id;

	public String ime;
	
	public String prezime;
	
	public String mesto;

	public RecenzentDTO() {
		super();
	}

	public RecenzentDTO(Long id, String ime, String prezime, String mesto) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mesto = mesto;
	}
	
	public RecenzentDTO(Korisnik recenzent) {
		super();
		this.id = recenzent.getId();
		this.ime = recenzent.getIme();
		this.prezime = recenzent.getPrezime();
		this.mesto = recenzent.getGrad().getNaziv()+" "+recenzent.getGrad().getDrzava().getNaziv();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}
	
	
	
}
