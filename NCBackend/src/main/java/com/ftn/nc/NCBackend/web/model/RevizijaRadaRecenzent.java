package com.ftn.nc.NCBackend.web.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	public RevizijaRadaRecenzent() {
		super();
	}

	public RevizijaRadaRecenzent(Long id, Recenzent recenzent, RevizijaRada revizija, boolean zavrseno) {
		super();
		this.id = id;
		this.recenzent = recenzent;
		this.revizija = revizija;
		this.zavrseno = zavrseno;
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

}
