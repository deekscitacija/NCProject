package com.ftn.nc.NCBackend.web.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ftn.nc.NCBackend.web.model.Koautor;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;

public class NaucniRadDTO {
	
	private String naslov;
	
	private List<Koautor> koautori;
	
	private String kljucne;
	
	private String apstrakt;
	
	private Long casopisId;
	
	private MultipartFile[] fajlovi;
	
	private NaucnaOblast naucnaOblast;
	
	private String procesId;

	public NaucniRadDTO() {
		super();
	}

	public NaucniRadDTO(String naslov, List<Koautor> koautori, String kljucne, String apstrakt, Long casopisId,
			MultipartFile[] fajlovi, NaucnaOblast naucnaOblast, String procesId) {
		super();
		this.naslov = naslov;
		this.koautori = koautori;
		this.kljucne = kljucne;
		this.apstrakt = apstrakt;
		this.casopisId = casopisId;
		this.fajlovi = fajlovi;
		this.naucnaOblast = naucnaOblast;
		this.procesId = procesId;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public List<Koautor> getKoautori() {
		return koautori;
	}

	public void setKoautori(List<Koautor> koautori) {
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

	public Long getCasopisId() {
		return casopisId;
	}

	public void setCasopisId(Long casopisId) {
		this.casopisId = casopisId;
	}

	public MultipartFile[] getFajlovi() {
		return fajlovi;
	}

	public void setFajlovi(MultipartFile[] fajlovi) {
		this.fajlovi = fajlovi;
	}

	public NaucnaOblast getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getProcesId() {
		return procesId;
	}

	public void setProcesId(String procesId) {
		this.procesId = procesId;
	}

	@Override
	public String toString() {
		return "NaucniRadDTO [naslov=" + naslov + ", koautori=" + koautori + ", kljucne=" + kljucne + ", apstrakt="
				+ apstrakt + ", casopisId=" + casopisId + ", fajlovi=" + Arrays.toString(fajlovi) + ", naucnaOblast="
				+ naucnaOblast + ", procesId=" + procesId + "]";
	}

}
