package com.ftn.nc.NCBackend.model;

import javax.persistence.*;

@Entity
public class Recenzent {
	
	@Id
	private Long id;

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

}
