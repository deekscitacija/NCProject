package com.ftn.nc.NCBackend.elastic.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "naucnacentrala", type = "rad", shards = 1, replicas = 0)
public class IndexUnit {
	
	@Id
	@Field(type = FieldType.text)
	private String id;
	
	@Field(type = FieldType.text)
	private String naslov;
	
	@Field(type = FieldType.text)
	private String koautori;
	
	@Field(type = FieldType.text)
	private String kljucniPojmovi;
	
	@Field(type = FieldType.text)
	private String apstrakt;
	
	@Field(type = FieldType.text)
	private String autor;
	
	@Field(type = FieldType.text)
	private String nazivCasopisa;
	
	@Field(type = FieldType.text)
	private String sadrzajRada;
	
	@Field(type = FieldType.Boolean)
	private boolean openAccess;
	
	@Field(type = FieldType.Nested)
	private List<RecenzentInfo> recenzenti;
	
	@Field(type = FieldType.Nested)
	private List<NaucnaOblastInfo> naucneOblasti;

	public IndexUnit() {
		super();
	}

	public IndexUnit(String id, String naslov, String koautori, String kljucniPojmovi, String apstrakt, String autor,
			String nazivCasopisa, String sadrzajRada, boolean openAccess, List<RecenzentInfo> recenzenti
			,List<NaucnaOblastInfo> naucneOblasti
			) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.koautori = koautori;
		this.kljucniPojmovi = kljucniPojmovi;
		this.apstrakt = apstrakt;
		this.autor = autor;
		this.nazivCasopisa = nazivCasopisa;
		this.sadrzajRada = sadrzajRada;
		this.openAccess = openAccess;
		this.recenzenti = recenzenti;
		this.naucneOblasti = naucneOblasti;
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

	public String getKljucniPojmovi() {
		return kljucniPojmovi;
	}

	public void setKljucniPojmovi(String kljucniPojmovi) {
		this.kljucniPojmovi = kljucniPojmovi;
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

	public String getNazivCasopisa() {
		return nazivCasopisa;
	}

	public void setNazivCasopisa(String nazivCasopisa) {
		this.nazivCasopisa = nazivCasopisa;
	}

	public String getSadrzajRada() {
		return sadrzajRada;
	}

	public void setSadrzajRada(String sadrzajRada) {
		this.sadrzajRada = sadrzajRada;
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

	
	public List<NaucnaOblastInfo> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<NaucnaOblastInfo> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}
	

}
