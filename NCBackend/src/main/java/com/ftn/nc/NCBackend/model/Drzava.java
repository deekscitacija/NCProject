package com.ftn.nc.NCBackend.model;

import javax.persistence.*;

@Entity
public class Drzava {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 3)
	private String kod;
	
	@Column(nullable = false, length = 120)
	private String naziv;

	public Drzava() {
		
	}
	
	public Drzava(Long id, String kod, String naziv) {
		super();
		this.id = id;
		this.kod = kod;
		this.naziv = naziv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKod() {
		return kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

}
