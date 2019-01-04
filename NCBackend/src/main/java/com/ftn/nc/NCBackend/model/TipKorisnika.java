package com.ftn.nc.NCBackend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class TipKorisnika {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 2)
	private String kod;
	
	@Column(nullable = false, length = 120)
	private String naziv;
	
	@ManyToMany
	private Set<Permisija> permisije;
	
	public TipKorisnika() {
		super();
	}

	public TipKorisnika(Long id, String kod, String naziv, Set<Permisija> permisije) {
		super();
		this.id = id;
		this.kod = kod;
		this.naziv = naziv;
		this.permisije = permisije;
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

	public Set<Permisija> getPermisije() {
		return permisije;
	}

	public void setPermisije(Set<Permisija> permisije) {
		this.permisije = permisije;
	}
	

}
