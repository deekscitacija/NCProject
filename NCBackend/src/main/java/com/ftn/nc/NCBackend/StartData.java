package com.ftn.nc.NCBackend;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.mockito.internal.util.collections.HashCodeAndEqualsSafeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.nc.NCBackend.model.Autor;
import com.ftn.nc.NCBackend.model.Casopis;
import com.ftn.nc.NCBackend.model.Cena;
import com.ftn.nc.NCBackend.model.Korisnik;
import com.ftn.nc.NCBackend.model.NaucnaOblast;
import com.ftn.nc.NCBackend.model.Permisija;
import com.ftn.nc.NCBackend.model.Recenzent;
import com.ftn.nc.NCBackend.model.RegistrovaniKorisnik;
import com.ftn.nc.NCBackend.model.TipKorisnika;
import com.ftn.nc.NCBackend.model.Urednik;
import com.ftn.nc.NCBackend.repository.AutorRepository;
import com.ftn.nc.NCBackend.repository.CasopisRepository;
import com.ftn.nc.NCBackend.repository.CenaReposiotry;
import com.ftn.nc.NCBackend.repository.DrzavaRepository;
import com.ftn.nc.NCBackend.repository.GradRepository;
import com.ftn.nc.NCBackend.repository.IzdanjeRepository;
import com.ftn.nc.NCBackend.repository.KomentarRepository;
import com.ftn.nc.NCBackend.repository.KorisnikRepository;
import com.ftn.nc.NCBackend.repository.NaucnaOblastRepository;
import com.ftn.nc.NCBackend.repository.NaucniRadRepository;
import com.ftn.nc.NCBackend.repository.PermisijaRepository;
import com.ftn.nc.NCBackend.repository.RecenzentRepository;
import com.ftn.nc.NCBackend.repository.RegistrovaniKorisnikRepository;
import com.ftn.nc.NCBackend.repository.RevizijaRadaRepository;
import com.ftn.nc.NCBackend.repository.TipKorisnikaRepository;
import com.ftn.nc.NCBackend.repository.UrednikRepository;

@Component
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
		
		Korisnik k1 = new Korisnik(null, "korisnik1@email.com", "lozinka1", "Pera", "Peric", setAU, null, null, null, null);
		Korisnik k2 = new Korisnik(null, "korisnik2@email.com", "lozinka2", "Zika", "Zikic", setRK, null, null, null, null);
		Korisnik k3 = new Korisnik(null, "korisnik3@email.com", "lozinka3", "Sima", "Simic", setRE, null, null, null, null);
		Korisnik k4 = new Korisnik(null, "korisnik4@email.com", "lozinka3", "Sima", "Simic", setRE, null, null, null, null);
		
		korisnikRepository.save(k1);
		korisnikRepository.save(k2);
		korisnikRepository.save(k3);
		korisnikRepository.save(k4);
		
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
		
		korisnikRepository.save(k1);
		korisnikRepository.save(k2);
		korisnikRepository.save(k3);
		korisnikRepository.save(k4);
		
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
		
	}

}
