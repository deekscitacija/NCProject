package com.ftn.nc.NCBackend.web.dto;

import java.util.Iterator;
import java.util.Set;

import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Cena;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;

public class CasopisDTO {

	private Long id;	
	private String issn;
	private String naziv;
	private boolean openAccess;
	private Set<NaucnaOblast> naucneOblasti;
	private Cena cenaPretplate;
	private Cena cenaClanarine;
	private String urednik;
	
	public CasopisDTO() {
		super();
	}
	
	public CasopisDTO(Casopis casopis, Korisnik korisnik) {
		super();
		this.id = casopis.getId();
		this.issn = casopis.getIssn();
		this.naziv = casopis.getNaziv();
		this.openAccess = casopis.isOpenAccess();
		this.naucneOblasti = casopis.getNaucneOblasti();
		
		Set<Cena> clanarine = casopis.getCeneClanarine();
		if(clanarine != null && !clanarine.isEmpty()) {
			Iterator<Cena> clanarina = casopis.getCeneClanarine().iterator();
			this.cenaClanarine = clanarina.next();
		}
		
		Set<Cena> pretplate = casopis.getCenePretplate();
		if(pretplate != null && !pretplate.isEmpty()) {
			Iterator<Cena> pretplata = casopis.getCenePretplate().iterator();
			this.cenaClanarine = pretplata.next();
		}
		
		this.urednik = korisnik.getIme()+" "+korisnik.getPrezime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	public Set<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public Cena getCenaPretplate() {
		return cenaPretplate;
	}

	public void setCenaPretplate(Cena cenaPretplate) {
		this.cenaPretplate = cenaPretplate;
	}

	public Cena getCenaClanarine() {
		return cenaClanarine;
	}

	public void setCenaClanarine(Cena cenaClanarine) {
		this.cenaClanarine = cenaClanarine;
	}

	public String getUrednik() {
		return urednik;
	}

	public void setUrednik(String urednik) {
		this.urednik = urednik;
	}
	
}
