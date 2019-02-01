package com.ftn.nc.NCBackend.web.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Recenzent {
	
	@Id
	private Long id;
	
	@ManyToMany
	@JsonBackReference
	@JoinTable(name="CASOPIS_RECENZENT",
	    joinColumns=@JoinColumn(name="recenzent_id"),
	       inverseJoinColumns=@JoinColumn(name="casopis_id"))
	private Set<Casopis> casopisi;

	public Recenzent() {
		super();
	}
	
	public Recenzent(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Casopis> getCasopisi() {
		return casopisi;
	}

	public void setCasopisi(Set<Casopis> casopisi) {
		this.casopisi = casopisi;
	}

}
