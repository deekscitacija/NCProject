package com.ftn.nc.NCBackend.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Grad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String naziv;
	
	@ManyToOne(optional = false)
	private Drzava drzava;
	
	@Column(nullable = false)
	private double lat;
	
	@Column(nullable = false)
	private double lon;

	public Grad() {

	}

	public Grad(Long id, String naziv, Drzava drzava, double lat, double lon) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.drzava = drzava;
		this.lat = lat;
		this.lon = lon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Drzava getDrzava() {
		return drzava;
	}

	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

}
