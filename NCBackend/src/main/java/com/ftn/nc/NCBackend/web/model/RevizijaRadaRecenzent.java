package com.ftn.nc.NCBackend.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ftn.nc.NCBackend.web.enums.RecenzijaStatus;

@Entity
public class RevizijaRadaRecenzent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Recenzent recenzent;
	
	@ManyToOne
	private RevizijaRada revizija;
	
	@Column(nullable = false)
	private boolean zavrseno;
	
	@Enumerated(EnumType.STRING)
	private RecenzijaStatus status;
	
	public RevizijaRadaRecenzent() {
		super();
	}

	public RevizijaRadaRecenzent(Long id, Recenzent recenzent, RevizijaRada revizija, boolean zavrseno,
			RecenzijaStatus status) {
		super();
		this.id = id;
		this.recenzent = recenzent;
		this.revizija = revizija;
		this.zavrseno = zavrseno;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recenzent getRecenzent() {
		return recenzent;
	}

	public void setRecenzent(Recenzent recenzent) {
		this.recenzent = recenzent;
	}

	public RevizijaRada getRevizija() {
		return revizija;
	}

	public void setRevizija(RevizijaRada revizija) {
		this.revizija = revizija;
	}

	public boolean isZavrseno() {
		return zavrseno;
	}

	public void setZavrseno(boolean zavrseno) {
		this.zavrseno = zavrseno;
	}

	public RecenzijaStatus getStatus() {
		return status;
	}

	public void setStatus(RecenzijaStatus status) {
		this.status = status;
	}

}
