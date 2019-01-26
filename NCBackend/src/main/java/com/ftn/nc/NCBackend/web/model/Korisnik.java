package com.ftn.nc.NCBackend.web.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 90)
	private String email;
	
	@Column(nullable = false, length = 256)
	private String lozinka;
	
	@Column(nullable = false, length = 120)
	private String ime;
	
	@Column(nullable = false, length = 120)
	private String prezime;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<TipKorisnika> tip;
	
	@OneToOne(optional = true)
    @PrimaryKeyJoinColumn
    private Autor autor;
	
	@OneToOne(optional = true)
    @PrimaryKeyJoinColumn
    private Recenzent recenzent;

	@OneToOne(optional = true)
    @PrimaryKeyJoinColumn
    private RegistrovaniKorisnik registrovaniKorisnik;
	
	@OneToOne(optional = true)
    @PrimaryKeyJoinColumn
    private Urednik urednik;

	public Korisnik() {
		super();
	}

	public Korisnik(Long id, String email, String lozinka, String ime, String prezime, Set<TipKorisnika> tip, Autor autor,
			Recenzent recenzent, RegistrovaniKorisnik registrovaniKorisnik, Urednik urednik) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.tip = tip;
		this.autor = autor;
		this.recenzent = recenzent;
		this.registrovaniKorisnik = registrovaniKorisnik;
		this.urednik = urednik;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Set<TipKorisnika> getTip() {
		return tip;
	}

	public void setTip(Set<TipKorisnika> tip) {
		this.tip = tip;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Recenzent getRecenzent() {
		return recenzent;
	}

	public void setRecenzent(Recenzent recenzent) {
		this.recenzent = recenzent;
	}

	public RegistrovaniKorisnik getRegistrovaniKorisnik() {
		return registrovaniKorisnik;
	}

	public void setRegistrovaniKorisnik(RegistrovaniKorisnik registrovaniKorisnik) {
		this.registrovaniKorisnik = registrovaniKorisnik;
	}

	public Urednik getUrednik() {
		return urednik;
	}

	public void setUrednik(Urednik urednik) {
		this.urednik = urednik;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", email=" + email + ", lozinka=" + lozinka + ", ime=" + ime + ", prezime="
				+ prezime + "]";
	}
	
	

}
