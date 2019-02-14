package com.ftn.nc.NCBackend.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pretplata {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Autor autor;
	
	@ManyToOne(optional = false)
	private Casopis casopis;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumOd;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datumDo;
	
	public Pretplata() {
		super();
	}

	public Pretplata(Autor autor, Casopis casopis, Date datumOd, Date datumDo) {
		super();
		this.autor = autor;
		this.casopis = casopis;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public Date getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(Date datumOd) {
		this.datumOd = datumOd;
	}

	public Date getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(Date datumDo) {
		this.datumDo = datumDo;
	}
	
}
