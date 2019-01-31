package com.ftn.nc.NCBackend.elastic.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ftn.nc.NCBackend.web.model.NaucnaOblast;


public class IndexUnitDTO {

	private String naslov;
	
	private String koautori;
	
	private String kljucne;
	
	private String apstrakt;
	
	private MultipartFile[] fajlovi;
	
	private List<NaucnaOblast> naucneOblasti;

	public IndexUnitDTO() {
		super();
	}

	public IndexUnitDTO(String naslov, String koautori, String kljucne, String apstrakt, MultipartFile[] fajlovi,
			List<NaucnaOblast> naucneOblasti) {
		super();
		this.naslov = naslov;
		this.koautori = koautori;
		this.kljucne = kljucne;
		this.apstrakt = apstrakt;
		this.fajlovi = fajlovi;
		this.naucneOblasti = naucneOblasti;
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

	public MultipartFile[] getFajlovi() {
		return fajlovi;
	}

	public void setFajlovi(MultipartFile[] fajlovi) {
		this.fajlovi = fajlovi;
	}

	public List<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	@Override
	public String toString() {
		return "IndexUnitDTO [naslov=" + naslov + ", koautori=" + koautori + ", kljucne=" + kljucne + ", apstrakt="
				+ apstrakt + ", fajlovi=" + Arrays.toString(fajlovi) + ", naucneOblasti=" + naucneOblasti + "]";
	}
	
	
	
}
