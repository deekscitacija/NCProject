package com.ftn.nc.NCBackend.web.model;

import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	private List<NaucnaOblast> naucneOblasti;
	
	@Column(nullable = true)
	private Double cenaPretplate;
	
	@Column(nullable = true)
	private Double cenaClanarine;
	
	@Column(nullable = true, length = 10, unique = true)
	private String koncentratorKod;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Urednik urednik;
	
	@OneToMany
	@JoinTable(name="CASOPIS_UREDJIVACKI_ODBOR",
	    joinColumns=@JoinColumn(name="casopis_id"),
	       inverseJoinColumns=@JoinColumn(name="urednik_id"))
	@JsonBackReference
	private List<Urednik> uredjivackiOdbor;
	
	@ManyToMany
	@JoinTable(name="CASOPIS_RECENZENT",
	    joinColumns=@JoinColumn(name="casopis_id"),
	       inverseJoinColumns=@JoinColumn(name="recenzent_id"))
	@JsonBackReference
	private List<Recenzent> recenzenti;

	public Casopis() {
		super();
	}

	public Casopis(Long id, String issn, String naziv, boolean openAccess, List<NaucnaOblast> naucneOblasti,
			Double cenaPretplate, Double cenaClanarine, String koncentratorKod, Urednik urednik,
			List<Urednik> uredjivackiOdbor, List<Recenzent> recenzenti) {
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

	public List<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<NaucnaOblast> naucneOblasti) {
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

	public List<Urednik> getUredjivackiOdbor() {
		return uredjivackiOdbor;
	}

	public void setUredjivackiOdbor(List<Urednik> uredjivackiOdbor) {
		this.uredjivackiOdbor = uredjivackiOdbor;
	}

	public List<Recenzent> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(List<Recenzent> recenzenti) {
		this.recenzenti = recenzenti;
	}

	public String getKoncentratorKod() {
		return koncentratorKod;
	}

	public void setKoncentratorKod(String koncentratorKod) {
		this.koncentratorKod = koncentratorKod;
	}

}
