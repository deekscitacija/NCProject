package com.ftn.nc.NCBackend.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ftn.nc.NCBackend.web.enums.TransakcijaStatus;

@Entity
public class Transakcija {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransakcijaStatus status;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumFormiranja;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumIzvrsenja;
	
	@ManyToOne(optional = false)
	private Korisnik vrsiPlacanje;
	
	@Column(nullable = false)
	private boolean pretplata;
	
	@ManyToOne(optional = true)
	private Casopis casopis;
	
	@ManyToOne(optional = true)
	private Izdanje izdanje;
	
	@ManyToOne(optional = true)
	private NaucniRad naucniRad;
	
	@Column(nullable = false)
	private Double iznos;

	public Transakcija() {
		super();
	}

	public Transakcija(Long id, TransakcijaStatus status, Date datumFormiranja, Date datumIzvrsenja,
			Korisnik vrsiPlacanje, boolean pretplata, Casopis casopis, Izdanje izdanje, NaucniRad naucniRad,
			Double iznos) {
		super();
		this.id = id;
		this.status = status;
		this.datumFormiranja = datumFormiranja;
		this.datumIzvrsenja = datumIzvrsenja;
		this.vrsiPlacanje = vrsiPlacanje;
		this.pretplata = pretplata;
		this.casopis = casopis;
		this.izdanje = izdanje;
		this.naucniRad = naucniRad;
		this.iznos = iznos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Korisnik getVrsiPlacanje() {
		return vrsiPlacanje;
	}

	public void setVrsiPlacanje(Korisnik vrsiPlacanje) {
		this.vrsiPlacanje = vrsiPlacanje;
	}

	public boolean isPretplata() {
		return pretplata;
	}

	public void setPretplata(boolean pretplata) {
		this.pretplata = pretplata;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public Izdanje getIzdanje() {
		return izdanje;
	}

	public void setIzdanje(Izdanje izdanje) {
		this.izdanje = izdanje;
	}

	public NaucniRad getNaucniRad() {
		return naucniRad;
	}

	public void setNaucniRad(NaucniRad naucniRad) {
		this.naucniRad = naucniRad;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}
	
}
