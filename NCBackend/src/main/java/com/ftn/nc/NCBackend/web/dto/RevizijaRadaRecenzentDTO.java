package com.ftn.nc.NCBackend.web.dto;

import com.ftn.nc.NCBackend.web.enums.RecenzijaStatus;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;

public class RevizijaRadaRecenzentDTO {
	
	private String recenzent;
	
	private boolean zavrseno;
	
	private RecenzijaStatus status;

	public RevizijaRadaRecenzentDTO() {
		super();
	}

	public RevizijaRadaRecenzentDTO(String recenzent, boolean zavrseno, RecenzijaStatus status) {
		super();
		this.recenzent = recenzent;
		this.zavrseno = zavrseno;
		this.status = status;
	}
	
	public RevizijaRadaRecenzentDTO(RevizijaRadaRecenzent revizijaInfo, Korisnik recenzent) {
		super();
		this.recenzent = recenzent.getIme()+" "+recenzent.getPrezime();
		this.zavrseno = revizijaInfo.isZavrseno();
		this.status = revizijaInfo.getStatus();
	}

	public String getRecenzent() {
		return recenzent;
	}

	public void setRecenzent(String recenzent) {
		this.recenzent = recenzent;
	}

	public boolean isZavrseno() {
		return zavrseno;
	}

	public void setZavrseno(boolean zavrseno) {
		this.zavrseno = zavrseno;
	}

	public RecenzijaStatus getStatus() {
		return status;
	}

	public void setStatus(RecenzijaStatus status) {
		this.status = status;
	}

}
