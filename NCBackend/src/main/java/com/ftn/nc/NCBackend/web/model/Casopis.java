package com.ftn.nc.NCBackend.web.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Casopis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 9)
	private String issn;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private boolean openAccess;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<NaucnaOblast> naucneOblasti;
	
	@Column(nullable = true)
	private Double cenaPretplate;
	
	@Column(nullable = true)
	private Double cenaClanarine;
	
	@Column(nullable = true, length = 10, unique = true)
	private String koncentratorKod;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Urednik urednik;
	
	@OneToMany
	private Set<Urednik> uredjivackiOdbor;
	
	@ManyToMany
	@JoinTable(name="CASOPIS_RECENZENT",
	    joinColumns=@JoinColumn(name="casopis_id"),
	       inverseJoinColumns=@JoinColumn(name="recenzent_id"))
	private Set<Recenzent> recenzenti;

	public Casopis() {
		super();
	}

	public Casopis(Long id, String issn, String naziv, boolean openAccess, Set<NaucnaOblast> naucneOblasti,
			Double cenaPretplate, Double cenaClanarine, String koncentratorKod, Urednik urednik,
			Set<Urednik> uredjivackiOdbor, Set<Recenzent> recenzenti) {
		super();
		this.id = id;
		this.issn = issn;
		this.naziv = naziv;
		this.openAccess = openAccess;
		this.naucneOblasti = naucneOblasti;
		this.cenaPretplate = cenaPretplate;
		this.cenaClanarine = cenaClanarine;
		this.koncentratorKod = koncentratorKod;
		this.urednik = urednik;
		this.uredjivackiOdbor = uredjivackiOdbor;
		this.recenzenti = recenzenti;
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

	public Double getCenaPretplate() {
		return cenaPretplate;
	}

	public void setCenaPretplate(Double cenaPretplate) {
		this.cenaPretplate = cenaPretplate;
	}

	public Double getCenaClanarine() {
		return cenaClanarine;
	}

	public void setCenaClanarine(Double cenaClanarine) {
		this.cenaClanarine = cenaClanarine;
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

	public Set<Recenzent> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(Set<Recenzent> recenzenti) {
		this.recenzenti = recenzenti;
	}

	public String getKoncentratorKod() {
		return koncentratorKod;
	}

	public void setKoncentratorKod(String koncentratorKod) {
		this.koncentratorKod = koncentratorKod;
	}

}
