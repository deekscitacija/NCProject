package com.ftn.nc.NCBackend.web.model;

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
public class RevizijaRada {
	
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
	
	@Column(nullable = false)
	private boolean temaOk;
	
	@Column(nullable = false)
	private boolean formatOk;
	
	@Column(nullable = false)
	private boolean prihvacen;
	
	@OneToOne(optional = false)
	private Autor autor;
	
	@ManyToMany
	private Set<NaucnaOblast> naucneOblasti;
	
	@ManyToMany
	private Set<Recenzent> recenzenti;
	
	@OneToMany
	private Set<Komentar> komentari;

	public RevizijaRada() {
		super();
	}

	public RevizijaRada(Long id, String naslov, String koAutori, String apstrakt, String kljucneReci, boolean temaOk,
			boolean formatOk, boolean prihvacen, Autor autor, Set<NaucnaOblast> naucneOblasti,
			Set<Recenzent> recenzenti, Set<Komentar> komentari) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.koAutori = koAutori;
		this.apstrakt = apstrakt;
		this.kljucneReci = kljucneReci;
		this.temaOk = temaOk;
		this.formatOk = formatOk;
		this.prihvacen = prihvacen;
		this.autor = autor;
		this.naucneOblasti = naucneOblasti;
		this.recenzenti = recenzenti;
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

	public Set<Recenzent> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(Set<Recenzent> recenzenti) {
		this.recenzenti = recenzenti;
	}

	public Set<Komentar> getKomentari() {
		return komentari;
	}

	public void setKomentari(Set<Komentar> komentari) {
		this.komentari = komentari;
	}

	public Set<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

}