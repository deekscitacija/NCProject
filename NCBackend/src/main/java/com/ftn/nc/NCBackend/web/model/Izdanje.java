package com.ftn.nc.NCBackend.web.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Izdanje {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date objavljen;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JsonBackReference
	private Casopis casopis;
	
	@Column(nullable = false)
	private String naslov;
	
	@Column(nullable = true)
	private Double cenaIzdanja;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<NaucnaOblast> naucneOblasti;
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<NaucniRad> radovi;

	public Izdanje() {
		super();
	}

	public Izdanje(Long id, Date objavljen, Casopis casopis, String naslov, Double cenaIzdanja,
			Set<NaucnaOblast> naucneOblasti, Set<NaucniRad> radovi) {
		super();
		this.id = id;
		this.objavljen = objavljen;
		this.casopis = casopis;
		this.naslov = naslov;
		this.cenaIzdanja = cenaIzdanja;
		this.naucneOblasti = naucneOblasti;
		this.radovi = radovi;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
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

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public Set<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public Set<NaucniRad> getRadovi() {
		return radovi;
	}

	public void setRadovi(Set<NaucniRad> radovi) {
		this.radovi = radovi;
	}

	public Double getCenaIzdanja() {
		return cenaIzdanja;
	}

	public void setCenaIzdanja(Double cenaIzdanja) {
		this.cenaIzdanja = cenaIzdanja;
	}
		
}
