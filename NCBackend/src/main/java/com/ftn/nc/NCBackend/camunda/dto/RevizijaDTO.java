package com.ftn.nc.NCBackend.camunda.dto;

import java.util.List;

import com.ftn.nc.NCBackend.web.dto.KomentarDTO;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;


public class RevizijaDTO {
	
	private Long id;
	
	private String naslov;
	
	private String apstrakt;
	
	private String kljucneReci;

	private boolean temaOk;
	
	private boolean formatOk;
	
	private boolean prihvacen;
	
	private String autor;
	
	private String casopis;
	
	private String casopisISSN;
	
	private String naucnaOblast;

	private String naucnaOblastKod;
	
	private String tekstKomentara;
	
	private List<KomentarDTO> komentari;
	
	public RevizijaDTO() {
		super();
	}

	public RevizijaDTO(RevizijaRada revizija, Korisnik autor, String tekstKomentara, List<KomentarDTO> komentari) {
		super();
		this.id = revizija.getId();
		this.naslov = revizija.getNaslov();
		this.apstrakt = revizija.getApstrakt();
		this.kljucneReci = revizija.getKljucneReci();
		this.temaOk = revizija.isTemaOk();
		this.formatOk = revizija.isFormatOk();
		this.prihvacen = revizija.isPrihvacen();
		this.autor = autor.getIme()+" "+autor.getPrezime();
		this.casopis = revizija.getCasopis().getNaziv();
		this.casopisISSN = revizija.getCasopis().getIssn();
		this.naucnaOblast = revizija.getNaucnaOblast().getNaziv();
		this.naucnaOblastKod = revizija.getNaucnaOblast().getKod();
		this.tekstKomentara = tekstKomentara;
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

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCasopis() {
		return casopis;
	}

	public void setCasopis(String casopis) {
		this.casopis = casopis;
	}

	public String getCasopisISSN() {
		return casopisISSN;
	}

	public void setCasopisISSN(String casopisISSN) {
		this.casopisISSN = casopisISSN;
	}

	public String getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(String naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getNaucnaOblastKod() {
		return naucnaOblastKod;
	}

	public void setNaucnaOblastKod(String naucnaOblastKod) {
		this.naucnaOblastKod = naucnaOblastKod;
	}

	public String getTekstKomentara() {
		return tekstKomentara;
	}

	public void setTekstKomentara(String tekstKomentara) {
		this.tekstKomentara = tekstKomentara;
	}

	public List<KomentarDTO> getKomentari() {
		return komentari;
	}

	public void setKomentari(List<KomentarDTO> komentari) {
		this.komentari = komentari;
	}
	
}
