package com.ftn.nc.NCBackend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Casopis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String issn;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private boolean openAccess;
	
	@ManyToMany
	private Set<NaucnaOblast> naucneOblasti;
	
	@ManyToMany
	private Set<Cena> cenePretplate;
	
	@ManyToMany
	private Set<Cena> ceneClanarine;
	
	@ManyToOne(optional = false)
	private Urednik urednik;
	
	@OneToMany
	private Set<Urednik> uredjivackiOdbor;

	public Casopis() {
		super();
	}

	public Casopis(Long id, String issn, String naziv, boolean openAccess, Set<NaucnaOblast> naucneOblasti,
			Set<Cena> cenePretplate, Set<Cena> ceneClanarine, Urednik urednik, Set<Urednik> uredjivackiOdbor) {
		super();
		this.id = id;
		this.issn = issn;
		this.naziv = naziv;
		this.openAccess = openAccess;
		this.naucneOblasti = naucneOblasti;
		this.cenePretplate = cenePretplate;
		this.ceneClanarine = ceneClanarine;
		this.urednik = urednik;
		this.uredjivackiOdbor = uredjivackiOdbor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	public Set<Cena> getCenePretplate() {
		return cenePretplate;
	}

	public void setCenePretplate(Set<Cena> cenePretplate) {
		this.cenePretplate = cenePretplate;
	}

	public Set<Cena> getCeneClanarine() {
		return ceneClanarine;
	}

	public void setCeneClanarine(Set<Cena> ceneClanarine) {
		this.ceneClanarine = ceneClanarine;
	}

	public Set<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Urednik getUrednik() {
		return urednik;
	}

	public void setUrednik(Urednik urednik) {
		this.urednik = urednik;
	}

	public Set<Urednik> getUredjivackiOdbor() {
		return uredjivackiOdbor;
	}

	public void setUredjivackiOdbor(Set<Urednik> uredjivackiOdbor) {
		this.uredjivackiOdbor = uredjivackiOdbor;
	}

}
