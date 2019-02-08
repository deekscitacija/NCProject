package com.ftn.nc.NCBackend.web.dto;

import java.util.Date;

import com.ftn.nc.NCBackend.web.enums.TransakcijaStatus;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.Transakcija;

public class TransakcijaDTO {
	
	private TransakcijaStatus status;
	
	private Date datumFormiranja;
	
	private Date datumIzvrsenja;
	
	private boolean pretplata;
	
	private String entitet;
	
	private String naziv;
	
	private String putanja;
	
	private Double iznos;

	public TransakcijaDTO() {
		super();
	}
	
	public TransakcijaDTO(TransakcijaStatus status, Date datumFormiranja, Date datumIzvrsenja, boolean pretplata,
			String entitet, String naziv, String putanja, Double iznos) {
		super();
		this.status = status;
		this.datumFormiranja = datumFormiranja;
		this.datumIzvrsenja = datumIzvrsenja;
		this.pretplata = pretplata;
		this.entitet = entitet;
		this.naziv = naziv;
		this.putanja = putanja;
		this.iznos = iznos;
	}



	public TransakcijaDTO(Transakcija transakcija) {
		super();
		this.status = transakcija.getStatus();
		this.datumFormiranja = transakcija.getDatumFormiranja();
		this.datumIzvrsenja = transakcija.getDatumIzvrsenja();
		this.pretplata = transakcija.isPretplata();
		
		if(transakcija.getCasopis() != null) {
			Casopis casopis = transakcija.getCasopis();
			this.entitet = "Casopis";
			this.putanja = "http://localhost:4200/naucna-centrala.com/casopis/"+casopis.getId();
			this.naziv = casopis.getNaziv();
		}else if(transakcija.getIzdanje() != null){
			Izdanje izdanje = transakcija.getIzdanje();
			this.entitet = "Izdanje";
			this.putanja = "http://localhost:4200/naucna-centrala.com/izdanje/"+izdanje.getId();
			this.naziv = izdanje.getNaslov();
		}else if(transakcija.getNaucniRad() != null) {
			NaucniRad rad = transakcija.getNaucniRad();
			this.entitet = "Rad";
			this.putanja = "https://localhost:8077/app/download?paperId="+rad.getId();
			this.naziv = rad.getNaslov();
		}
	
		this.iznos = transakcija.getIznos();	
	}

	public TransakcijaStatus getStatus() {
		return status;
	}

	public void setStatus(TransakcijaStatus status) {
		this.status = status;
	}

	public Date getDatumFormiranja() {
		return datumFormiranja;
	}

	public void setDatumFormiranja(Date datumFormiranja) {
		this.datumFormiranja = datumFormiranja;
	}

	public Date getDatumIzvrsenja() {
		return datumIzvrsenja;
	}

	public void setDatumIzvrsenja(Date datumIzvrsenja) {
		this.datumIzvrsenja = datumIzvrsenja;
	}

	public boolean isPretplata() {
		return pretplata;
	}

	public void setPretplata(boolean pretplata) {
		this.pretplata = pretplata;
	}

	public String getEntitet() {
		return entitet;
	}

	public void setEntitet(String entitet) {
		this.entitet = entitet;
	}

	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
}
