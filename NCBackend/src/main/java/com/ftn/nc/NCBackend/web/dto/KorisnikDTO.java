package com.ftn.nc.NCBackend.web.dto;

import java.util.Set;

import com.ftn.nc.NCBackend.web.model.Grad;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.TipKorisnika;

public class KorisnikDTO {
	
	private String ime;
	private String prezime;
	private String email;
	private Set<TipKorisnika> tip;
	private Grad grad;
	
	public KorisnikDTO() {
		super();
	}

	public KorisnikDTO(Korisnik korisnik) {
		super();
		this.ime = korisnik.getIme();
		this.prezime = korisnik.getPrezime();
		this.email = korisnik.getEmail();
		this.tip = korisnik.getTip();
		this.grad = korisnik.getGrad();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<TipKorisnika> getTip() {
		return tip;
	}

	public void setTip(Set<TipKorisnika> tip) {
		this.tip = tip;
	}

	public Grad getGrad() {
		return grad;
	}

	public void setGrad(Grad grad) {
		this.grad = grad;
	}
	
}
