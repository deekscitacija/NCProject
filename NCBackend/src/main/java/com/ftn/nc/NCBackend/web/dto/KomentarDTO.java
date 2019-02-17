package com.ftn.nc.NCBackend.web.dto;

import com.ftn.nc.NCBackend.web.enums.KomentarVidljivost;

public class KomentarDTO {
	
	private String tekst;
	
	private KomentarVidljivost vidljivost;
	
	private String autor;

	public KomentarDTO() {
		super();
	}

	public KomentarDTO(String tekst, KomentarVidljivost vidljivost, String autor) {
		super();
		this.tekst = tekst;
		this.vidljivost = vidljivost;
		this.autor = autor;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public KomentarVidljivost getVidljivost() {
		return vidljivost;
	}

	public void setVidljivost(KomentarVidljivost vidljivost) {
		this.vidljivost = vidljivost;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

}
