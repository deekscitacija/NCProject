package com.ftn.nc.NCBackend.web.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Urednik {

	@Id
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String titula;
	
	@ManyToMany
	private List<NaucnaOblast> naucneOblasti;
	
	@ManyToOne
	@JoinTable(name="CASOPIS_UREDJIVACKI_ODBOR",
    joinColumns=@JoinColumn(name="urednik_id"),
       inverseJoinColumns=@JoinColumn(name="casopis_id"))
	private Casopis uredjujeCasopis;
	
	@ManyToOne
	private Casopis odgovorniUrednik;

	public Urednik() {
		super();
	}
	
	public Urednik(Long id, String titula, List<NaucnaOblast> naucneOblasti, Casopis odgovorniUrednik) {
		super();
		this.id = id;
		this.titula = titula;
		this.naucneOblasti = naucneOblasti;
		this.odgovorniUrednik = odgovorniUrednik;
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

	public List<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public Casopis getUredjujeCasopis() {
		return uredjujeCasopis;
	}

	public void setUredjujeCasopis(Casopis uredjujeCasopis) {
		this.uredjujeCasopis = uredjujeCasopis;
	}

	public Casopis getOdgovorniUrednik() {
		return odgovorniUrednik;
	}

	public void setOdgovorniUrednik(Casopis odgovorniUrednik) {
		this.odgovorniUrednik = odgovorniUrednik;
	}
	
}
