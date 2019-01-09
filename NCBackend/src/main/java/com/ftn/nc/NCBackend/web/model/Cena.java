package com.ftn.nc.NCBackend.web.model;

import javax.persistence.*;

@Entity
public class Cena {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Integer mesec;
	
	@Column(nullable = false)
	private Integer godina;
	
	@Column(nullable = false)
	private Double vrednost;

	public Cena() {
		super();
	}

	public Cena(Long id, Integer mesec, Integer godina, Double vrednost) {
		super();
		this.id = id;
		this.mesec = mesec;
		this.godina = godina;
		this.vrednost = vrednost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMesec() {
		return mesec;
	}

	public void setMesec(Integer mesec) {
		this.mesec = mesec;
	}

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public Double getVrednost() {
		return vrednost;
	}

	public void setVrednost(Double vrednost) {
		this.vrednost = vrednost;
	}
	
}
