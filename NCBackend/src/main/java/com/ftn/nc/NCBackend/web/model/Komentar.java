package com.ftn.nc.NCBackend.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ftn.nc.NCBackend.web.enums.KomentarVidljivost;

@Entity
public class Komentar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	private Korisnik posiljalac;
	
	@ManyToOne(optional = false)
	private Korisnik primalac;
	
	@Column(nullable = false, length = 1024)
	private String tekst;
	
	@Column(nullable = false)
	private boolean procitan;
	
	@Enumerated(EnumType.STRING)
	private KomentarVidljivost vidljivost;

	public Komentar() {
		super();
	}

	public Komentar(Long id, Korisnik posiljalac, Korisnik primalac, String tekst, boolean procitan,
			KomentarVidljivost vidljivost) {
		super();
		this.id = id;
		this.posiljalac = posiljalac;
		this.primalac = primalac;
		this.tekst = tekst;
		this.procitan = procitan;
		this.vidljivost = vidljivost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Korisnik getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(Korisnik posiljalac) {
		this.posiljalac = posiljalac;
	}

	public Korisnik getPrimalac() {
		return primalac;
	}

	public void setPrimalac(Korisnik primalac) {
		this.primalac = primalac;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public boolean isProcitan() {
		return procitan;
	}

	public void setProcitan(boolean procitan) {
		this.procitan = procitan;
	}

	public KomentarVidljivost getVidljivost() {
		return vidljivost;
	}

	public void setVidljivost(KomentarVidljivost vidljivost) {
		this.vidljivost = vidljivost;
	}
	

}
