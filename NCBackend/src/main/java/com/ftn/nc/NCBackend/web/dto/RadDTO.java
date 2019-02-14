package com.ftn.nc.NCBackend.web.dto;

import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.NaucniRad;

public class RadDTO {
	
	private Long id;
	
	private String naslov;
	
	private String apstrakt;
	
	private Double cena;
	
	private NaucnaOblast naucnaOblast;
	
	private String autor;
	
	private boolean openAccess;

	public RadDTO() {
		super();
	}

	public RadDTO(Long id, String naslov, String apstrakt, Double cena, NaucnaOblast naucnaOblast, String autor,
			boolean openAccess) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.apstrakt = apstrakt;
		this.cena = cena;
		this.naucnaOblast = naucnaOblast;
		this.autor = autor;
		this.openAccess = openAccess;
	}

	public RadDTO(NaucniRad rad, Korisnik autor) {
		super();
		this.id = rad.getId();
		this.naslov = rad.getNaslov();
		this.apstrakt = rad.getApstrakt();
		this.cena = rad.getCena();
		this.naucnaOblast = rad.getNaucnaOblast();
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

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public NaucnaOblast getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
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
