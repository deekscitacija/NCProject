package com.ftn.nc.NCBackend.web.model;

import javax.persistence.*;

@Entity
public class RegistrovaniKorisnik {

	@Id
	private Long id;

	public RegistrovaniKorisnik() {
		
	}
	
	public RegistrovaniKorisnik(Long id) {
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
