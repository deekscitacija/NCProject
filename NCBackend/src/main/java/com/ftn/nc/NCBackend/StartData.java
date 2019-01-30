package com.ftn.nc.NCBackend;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.nc.NCBackend.web.model.Autor;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Cena;
import com.ftn.nc.NCBackend.web.model.Drzava;
import com.ftn.nc.NCBackend.web.model.Grad;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.Permisija;
import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RegistrovaniKorisnik;
import com.ftn.nc.NCBackend.web.model.TipKorisnika;
import com.ftn.nc.NCBackend.web.model.Urednik;
import com.ftn.nc.NCBackend.web.repository.AutorRepository;
import com.ftn.nc.NCBackend.web.repository.CasopisRepository;
import com.ftn.nc.NCBackend.web.repository.CenaReposiotry;
import com.ftn.nc.NCBackend.web.repository.DrzavaRepository;
import com.ftn.nc.NCBackend.web.repository.GradRepository;
import com.ftn.nc.NCBackend.web.repository.IzdanjeRepository;
import com.ftn.nc.NCBackend.web.repository.KomentarRepository;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;
import com.ftn.nc.NCBackend.web.repository.NaucnaOblastRepository;
import com.ftn.nc.NCBackend.web.repository.NaucniRadRepository;
import com.ftn.nc.NCBackend.web.repository.PermisijaRepository;
import com.ftn.nc.NCBackend.web.repository.RecenzentRepository;
import com.ftn.nc.NCBackend.web.repository.RegistrovaniKorisnikRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;
import com.ftn.nc.NCBackend.web.repository.TipKorisnikaRepository;
import com.ftn.nc.NCBackend.web.repository.UrednikRepository;

//@Component
public class StartData {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private CasopisRepository casopisRepository;
	
	@Autowired
	private CenaReposiotry cenaReposiotry; 
	
	@Autowired
	private DrzavaRepository drzavaRepository;
	
	@Autowired
	private GradRepository gradRepository;
	
	@Autowired
	private IzdanjeRepository izdanjeRepository;
	
	@Autowired
	private KomentarRepository komentarRepository;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private NaucnaOblastRepository naucnaOblastRepository;
	
	@Autowired
	private NaucniRadRepository naucniRadRepository;
	
	@Autowired
	private PermisijaRepository permisijaRepository;
	
	@Autowired
	private RecenzentRepository recenzentRepository;
	
	@Autowired
	private RegistrovaniKorisnikRepository registrovaniKorisnikRepository;
	
	@Autowired
	private RevizijaRadaRepository revizijaRadaRepository;
	
	@Autowired
	private TipKorisnikaRepository tipKorisnikaRepository;
	
	@Autowired
	private UrednikRepository urednikRepository;
	
	@Autowired
	private IdentityService identityService;
	
	public StartData() {
		
	}
	
