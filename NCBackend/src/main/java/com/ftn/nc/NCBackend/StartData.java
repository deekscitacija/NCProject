package com.ftn.nc.NCBackend;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Component;

import com.ftn.nc.NCBackend.elastic.handler.PDFHandler;
import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;
import com.ftn.nc.NCBackend.elastic.model.RecenzentInfo;
import com.ftn.nc.NCBackend.elastic.repository.IndexUnitRepository;
import com.ftn.nc.NCBackend.elastic.repository.NaucnaOblastInfoRepository;
import com.ftn.nc.NCBackend.elastic.repository.RecenzentInfoRepository;
import com.ftn.nc.NCBackend.web.model.Autor;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Cena;
import com.ftn.nc.NCBackend.web.model.Drzava;
import com.ftn.nc.NCBackend.web.model.Grad;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.Permisija;
import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RegistrovaniKorisnik;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
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
	
	public static final String LIBRARY_DIR_PATH = "D:\\TheMara\\Master PRNiI\\Naucna Centrala\\Biblioteka"; 
	
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
	
	// *** Camunda *** //
	
	@Autowired
	private IdentityService identityService;
	
	// *** ElasticSearch *** //
	
	@Autowired
	private IndexUnitRepository indexUnitRepository;
	
	@Autowired
	private NaucnaOblastInfoRepository naucnaOblastInfoRepository;
	
	@Autowired
	private RecenzentInfoRepository recenzentInfoRepository;
	
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
		
		// *** Gradovi i Drzave *** //
		
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
		
		// *** Naucne Oblasti *** //
		
		NaucnaOblast no1 = new NaucnaOblast(null, "MAT1", "Matematika");
		NaucnaOblast no2 = new NaucnaOblast(null, "FIZ1", "Fizika");
		NaucnaOblast no3 = new NaucnaOblast(null, "PSI1", "Psihologija");
		NaucnaOblast no4 = new NaucnaOblast(null, "GEO1", "Geografija");
		NaucnaOblast no5 = new NaucnaOblast(null, "BIO1", "Biologija");
		NaucnaOblast no6 = new NaucnaOblast(null, "HEM1", "Hemija");
		
		no1 = naucnaOblastRepository.save(no1);
		no2 = naucnaOblastRepository.save(no2);
		no3 = naucnaOblastRepository.save(no3);
		no4 = naucnaOblastRepository.save(no4);
		no5 = naucnaOblastRepository.save(no5);
		no6 = naucnaOblastRepository.save(no6);
		
		NaucnaOblastInfo noi1 = new NaucnaOblastInfo(no1.getId().toString(), no1.getNaziv(), no1.getKod());
		NaucnaOblastInfo noi2 = new NaucnaOblastInfo(no2.getId().toString(), no2.getNaziv(), no2.getKod());
		NaucnaOblastInfo noi3 = new NaucnaOblastInfo(no3.getId().toString(), no3.getNaziv(), no3.getKod());
		NaucnaOblastInfo noi4 = new NaucnaOblastInfo(no4.getId().toString(), no4.getNaziv(), no4.getKod());
		NaucnaOblastInfo noi5 = new NaucnaOblastInfo(no5.getId().toString(), no5.getNaziv(), no5.getKod());
		NaucnaOblastInfo noi6 = new NaucnaOblastInfo(no6.getId().toString(), no6.getNaziv(), no6.getKod());
		
		noi1 = naucnaOblastInfoRepository.save(noi1);
		noi2 = naucnaOblastInfoRepository.save(noi2);
		noi3 = naucnaOblastInfoRepository.save(noi3);
		noi4 = naucnaOblastInfoRepository.save(noi4);
		noi5 = naucnaOblastInfoRepository.save(noi5);
		noi6 = naucnaOblastInfoRepository.save(noi6);
		
		Set<NaucnaOblast> naucneOblasti1 = new HashSet<>();
		naucneOblasti1.add(no1);
		naucneOblasti1.add(no2);
		naucneOblasti1.add(no6);
		
		Set<NaucnaOblast> naucneOblasti2 = new HashSet<>();
		naucneOblasti2.add(no2);
		naucneOblasti2.add(no3);
		naucneOblasti2.add(no1);
		naucneOblasti2.add(no5);
		
		Set<NaucnaOblast> naucneOblasti3 = new HashSet<>();
		naucneOblasti3.add(no1);
		naucneOblasti3.add(no3);
		naucneOblasti2.add(no4);
		
		Set<NaucnaOblast> naucneOblasti4 = new HashSet<>();
		naucneOblasti4.add(no2);
		naucneOblasti4.add(no3);
		
		Set<NaucnaOblast> naucneOblasti5 = new HashSet<>();
		naucneOblasti5.add(no1);
		naucneOblasti5.add(no4);
		naucneOblasti2.add(no6);
		
		List<NaucnaOblastInfo> naucneOblastiI1 = new ArrayList<>();
		naucneOblastiI1.add(noi1);
		naucneOblastiI1.add(noi2);
		naucneOblastiI1.add(noi6);
		
		List<NaucnaOblastInfo> naucneOblastiI2 = new ArrayList<>();
		naucneOblastiI2.add(noi2);
		naucneOblastiI2.add(noi3);
		naucneOblastiI2.add(noi1);
		naucneOblastiI2.add(noi5);
		
		List<NaucnaOblastInfo> naucneOblastiI3 = new ArrayList<>();
		naucneOblastiI3.add(noi1);
		naucneOblastiI3.add(noi3);
		naucneOblastiI3.add(noi4);
		
		List<NaucnaOblastInfo> naucneOblastiI4 = new ArrayList<>();
		naucneOblastiI4.add(noi2);
		naucneOblastiI4.add(noi3);
		
		List<NaucnaOblastInfo> naucneOblastiI5 = new ArrayList<>();
		naucneOblastiI5.add(noi1);
		naucneOblastiI5.add(noi4);
		naucneOblastiI5.add(noi6);
		
		// *** Korisnici *** //
		
		Korisnik k1 = new Korisnik(null, "korisnik1@email.com", "lozinka1", "Pera", "Peric", gr1, setAU, null, null, null, null);
		Korisnik k2 = new Korisnik(null, "korisnik2@email.com", "lozinka2", "Zika", "Zikic", gr2, setRK, null, null, null, null);
		Korisnik k3 = new Korisnik(null, "korisnik3@email.com", "lozinka3", "Sima", "Simic", gr3, setRE, null, null, null, null);
		Korisnik k4 = new Korisnik(null, "korisnik4@email.com", "lozinka4", "Mika", "Mikic", gr4, setUR, null, null, null, null);
		Korisnik k5 = new Korisnik(null, "korisnik5@email.com", "lozinka5", "Mila", "Milanovic", gr5, setRE, null, null, null, null);
		Korisnik k6 = new Korisnik(null, "korisnik6@email.com", "lozinka6", "Sava", "Savic", gr6, setRE, null, null, null, null);
		Korisnik k7 = new Korisnik(null, "korisnik7@email.com", "lozinka7", "Nikola", "Nikolic", gr1, setRE, null, null, null, null);
		Korisnik k8 = new Korisnik(null, "korisnik8@email.com", "lozinka8", "Marko", "Markovic", gr2, setRE, null, null, null, null);
		Korisnik k9 = new Korisnik(null, "korisnik9@email.com", "lozinka9", "Milica", "Milicic", gr3, setRE, null, null, null, null);
		Korisnik k10 = new Korisnik(null, "korisnik10@email.com", "lozinka10", "Rada", "Radovanovic", gr5, setRE, null, null, null, null);
		
		k1 = korisnikRepository.save(k1);
		k2 = korisnikRepository.save(k2);
		k3 = korisnikRepository.save(k3);
		k4 = korisnikRepository.save(k4);
		k5 = korisnikRepository.save(k5);
		k6 = korisnikRepository.save(k6);
		k7 = korisnikRepository.save(k7);
		k8 = korisnikRepository.save(k8);
		k9 = korisnikRepository.save(k9);
		k10 = korisnikRepository.save(k10);
		
		Autor autor = new Autor(k1.getId(), null, null);
		RegistrovaniKorisnik registrovaniKorisnik = new RegistrovaniKorisnik(k2.getId());
		Urednik urednik = new Urednik(k4.getId(), "Titula 1", naucneOblasti1, null);
		
		autor = autorRepository.save(autor);
		registrovaniKorisnik = registrovaniKorisnikRepository.save(registrovaniKorisnik);
		urednik = urednikRepository.save(urednik);
		
		Recenzent rec1 = new Recenzent(k3.getId());
		Recenzent rec2 = new Recenzent(k5.getId());
		Recenzent rec3 = new Recenzent(k6.getId());
		Recenzent rec4 = new Recenzent(k7.getId());
		Recenzent rec5 = new Recenzent(k8.getId());
		Recenzent rec6 = new Recenzent(k9.getId());
		Recenzent rec7 = new Recenzent(k10.getId());
		
		rec1 = recenzentRepository.save(rec1);
		rec2 = recenzentRepository.save(rec2);
		rec3 = recenzentRepository.save(rec3);
		rec4 = recenzentRepository.save(rec4);
		rec5 = recenzentRepository.save(rec5);
		rec6 = recenzentRepository.save(rec6);
		rec7 = recenzentRepository.save(rec7);
		
		RecenzentInfo reci1 = new RecenzentInfo(rec1.getId().toString(), k3.getIme(), k3.getPrezime(), 
				k3.getGrad().getNaziv(), new GeoPoint(k3.getGrad().getLat(), k3.getGrad().getLon()));
		
		RecenzentInfo reci2 = new RecenzentInfo(rec2.getId().toString(), k5.getIme(), k5.getPrezime(), 
				k5.getGrad().getNaziv(), new GeoPoint(k5.getGrad().getLat(), k5.getGrad().getLon()));
		
		RecenzentInfo reci3 = new RecenzentInfo(rec3.getId().toString(), k6.getIme(), k6.getPrezime(), 
				k6.getGrad().getNaziv(), new GeoPoint(k6.getGrad().getLat(), k6.getGrad().getLon()));
		
		RecenzentInfo reci4 = new RecenzentInfo(rec4.getId().toString(), k7.getIme(), k7.getPrezime(), 
				k7.getGrad().getNaziv(), new GeoPoint(k7.getGrad().getLat(), k7.getGrad().getLon()));
		
		RecenzentInfo reci5 = new RecenzentInfo(rec5.getId().toString(), k8.getIme(), k8.getPrezime(), 
				k8.getGrad().getNaziv(), new GeoPoint(k8.getGrad().getLat(), k8.getGrad().getLon()));
		
		RecenzentInfo reci6 = new RecenzentInfo(rec6.getId().toString(), k9.getIme(), k9.getPrezime(), 
				k9.getGrad().getNaziv(), new GeoPoint(k9.getGrad().getLat(), k9.getGrad().getLon()));
		
		RecenzentInfo reci7 = new RecenzentInfo(rec7.getId().toString(), k10.getIme(), k10.getPrezime(), 
				k10.getGrad().getNaziv(), new GeoPoint(k10.getGrad().getLat(), k10.getGrad().getLon()));
		
		reci1 = recenzentInfoRepository.save(reci1);
		reci2 = recenzentInfoRepository.save(reci2);
		reci3 = recenzentInfoRepository.save(reci3);
		reci4 = recenzentInfoRepository.save(reci4);
		reci5 = recenzentInfoRepository.save(reci5);
		reci6 = recenzentInfoRepository.save(reci6);
		reci7 = recenzentInfoRepository.save(reci7);
		
		k1.setAutor(autor);
		k2.setRegistrovaniKorisnik(registrovaniKorisnik);
		k3.setRecenzent(rec1);
		k4.setUrednik(urednik);
		k5.setRecenzent(rec2);
		k6.setRecenzent(rec3);
		k7.setRecenzent(rec4);
		k8.setRecenzent(rec5);
		k9.setRecenzent(rec6);
		k10.setRecenzent(rec7);
		
		k1 = korisnikRepository.save(k1);
		k2 = korisnikRepository.save(k2);
		k3 = korisnikRepository.save(k3);
		k4 = korisnikRepository.save(k4);
		k5 = korisnikRepository.save(k5);
		k6 = korisnikRepository.save(k6);
		k7 = korisnikRepository.save(k7);
		k8 = korisnikRepository.save(k8);
		k9 = korisnikRepository.save(k9);
		k10 = korisnikRepository.save(k10);
		
		// *** Camunda Korisnici *** //
		
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
		
		// *** Cene *** //
		
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
		
		Set<Recenzent> recenzenti1 = new HashSet<Recenzent>();
		recenzenti1.add(rec1);
		recenzenti1.add(rec3);
		recenzenti1.add(rec4);
		recenzenti1.add(rec5);
		recenzenti1.add(rec7);
		
		Set<Recenzent> recenzenti2 = new HashSet<Recenzent>();
		recenzenti2.add(rec2);
		recenzenti2.add(rec4);
		recenzenti2.add(rec6);
		recenzenti2.add(rec1);
		recenzenti2.add(rec3);
		recenzenti2.add(rec7);
		
		Set<Recenzent> recenzenti3 = new HashSet<Recenzent>();
		recenzenti3.add(rec7);
		recenzenti3.add(rec5);
		recenzenti3.add(rec4);
		recenzenti3.add(rec2);
		recenzenti3.add(rec1);
		
		List<RecenzentInfo> recenzentiI1 = new ArrayList<>();
		recenzentiI1.add(reci1);
		recenzentiI1.add(reci3);
		recenzentiI1.add(reci4);
		recenzentiI1.add(reci5);
		recenzentiI1.add(reci7);
		
		List<RecenzentInfo> recenzentiI2 = new ArrayList<>();
		recenzentiI2.add(reci2);
		recenzentiI2.add(reci4);
		recenzentiI2.add(reci6);
		recenzentiI2.add(reci1);
		recenzentiI2.add(reci3);
		recenzentiI2.add(reci7);
		
		List<RecenzentInfo> recenzentiI3 = new ArrayList<>();
		recenzentiI3.add(reci7);
		recenzentiI3.add(reci5);
		recenzentiI3.add(reci4);
		recenzentiI3.add(reci2);
		recenzentiI3.add(reci1);
		
		// *** Casopisi *** //
		
		Casopis c1 = new Casopis(null, "0001-184X", "Acta herbologica", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti3);
		Casopis c2 = new Casopis(null, "0002-1X22", "Arhitektura i urbanizam", false, naucneOblasti1, pretplata, null, urednik, null, recenzenti2);
		Casopis c3 = new Casopis(null, "0003-45X6", "Balkan Journal of Dental Medicine", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti1);
		Casopis c4 = new Casopis(null, "0004-7895", "Ekonomika poljoprivrede", false, naucneOblasti1, pretplata, null, urednik, null, recenzenti3);
		Casopis c5 = new Casopis(null, "0005-7802", "Civitas", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti2);
		Casopis c6 = new Casopis(null, "0006-5630", "Geonauka", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti1);
		Casopis c7 = new Casopis(null, "0007-XX78", "Geographica Pannonica", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti3);
		Casopis c8 = new Casopis(null, "0008-9621", "Jugoslovensko bankarstvo", false, naucneOblasti1, pretplata, null, urednik, null, recenzenti2);
		Casopis c9 = new Casopis(null, "0009-8862", "Mathematica Moravica", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti1);
		Casopis c10 = new Casopis(null, "0010-1403", "Medicinski časopis", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti3);
		Casopis c11 = new Casopis(null, "0011-54X6", "Sanamed", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti2);
		Casopis c12 = new Casopis(null, "0012-7863", "Spatium", false, naucneOblasti1, pretplata, null, urednik, null, recenzenti1);
		Casopis c13 = new Casopis(null, "0013-1452", "Sociološki pregled", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti3);
		Casopis c14 = new Casopis(null, "0014-8787", "Tehnika - Elektrotehnika", true, naucneOblasti1, null, clanarina, urednik, null, recenzenti2);
		Casopis c15 = new Casopis(null, "0015-1465", "Veterinarski glasnik", false, naucneOblasti1, pretplata, null, urednik, null, recenzenti1);
		
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
		
		RevizijaRada revizija1 = new RevizijaRada(null, "Predikcija ishoda teniskih meceva", "Milan Milanovic, Predrag Preradovic", 
				"U radu je opisana predkcija ishoda teniskih meceva.", "predikcija, tenis, mec, loptica, Naive Bayes", LIBRARY_DIR_PATH+"\\2015 Predikcija ishoda teniskih meceva.pdf", 
				true, true, true, autor, c1, naucneOblasti1, recenzenti1, null);
		
		RevizijaRada revizija2 = new RevizijaRada(null, "Цене некретнина", "Соња Петровић, Бранка Радовановић", 
				"Кретање цена некретнина, опширно и детаљно,", "предикција, некретнине, некретнина, кластернованје, кластер, цена, новац, стан, кућа", LIBRARY_DIR_PATH+"\\2016 Predikcija cene nekretnina.pdf", 
				true, true, true, autor, c2, naucneOblasti2, recenzenti2, null);
		
		RevizijaRada revizija3 = new RevizijaRada(null, "Predikcija musterija koje ce napustiti kompaniju", "Darko Jovanovic, Nikola Nikolic, Jovana Jovanovic", 
				"Postoji li rizik da vasi zaposleni napuste kompaniju?", "predikcija, kompanija, zaposleni, nezadovoljstvo, klaster, klasterovanje", LIBRARY_DIR_PATH+"\\2016 Predikcija Churn (musterije koje ce napustiti kompaniju).pdf", 
				true, true, true, autor, c2, naucneOblasti3, recenzenti3, null);
		
		RevizijaRada revizija4 = new RevizijaRada(null, "IMDB, predikcija ranka filmova", "Ostoja Ostojic, Nenad Nenadovic", 
				"Metode koriscenje kako bi se izvrsila predikcija ranka filma na IMDB sajtu.", "predikcija, film, uspeh, rank, zanr, faktori", LIBRARY_DIR_PATH+"\\2016 Predikcija ranka filmova na IMDB.pdf", 
				true, true, true, autor, c3, naucneOblasti1, recenzenti1, null);
		
		RevizijaRada revizija5 = new RevizijaRada(null, "Анализа кашњења авионских летова", "Драгана Драгић", 
				"Анализа и прикз доминантних разлога кашњења авионских летова на свим рутама.", "анализа, кашњење, касни, авион, лет, писта", LIBRARY_DIR_PATH+"\\2017 Analiza kasnjenja avionskih letova.pdf", 
				true, true, true, autor, c1, naucneOblasti2, recenzenti2, null);
		
		RevizijaRada revizija6 = new RevizijaRada(null, "Hronicne bubrezne bolesti", "Aleksandar Stankovic, Luka Lukic, Ana Petrovic", 
				"Predikcija hronicnih bubreznih bolesti iz prethodnih anamneza nekog pacijenta i procena rizika razvijanja.", "predikcija, bubreg, bubrezi, bolest, anamneza, hronicno", LIBRARY_DIR_PATH+"\\2017 Chronic kidney disease.pdf", 
				true, true, true, autor, c1, naucneOblasti4, recenzenti3, null);
		
		RevizijaRada revizija7 = new RevizijaRada(null, "СМС спам", "Марко Марковић", 
				"Анализа смс порука и предикција спама на основу претходне анализе", "предикција, анализа, смс, спам", LIBRARY_DIR_PATH+"\\2017 Predikcija SMS spama.pdf", 
				true, true, true, autor, c3, naucneOblasti5, recenzenti1, null);
		
		revizija1 = revizijaRadaRepository.save(revizija1);
		revizija2 = revizijaRadaRepository.save(revizija2);
		revizija3 = revizijaRadaRepository.save(revizija3);
		revizija4 = revizijaRadaRepository.save(revizija4);
		revizija5 = revizijaRadaRepository.save(revizija5);
		revizija6 = revizijaRadaRepository.save(revizija6);
		revizija7 = revizijaRadaRepository.save(revizija7);
		
		NaucniRad rad1 = new NaucniRad(revizija1);
		NaucniRad rad2 = new NaucniRad(revizija2);
		NaucniRad rad3 = new NaucniRad(revizija3);
		NaucniRad rad4 = new NaucniRad(revizija4);
		NaucniRad rad5 = new NaucniRad(revizija5);
		NaucniRad rad6 = new NaucniRad(revizija6);
		NaucniRad rad7 = new NaucniRad(revizija7);
		
		rad1 = naucniRadRepository.save(rad1);
		rad2 = naucniRadRepository.save(rad2);
		rad3 = naucniRadRepository.save(rad3);
		rad4 = naucniRadRepository.save(rad4);
		rad5 = naucniRadRepository.save(rad5);
		rad6 = naucniRadRepository.save(rad6);
		rad7 = naucniRadRepository.save(rad7);
		
		PDFHandler pdfHandler = new PDFHandler(); 
		
		IndexUnit iu1 = new IndexUnit(rad1, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad1.getPutanja())), recenzentiI1, naucneOblastiI1);
		IndexUnit iu2 = new IndexUnit(rad2, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad2.getPutanja())), recenzentiI2, naucneOblastiI2);
		IndexUnit iu3 = new IndexUnit(rad3, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad3.getPutanja())), recenzentiI3, naucneOblastiI3);
		IndexUnit iu4 = new IndexUnit(rad4, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad4.getPutanja())), recenzentiI1, naucneOblastiI1);
		IndexUnit iu5 = new IndexUnit(rad5, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad5.getPutanja())), recenzentiI2, naucneOblastiI2);
		IndexUnit iu6 = new IndexUnit(rad6, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad6.getPutanja())), recenzentiI3, naucneOblastiI4);
		IndexUnit iu7 = new IndexUnit(rad7, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad7.getPutanja())), recenzentiI1, naucneOblastiI5);
		
		
		iu1 = indexUnitRepository.save(iu1);
		iu2 = indexUnitRepository.save(iu2);
		iu3 = indexUnitRepository.save(iu3);
		iu4 = indexUnitRepository.save(iu4);
		iu5 = indexUnitRepository.save(iu5);
		iu6 = indexUnitRepository.save(iu6);
		iu7 = indexUnitRepository.save(iu7);
	
	}
}
