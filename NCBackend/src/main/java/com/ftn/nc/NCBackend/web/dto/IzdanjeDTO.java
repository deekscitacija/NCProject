package com.ftn.nc.NCBackend.web.dto;

import java.util.Date;
import java.util.List;

import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;

public class IzdanjeDTO {
	
	private Long id;
	
	private Date objavljen;
	
	private String casopis;
	
	private String naslov;
	
	private Double cenaIzdanja;
	
	private List<NaucnaOblast> naucneOblasti;
	
	private List<RadDTO> radovi;
	
	private boolean openAccess;

	public IzdanjeDTO() {
		super();
	}

	public IzdanjeDTO(Long id, Date objavljen, String casopis, String naslov, Double cenaIzdanja,
			List<NaucnaOblast> naucneOblasti, List<RadDTO> radovi) {
		super();
		this.id = id;
		this.objavljen = objavljen;
		this.casopis = casopis;
		this.naslov = naslov;
		this.cenaIzdanja = cenaIzdanja;
		this.naucneOblasti = naucneOblasti;
		this.radovi = radovi;
	}
	
	public IzdanjeDTO(Izdanje izdanje, List<RadDTO> radovi) {
		super();
		this.id = izdanje.getId();
		this.objavljen = izdanje.getObjavljen();
		this.casopis = izdanje.getCasopis().getNaziv();
		this.naslov = izdanje.getNaslov();
		this.cenaIzdanja = izdanje.getCenaIzdanja();
		this.naucneOblasti = izdanje.getNaucneOblasti();
		this.radovi = radovi;
		this.openAccess = izdanje.getCasopis().isOpenAccess();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getObjavljen() {
		return objavljen;
	}

	public void setObjavljen(Date objavljen) {
		this.objavljen = objavljen;
	}

	public String getCasopis() {
		return casopis;
	}

	public void setCasopis(String casopis) {
		this.casopis = casopis;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public Double getCenaIzdanja() {
		return cenaIzdanja;
	}

	public void setCenaIzdanja(Double cenaIzdanja) {
		this.cenaIzdanja = cenaIzdanja;
	}

	public List<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public List<RadDTO> getRadovi() {
		return radovi;
	}

	public void setRadovi(List<RadDTO> radovi) {
		this.radovi = radovi;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}
	
}