	@PostConstruct
	private void init() {
		
		Permisija p1 = new Permisija(null, "P1", "permisija 1");
		
		Set<Permisija> permisije = new HashSet<Permisija>();
		permisije.add(p1);
		
		p1 = permisijaRepository.save(p1);
		
		TipKorisnika tk1 = new TipKorisnika(null, "AU", "Autor", permisije);
		TipKorisnika tk2 = new TipKorisnika(null, "RK", "Registrovani Korisnik", permisije);
		TipKorisnika tk3 = new TipKorisnika(null, "RE", "Recenzent", permisije);
		TipKorisnika tk4 = new TipKorisnika(null, "UR", "Urednik", permisije);
		
		tk1 = tipKorisnikaRepository.save(tk1);
		tk2 = tipKorisnikaRepository.save(tk2);
		tk3 = tipKorisnikaRepository.save(tk3);
		tk4 = tipKorisnikaRepository.save(tk4);
		
		HashSet<TipKorisnika> setAU = new HashSet<>();
		HashSet<TipKorisnika> setRK = new HashSet<>();
		HashSet<TipKorisnika> setRE = new HashSet<>();
		HashSet<TipKorisnika> setUR = new HashSet<>();
		
		setAU.add(tk1);
		setRK.add(tk2);
		setRE.add(tk3);
		setUR.add(tk3);
		setUR.add(tk4);
		
		NaucnaOblast no1 = new NaucnaOblast(null, "NO01", "Naucna Oblast 1");
		NaucnaOblast no2 = new NaucnaOblast(null, "NO02", "Naucna Oblast 2");
		NaucnaOblast no3 = new NaucnaOblast(null, "NO03", "Naucna Oblast 3");
		
		naucnaOblastRepository.save(no1);
		naucnaOblastRepository.save(no2);
		naucnaOblastRepository.save(no3);
		
		Set<NaucnaOblast> naucneOblasti1 = new HashSet<>();
		naucneOblasti1.add(no1);
		naucneOblasti1.add(no2);
		
		Set<NaucnaOblast> naucneOblasti2 = new HashSet<>();
		naucneOblasti2.add(no2);
		naucneOblasti2.add(no3);
		
		Set<NaucnaOblast> naucneOblasti3 = new HashSet<>();
		naucneOblasti3.add(no1);
		naucneOblasti3.add(no3);
		
		Drzava dr1 = new Drzava(null, "SRB", "Srbija");
		dr1 = drzavaRepository.save(dr1);
		
		Grad gr1 = new Grad(null, "Novi Sad", dr1, 45.267136, 19.833549);
		Grad gr2 = new Grad(null, "Beograd", dr1, 44.787197, 20.457273);
		Grad gr3 = new Grad(null, "Sremska Mitrovica", dr1, 44.993642, 19.6271983);
		Grad gr4 = new Grad(null, "Nis", dr1, 43.316238, 21.8583399);
		Grad gr5 = new Grad(null, "Valjevo", dr1, 44.2708145, 19.8655599);
		Grad gr6 = new Grad(null, "Uzice", dr1, 43.849342, 19.8098855);
		
		gr1 = gradRepository.save(gr1);
		gr2 = gradRepository.save(gr2);
		gr3 = gradRepository.save(gr3);
		gr4 = gradRepository.save(gr4);
		gr5 = gradRepository.save(gr5);
		gr6 = gradRepository.save(gr6);
		
		Korisnik k1 = new Korisnik(null, "korisnik1@email.com", "lozinka1", "Pera", "Peric", gr1, setAU, null, null, null, null);
		Korisnik k2 = new Korisnik(null, "korisnik2@email.com", "lozinka2", "Zika", "Zikic", gr2, setRK, null, null, null, null);
		Korisnik k3 = new Korisnik(null, "korisnik3@email.com", "lozinka3", "Sima", "Simic", gr3, setRE, null, null, null, null);
		Korisnik k4 = new Korisnik(null, "korisnik4@email.com", "lozinka3", "Sima", "Simic", gr4, setRE, null, null, null, null);
		
		k1 = korisnikRepository.save(k1);
		k2 = korisnikRepository.save(k2);
		k3 = korisnikRepository.save(k3);
		k4 = korisnikRepository.save(k4);
		
		Autor autor = new Autor(k1.getId(), null, null);
		RegistrovaniKorisnik registrovaniKorisnik = new RegistrovaniKorisnik(k2.getId());
		Recenzent recenzent = new Recenzent(k3.getId());
		Urednik urednik = new Urednik(k4.getId(), "Titula 1", naucneOblasti1, null);
		
		autor = autorRepository.save(autor);
		registrovaniKorisnik = registrovaniKorisnikRepository.save(registrovaniKorisnik);
		recenzent = recenzentRepository.save(recenzent);
		urednik = urednikRepository.save(urednik);
		
		k1.setAutor(autor);
		k2.setRegistrovaniKorisnik(registrovaniKorisnik);
		k3.setRecenzent(recenzent);
		k4.setUrednik(urednik);
		
		k1 = korisnikRepository.save(k1);
		k2 = korisnikRepository.save(k2);
		k3 = korisnikRepository.save(k3);
		k4 = korisnikRepository.save(k4);
		
		Cena cena1 = new Cena(null, 1, 2019, new Double(11.50));
		Cena cena2 = new Cena(null, 1, 2019, new Double(71.00));
		Cena cena3 = new Cena(null, 1, 2019, new Double(16.99));
		Cena cena4 = new Cena(null, 1, 2019, new Double(9.99));
		
		cena1 = cenaReposiotry.save(cena1);
		cena2 = cenaReposiotry.save(cena2);
		cena3 = cenaReposiotry.save(cena3);
		cena4 = cenaReposiotry.save(cena4);
		
		Set<Cena> pretplata = new HashSet<>();
		pretplata.add(cena1);
		
		Set<Cena> clanarina = new HashSet<>();
		clanarina.add(cena3);
		
		Casopis c1 = new Casopis(null, "ABCD", "Casopis 1", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c2 = new Casopis(null, "QWER", "Casopis 2", false, naucneOblasti1, pretplata, null, urednik, null);
		Casopis c3 = new Casopis(null, "WERT", "Casopis 3", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c4 = new Casopis(null, "ERTY", "Casopis 4", false, naucneOblasti1, pretplata, null, urednik, null);
		Casopis c5 = new Casopis(null, "RTYU", "Casopis 5", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c6 = new Casopis(null, "ASDF", "Casopis 6", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c7 = new Casopis(null, "SDFG", "Casopis 7", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c8 = new Casopis(null, "DFGH", "Casopis 8", false, naucneOblasti1, pretplata, null, urednik, null);
		Casopis c9 = new Casopis(null, "FGHJ", "Casopis 9", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c10 = new Casopis(null, "GHJK", "Casopis 11", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c11 = new Casopis(null, "HJKL", "Casopis 12", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c12 = new Casopis(null, "ZXCV", "Casopis 13", false, naucneOblasti1, pretplata, null, urednik, null);
		Casopis c13 = new Casopis(null, "XCVB", "Casopis 14", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c14 = new Casopis(null, "CVBN", "Casopis 15", true, naucneOblasti1, null, clanarina, urednik, null);
		Casopis c15 = new Casopis(null, "VBNM", "Casopis 16", false, naucneOblasti1, pretplata, null, urednik, null);
		
		c1 = casopisRepository.save(c1);
		c2 = casopisRepository.save(c2);
		c3 = casopisRepository.save(c3);
		c4 = casopisRepository.save(c4);
		c5 = casopisRepository.save(c5);
		c6 = casopisRepository.save(c6);
		c7 = casopisRepository.save(c7);
		c8 = casopisRepository.save(c8);
		c9 = casopisRepository.save(c9);
		c10 = casopisRepository.save(c10);
		c11 = casopisRepository.save(c11);
		c12 = casopisRepository.save(c12);
		c13 = casopisRepository.save(c13);
		c14 = casopisRepository.save(c14);
		c15 = casopisRepository.save(c15);
		
		System.out.println(k1.getId().toString());
		System.out.println(k2);
		System.out.println(k3);
		System.out.println(k4);
		
		User u1 = identityService.newUser(k1.getId().toString());
		u1.setFirstName(k1.getIme());
		u1.setLastName(k1.getPrezime());
		u1.setEmail(k1.getEmail());
		u1.setPassword(k1.getLozinka());
		
		User u2 = identityService.newUser(k2.getId().toString());
		u2.setFirstName(k2.getIme());
		u2.setLastName(k2.getPrezime());
		u2.setEmail(k2.getEmail());
		u2.setPassword(k2.getLozinka());
		
		User u3 = identityService.newUser(k3.getId().toString());
		u3.setFirstName(k3.getIme());
		u3.setLastName(k3.getPrezime());
		u3.setEmail(k3.getEmail());
		u3.setPassword(k3.getLozinka());
		
		User u4 = identityService.newUser(k4.getId().toString());
		u4.setEmail(k4.getEmail());
		u4.setFirstName(k4.getIme());
		u4.setLastName(k4.getPrezime());
		u4.setPassword(k4.getLozinka());
		
		User u5 = identityService.newUser("undefinedUser");
		u5.setFirstName("Jonh");
		u5.setLastName("Doe");
		u5.setEmail("undefined@email.com");
		u5.setPassword("undefinedpass");
		
		identityService.saveUser(u1);
		identityService.saveUser(u2);
		identityService.saveUser(u3);
		identityService.saveUser(u4);
		identityService.saveUser(u5);
		
	}

}
