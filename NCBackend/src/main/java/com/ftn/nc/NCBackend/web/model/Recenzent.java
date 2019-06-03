package com.ftn.nc.NCBackend.web.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Recenzent {
	
	@Id
	private Long id;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinTable(name="CASOPIS_RECENZENT",
	    joinColumns=@JoinColumn(name="recenzent_id"),
	       inverseJoinColumns=@JoinColumn(name="casopis_id"))
	private List<Casopis> casopisi;
	
	@ManyToMany
	private List<NaucnaOblast> naucneOblasti;

	public Recenzent() {
		super();
	}

	public Recenzent(Long id, List<NaucnaOblast> naucneOblasti) {
		super();
		this.id = id;
		this.naucneOblasti = naucneOblasti;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Casopis> getCasopisi() {
		return casopisi;
	}

	public void setCasopisi(List<Casopis> casopisi) {
		this.casopisi = casopisi;
	}

	public List<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}
	

}
