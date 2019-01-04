package com.ftn.nc.NCBackend.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Autor {
	
	@Id
	private Long id;
	
	@OneToMany
	@JsonBackReference
	private Set<RevizijaRada> revizije;
	
	@OneToMany
	@JsonBackReference
	private Set<NaucniRad> radovi;

	public Autor() {
		super();
	}

	public Autor(Long id, Set<RevizijaRada> revizije, Set<NaucniRad> radovi) {
		super();
		this.id = id;
		this.revizije = revizije;
		this.radovi = radovi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<RevizijaRada> getRevizije() {
		return revizije;
	}

	public void setRevizije(Set<RevizijaRada> revizije) {
		this.revizije = revizije;
	}

	public Set<NaucniRad> getRadovi() {
		return radovi;
	}

	public void setRadovi(Set<NaucniRad> radovi) {
		this.radovi = radovi;
	}
	
}
