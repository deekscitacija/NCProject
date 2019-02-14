package com.ftn.nc.NCBackend.web.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class NaucniRad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String naslov;
	
	@ManyToMany
	private List<Koautor> koAutori;
	
	@Column(nullable = true, length = 1024)
	private String apstrakt;
	
	@Column(nullable = true, length = 1024)
	private String kljucneReci;
	
	@Column(nullable = false, length = 1024)
	private String putanja;
	
	@Column(nullable = true)
	private Double cena;
	
	@Column(nullable = true, length = 10, unique = true)
	private String koncentratorKod;
	
	@ManyToOne
	private NaucnaOblast naucnaOblast;
	
	@OneToOne(optional = true)
	@JsonBackReference
	private RevizijaRada revizija;

	public NaucniRad() {
		super();
	}
	
	public NaucniRad(Long id, String naslov, List<Koautor> koAutori, String apstrakt, String kljucneReci, String putanja,
			Double cena, String koncentratorKod, NaucnaOblast naucnaOblast, RevizijaRada revizija) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.koAutori = koAutori;
		this.apstrakt = apstrakt;
		this.kljucneReci = kljucneReci;
		this.putanja = putanja;
		this.cena = cena;
		this.koncentratorKod = koncentratorKod;
		this.naucnaOblast = naucnaOblast;
		this.revizija = revizija;
	}

	public NaucniRad(RevizijaRada revizija, Double cena, String koncentratorKod) {
		super();
		this.revizija = revizija;
		this.naslov = revizija.getNaslov();
		this.koAutori = revizija.getKoAutori();
		this.apstrakt = revizija.getApstrakt();
		this.kljucneReci = revizija.getApstrakt();
		this.putanja = revizija.getPutanja();
		this.naucnaOblast = revizija.getNaucnaOblast();
		this.revizija = revizija;
		this.cena = cena;
		this.koncentratorKod = koncentratorKod;
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

	public List<Koautor> getKoAutori() {
		return koAutori;
	}

	public void setKoAutori(List<Koautor> koAutori) {
		this.koAutori = koAutori;
	}

	public String getApstrakt() {
		return apstrakt;
	}

	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
	}

	public String getKljucneReci() {
		return kljucneReci;
	}

	public void setKljucneReci(String kljucneReci) {
		this.kljucneReci = kljucneReci;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public RevizijaRada getRevizija() {
		return revizija;
	}

	public void setRevizija(RevizijaRada revizija) {
		this.revizija = revizija;
	}

	public NaucnaOblast getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	public String getKoncentratorKod() {
		return koncentratorKod;
	}

	public void setKoncentratorKod(String koncentratorKod) {
		this.koncentratorKod = koncentratorKod;
	}

}
