package com.ftn.nc.NCBackend.web.dto;

import java.util.Set;

import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.NaucniRad;

public class RadDTO {
	
	private Long id;
	
	private String naslov;
	
	private String apstrakt;
	
	private double cena;
	
	private Set<NaucnaOblast> naucneOblasti;
	
	private String autor;
	
	private boolean openAccess;

	public RadDTO() {
		super();
	}

	public RadDTO(Long id, String naslov, String apstrakt, double cena, Set<NaucnaOblast> naucneOblasti, String autor) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.apstrakt = apstrakt;
		this.cena = cena;
		this.naucneOblasti = naucneOblasti;
		this.autor = autor;
	}

	public RadDTO(NaucniRad rad, Korisnik autor) {
		super();
		this.id = rad.getId();
		this.naslov = rad.getNaslov();
		this.apstrakt = rad.getApstrakt();
		this.cena = rad.getCena();
		this.naucneOblasti = rad.getNaucneOblasti();
		this.autor = autor.getIme()+" "+autor.getPrezime();
		this.openAccess = rad.getRevizija().getCasopis().isOpenAccess();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getApstrakt() {
		return apstrakt;
	}

	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Set<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

}
