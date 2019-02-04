package com.ftn.nc.NCBackend.web.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class NaucniRad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String naslov;
	
	@Column(nullable = true, length = 1024)
	private String koAutori;
	
	@Column(nullable = true, length = 1024)
	private String apstrakt;
	
	@Column(nullable = true, length = 1024)
	private String kljucneReci;
	
	@Column(nullable = false, length = 1024)
	private String putanja;
	
	@OneToMany
	private List<Cena> cena;
	
	@ManyToMany
	private Set<NaucnaOblast> naucneOblasti;
	
	@OneToOne(optional = true)
	private RevizijaRada revizija;

	public NaucniRad() {
		super();
	}

	public NaucniRad(Long id, String naslov, String koAutori, String apstrakt, String kljucneReci, String putanja,
			List<Cena> cena, Set<NaucnaOblast> naucneOblasti, RevizijaRada revizija) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.koAutori = koAutori;
		this.apstrakt = apstrakt;
		this.kljucneReci = kljucneReci;
		this.putanja = putanja;
		this.cena = cena;
		this.naucneOblasti = naucneOblasti;
		this.revizija = revizija;
	}
	

	public NaucniRad(RevizijaRada revizija) {
		super();
		this.revizija = revizija;
		this.naslov = revizija.getNaslov();
		this.koAutori = revizija.getKoAutori();
		this.apstrakt = revizija.getApstrakt();
		this.kljucneReci = revizija.getApstrakt();
		this.putanja = revizija.getPutanja();
		this.naucneOblasti = revizija.getNaucneOblasti();
		this.revizija = revizija;
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

	public String getKoAutori() {
		return koAutori;
	}

	public void setKoAutori(String koAutori) {
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

	public List<Cena> getCena() {
		return cena;
	}

	public void setCena(List<Cena> cena) {
		this.cena = cena;
	}

	public RevizijaRada getRevizija() {
		return revizija;
	}

	public void setRevizija(RevizijaRada revizija) {
		this.revizija = revizija;
	}

	public Set<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

}
