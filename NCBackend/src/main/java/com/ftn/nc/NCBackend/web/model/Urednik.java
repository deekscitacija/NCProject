package com.ftn.nc.NCBackend.web.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Urednik {

	@Id
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String titula;
	
	@ManyToMany
	private Set<NaucnaOblast> naucneOblasti;
	
	@OneToMany
	private Set<Casopis> uredjujeCasopise;

	public Urednik() {
		super();
	}
	
	public Urednik(Long id, String titula, Set<NaucnaOblast> naucneOblasti, Set<Casopis> uredjujeCasopise) {
		super();
		this.id = id;
		this.titula = titula;
		this.naucneOblasti = naucneOblasti;
		this.uredjujeCasopise = uredjujeCasopise;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitula() {
		return titula;
	}

	public void setTitula(String titula) {
		this.titula = titula;
	}

	public Set<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public Set<Casopis> getUredjujeCasopise() {
		return uredjujeCasopise;
	}

	public void setUredjujeCasopise(Set<Casopis> uredjujeCasopise) {
		this.uredjujeCasopise = uredjujeCasopise;
	}
	
}
