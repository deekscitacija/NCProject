package com.ftn.nc.NCBackend.elastic.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ftn.nc.NCBackend.web.dto.KorisnikDTO;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;


public class IndexUnitDTO {

	private String naslov;
	
	private String koautori;
	
	private String kljucne;
	
	private String apstrakt;
	
	private Long casopisId;
	
	private MultipartFile[] fajlovi;
	
	private List<NaucnaOblast> naucneOblasti;
	
	private List<KorisnikDTO> recenzenti;

	public IndexUnitDTO() {
		super();
	}

	public IndexUnitDTO(String naslov, String koautori, String kljucne, String apstrakt, Long casopisId,
			MultipartFile[] fajlovi, List<NaucnaOblast> naucneOblasti, List<KorisnikDTO> recenzenti) {
		super();
		this.naslov = naslov;
		this.koautori = koautori;
		this.kljucne = kljucne;
		this.apstrakt = apstrakt;
		this.casopisId = casopisId;
		this.fajlovi = fajlovi;
		this.naucneOblasti = naucneOblasti;
		this.recenzenti = recenzenti;
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

	public List<KorisnikDTO> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(List<KorisnikDTO> recenzenti) {
		this.recenzenti = recenzenti;
	}

	public Long getCasopisId() {
		return casopisId;
	}

	public void setCasopisId(Long casopisId) {
		this.casopisId = casopisId;
	}

	@Override
	public String toString() {
		return "IndexUnitDTO [naslov=" + naslov + ", koautori=" + koautori + ", kljucne=" + kljucne + ", apstrakt="
				+ apstrakt + ", casopisId=" + casopisId + ", fajlovi=" + Arrays.toString(fajlovi) + ", naucneOblasti="
				+ naucneOblasti + ", recenzenti=" + recenzenti + "]";
	}
	
}
