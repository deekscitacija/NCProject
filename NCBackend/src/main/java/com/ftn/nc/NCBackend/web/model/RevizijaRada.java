package com.ftn.nc.NCBackend.web.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class RevizijaRada {
	
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
	
	@Column(nullable = false)
	private boolean temaOk;
	
	@Column(nullable = false)
	private boolean formatOk;
	
	@Column(nullable = false)
	private boolean prihvacen;
	
	@ManyToOne(optional = false)
	@JoinTable(name="AUTOR_REVIZIJE",
	    joinColumns=@JoinColumn(name="revizija_rada_id"),
	       inverseJoinColumns=@JoinColumn(name="autor_id"))
	private Autor autor;
	
	@ManyToOne(optional = false)
	private Casopis casopis;
	
	@ManyToOne
	private NaucnaOblast naucnaOblast;
	
	@ManyToMany
	private List<RevizijaRadaRecenzent> recenzentiRevizija;
	
	@OneToMany
	private List<Komentar> komentari;
	

	public RevizijaRada() {
		super();
	}
	
	public RevizijaRada(Long id, String naslov, List<Koautor> koAutori, String apstrakt, String kljucneReci,
			String putanja, boolean temaOk, boolean formatOk, boolean prihvacen, Autor autor, Casopis casopis,
			NaucnaOblast naucnaOblast, List<RevizijaRadaRecenzent> recenzentiRevizija, List<Komentar> komentari) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.koAutori = koAutori;
		this.apstrakt = apstrakt;
		this.kljucneReci = kljucneReci;
		this.putanja = putanja;
		this.temaOk = temaOk;
		this.formatOk = formatOk;
		this.prihvacen = prihvacen;
		this.autor = autor;
		this.casopis = casopis;
		this.naucnaOblast = naucnaOblast;
		this.recenzentiRevizija = recenzentiRevizija;
		this.komentari = komentari;
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

	public boolean isTemaOk() {
		return temaOk;
	}

	public void setTemaOk(boolean temaOk) {
		this.temaOk = temaOk;
	}

	public boolean isFormatOk() {
		return formatOk;
	}

	public void setFormatOk(boolean formatOk) {
		this.formatOk = formatOk;
	}

	public boolean isPrihvacen() {
		return prihvacen;
	}

	public void setPrihvacen(boolean prihvacen) {
		this.prihvacen = prihvacen;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	public List<RevizijaRadaRecenzent> getRecenzentiRevizija() {
		return recenzentiRevizija;
	}

	public void setRecenzentiRevizija(List<RevizijaRadaRecenzent> recenzentiRevizija) {
		this.recenzentiRevizija = recenzentiRevizija;
	}

	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public NaucnaOblast getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public List<Komentar> getKomentari() {
		return komentari;
	}

	public void setKomentari(List<Komentar> komentari) {
		this.komentari = komentari;
	}

}
