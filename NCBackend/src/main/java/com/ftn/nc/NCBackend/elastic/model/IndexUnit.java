package com.ftn.nc.NCBackend.elastic.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.ftn.nc.NCBackend.elastic.dto.IndexUnitDTO;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Koautor;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucniRad;

@Document(indexName = "naucnacentrala", type = "rad", shards = 1, replicas = 0)
public class IndexUnit {
	
	@Id
	@Field(type = FieldType.text)
	private String id;
	
	@Field(type = FieldType.text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String naslov;
	
	@Field(type = FieldType.text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String koautori;
	
	@Field(type = FieldType.text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String kljucne;
	
	@Field(type = FieldType.text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String apstrakt;
	
	@Field(type = FieldType.text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String autor;
	
	@Field(type = FieldType.text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String casopis;
	
	@Field(type = FieldType.text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
	private String tekst;
	
	@Field(type = FieldType.Boolean)
	private boolean openAccess;
	
	@Field(type = FieldType.Nested)
	private List<RecenzentInfo> recenzenti;
	
	@Field(type = FieldType.Nested)
	private NaucnaOblastInfo naucnaOblast;

	public IndexUnit() {
		super();
	}

	public IndexUnit(String id, String naslov, String koautori, String kljucne, String apstrakt, String autor,
			String casopis, String tekst, boolean openAccess, List<RecenzentInfo> recenzenti,
			NaucnaOblastInfo naucnaOblast) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.koautori = koautori;
		this.kljucne = kljucne;
		this.apstrakt = apstrakt;
		this.autor = autor;
		this.casopis = casopis;
		this.tekst = tekst;
		this.openAccess = openAccess;
		this.recenzenti = recenzenti;
		this.naucnaOblast = naucnaOblast;
	}
	
	public IndexUnit(String id, IndexUnitDTO paperData, String textContent, Korisnik korisnik, Casopis casopis, 
			List<RecenzentInfo> recenzenti, NaucnaOblastInfo naucnaOblast) {
		super();
		this.id = id;
		this.naslov = paperData.getNaslov();
		this.koautori = paperData.getKoautori();
		this.kljucne = paperData.getKljucne();
		this.apstrakt = paperData.getApstrakt();
		this.autor = korisnik.getIme()+" "+korisnik.getPrezime();
		this.casopis = casopis.getNaziv();
		this.tekst = textContent;
		this.openAccess = casopis.isOpenAccess();
		this.recenzenti = recenzenti;
		this.naucnaOblast = naucnaOblast;
	}
	
	public IndexUnit(NaucniRad rad, String autor, String tekst, 
			List<RecenzentInfo> recenzenti, NaucnaOblastInfo naucnaOblast) {
		super();
		this.id = rad.getId().toString();
		this.naslov = rad.getNaslov();
		String koautori = "";
		for(Koautor koautor : rad.getKoAutori()) {
			koautori+=koautor.getIme()+" "+koautor.getPrezime()+", "+koautor.getAdresa()+", "+koautor.getEmail();
		}
		this.koautori = koautori;
		this.kljucne = rad.getKljucneReci();
		this.apstrakt = rad.getApstrakt();
		this.autor = autor;
		this.casopis = rad.getRevizija().getCasopis().getNaziv();
		this.tekst = tekst;
		this.openAccess = rad.getRevizija().getCasopis().isOpenAccess();
		this.recenzenti = recenzenti;
		this.naucnaOblast = naucnaOblast;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getKoautori() {
		return koautori;
	}

	public void setKoautori(String koautori) {
		this.koautori = koautori;
	}

	public String getKljucne() {
		return kljucne;
	}

	public void setKljucne(String kljucne) {
		this.kljucne = kljucne;
	}

	public String getApstrakt() {
		return apstrakt;
	}

	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
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

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	public List<RecenzentInfo> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(List<RecenzentInfo> recenzenti) {
		this.recenzenti = recenzenti;
	}

	public NaucnaOblastInfo getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblastInfo naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

}
