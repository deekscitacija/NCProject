package com.ftn.nc.NCBackend.elastic.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.ftn.nc.NCBackend.web.model.Korisnik;

@Document(indexName = "naucnacentrala", type = "recenzent")
public class RecenzentInfo {
	
	@Id
	@Field(type = FieldType.text)
	private String id;
	
	@Field(type = FieldType.text)
	private String ime;
	
	@Field(type = FieldType.text)
	private String prezime;
	
	@Field(type = FieldType.text)
	private String mesto;
	
	@Field(type = FieldType.Nested)
	private List<CasopisInfo> casopisi;
	
	@GeoPointField
	private GeoPoint lokacija;

	public RecenzentInfo() {
		super();
	}

	public RecenzentInfo(String id, String ime, String prezime, String mesto, GeoPoint lokacija) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mesto = mesto;
		this.lokacija = lokacija;
	}
	
	public RecenzentInfo(Korisnik korisnik, List<CasopisInfo> casopisi) {
		super();
		this.id = korisnik.getId().toString();
		this.ime = korisnik.getIme();
		this.prezime = korisnik.getPrezime();
		this.mesto = korisnik.getGrad().getNaziv();
		this.lokacija = new GeoPoint(korisnik.getGrad().getLat(), korisnik.getGrad().getLon());
		this.casopisi = casopisi;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public GeoPoint getLokacija() {
		return lokacija;
	}

	public void setLokacija(GeoPoint lokacija) {
		this.lokacija = lokacija;
	}

	public List<CasopisInfo> getCasopisi() {
		return casopisi;
	}

	public void setCasopisi(List<CasopisInfo> casopisi) {
		this.casopisi = casopisi;
	}
	
}
