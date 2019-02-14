package com.ftn.nc.NCBackend;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.ftn.nc.NCBackend.web.model.Drzava;
import com.ftn.nc.NCBackend.web.model.Grad;
import com.ftn.nc.NCBackend.web.model.Izdanje;
import com.ftn.nc.NCBackend.web.model.Koautor;
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
import com.ftn.nc.NCBackend.web.repository.DrzavaRepository;
import com.ftn.nc.NCBackend.web.repository.GradRepository;
import com.ftn.nc.NCBackend.web.repository.IzdanjeRepository;
import com.ftn.nc.NCBackend.web.repository.KoautorRepository;
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
	private KoautorRepository koautorRepository;
	
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public StartData() {
		
	}
	
	@PostConstruct
	private void init() {
		
		Permisija p1 = new Permisija(null, "KUPI", "Kupovina");
		Permisija p2 = new Permisija(null, "KREIRAJ_RAD", "Kreiranje rada");
		Permisija p3 = new Permisija(null, "PRETPLATI_SE", "pretplata");
		Permisija p4 = new Permisija(null, "PREUZMI_TRANSAKCIJE", "Preuzimaznje transakcija");
		Permisija p5 = new Permisija(null, "RECENZIRAJ_RAD", "Recenziranje");
		Permisija p6 = new Permisija(null, "IZABERI_RECENZENTE", "Odabir recenzenata");
		
		p1 = permisijaRepository.save(p1);
		p2 = permisijaRepository.save(p2);
		p3 = permisijaRepository.save(p3);
		p4 = permisijaRepository.save(p4);
		p5 = permisijaRepository.save(p5);
		p6 = permisijaRepository.save(p6);
		
		Set<Permisija> permisije1 = new HashSet<Permisija>();
		permisije1.add(p2);
		permisije1.add(p3);
		
		Set<Permisija> permisije2 = new HashSet<Permisija>();
		permisije2.add(p1);
		permisije2.add(p3);
		permisije2.add(p4);
		
		Set<Permisija> permisije3 = new HashSet<Permisija>();
		permisije3.add(p5);
		
		Set<Permisija> permisije4 = new HashSet<Permisija>();
		permisije4.add(p6);
		
		TipKorisnika tk1 = new TipKorisnika(null, "AU", "Autor", permisije1);
		TipKorisnika tk2 = new TipKorisnika(null, "RK", "Registrovani Korisnik", permisije2);
		TipKorisnika tk3 = new TipKorisnika(null, "RE", "Recenzent", permisije3);
		TipKorisnika tk4 = new TipKorisnika(null, "UR", "Urednik", permisije4);
		
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
		
		Korisnik k1 = new Korisnik(null, "korisnik1@email.com", passwordEncoder.encode("lozinka1"), "Pera", "Peric", gr1, setAU, null, null, null, null);
		Korisnik k2 = new Korisnik(null, "korisnik2@email.com", passwordEncoder.encode("lozinka2"), "Zika", "Zikic", gr2, setRK, null, null, null, null);
		Korisnik k3 = new Korisnik(null, "korisnik3@email.com", passwordEncoder.encode("lozinka3"), "Sima", "Simic", gr3, setRE, null, null, null, null);
		Korisnik k4 = new Korisnik(null, "korisnik4@email.com", passwordEncoder.encode("lozinka4"), "Mika", "Mikic", gr4, setUR, null, null, null, null);
		Korisnik k5 = new Korisnik(null, "korisnik5@email.com", passwordEncoder.encode("lozinka5"), "Mila", "Milanovic", gr5, setRE, null, null, null, null);
		Korisnik k6 = new Korisnik(null, "korisnik6@email.com", passwordEncoder.encode("lozinka6"), "Sava", "Savic", gr6, setRE, null, null, null, null);
		Korisnik k7 = new Korisnik(null, "korisnik7@email.com", passwordEncoder.encode("lozinka7"), "Nikola", "Nikolic", gr1, setRE, null, null, null, null);
		Korisnik k8 = new Korisnik(null, "korisnik8@email.com", passwordEncoder.encode("lozinka8"), "Marko", "Markovic", gr2, setRE, null, null, null, null);
		Korisnik k9 = new Korisnik(null, "korisnik9@email.com", passwordEncoder.encode("lozinka9"), "Milica", "Milicic", gr3, setRE, null, null, null, null);
		Korisnik k10 = new Korisnik(null, "korisnik10@email.com", passwordEncoder.encode("lozinka10"), "Rada", "Radovanovic", gr5, setRE, null, null, null, null);
		
		// *** Autori *** //
		Korisnik k11 = new Korisnik(null, "korisnik11@email.com", passwordEncoder.encode("lozinka11"), "Sara", "Savic", gr1, setAU, null, null, null, null);
		Korisnik k12 = new Korisnik(null, "korisnik12@email.com", passwordEncoder.encode("lozinka12"), "Luka", "Lukic", gr3, setAU, null, null, null, null);
		Korisnik k13 = new Korisnik(null, "korisnik13@email.com", passwordEncoder.encode("lozinka13"), "Lazar", "Lazarevic", gr6, setAU, null, null, null, null);
		
		// *** Urednici *** //
		Korisnik k14 = new Korisnik(null, "korisnik14@email.com", passwordEncoder.encode("lozinka14"), "Sara", "Lazarevic", gr3, setUR, null, null, null, null);
		Korisnik k15 = new Korisnik(null, "korisnik15@email.com", passwordEncoder.encode("lozinka15"), "Luka", "Savic", gr4, setUR, null, null, null, null);
		Korisnik k16 = new Korisnik(null, "korisnik16@email.com", passwordEncoder.encode("lozinka16"), "Marko", "Lazarevic", gr6, setUR, null, null, null, null);
		Korisnik k17 = new Korisnik(null, "korisnik17@email.com", passwordEncoder.encode("lozinka17"), "Sara", "Savic", gr5, setUR, null, null, null, null);
		Korisnik k18 = new Korisnik(null, "korisnik18@email.com", passwordEncoder.encode("lozinka18"), "Zika", "Mikic", gr5, setUR, null, null, null, null);
		Korisnik k19 = new Korisnik(null, "korisnik19@email.com", passwordEncoder.encode("lozinka19"), "Lazar", "Milicic", gr6, setUR, null, null, null, null);
		
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
		k11 = korisnikRepository.save(k11);
		k12 = korisnikRepository.save(k12);
		k13 = korisnikRepository.save(k13);
		k14 = korisnikRepository.save(k14);
		k15 = korisnikRepository.save(k15);
		k16 = korisnikRepository.save(k16);
		k17 = korisnikRepository.save(k17);
		k18 = korisnikRepository.save(k18);
		k19 = korisnikRepository.save(k19);
		
		Autor autor1 = new Autor(k1.getId(), null, null);
		Autor autor2 = new Autor(k11.getId(), null, null);
		Autor autor3 = new Autor(k12.getId(), null, null);
		Autor autor4 = new Autor(k13.getId(), null, null);
		
		RegistrovaniKorisnik registrovaniKorisnik = new RegistrovaniKorisnik(k2.getId());
		
		Urednik urednik1 = new Urednik(k4.getId(), "Titula 1", naucneOblasti1, null);
		Urednik urednik2 = new Urednik(k14.getId(), "Titula 2", naucneOblasti2, null);
		Urednik urednik3 = new Urednik(k15.getId(), "Titula 3", naucneOblasti3, null);
		Urednik urednik4 = new Urednik(k16.getId(), "Titula 4", naucneOblasti4, null);
		Urednik urednik5 = new Urednik(k17.getId(), "Titula 5", naucneOblasti5, null);
		Urednik urednik6 = new Urednik(k18.getId(), "Titula 6", naucneOblasti3, null);
		Urednik urednik7 = new Urednik(k19.getId(), "Titula 7", naucneOblasti2, null);
		
		autor1 = autorRepository.save(autor1);
		autor2 = autorRepository.save(autor2);
		autor3 = autorRepository.save(autor3);
		autor4 = autorRepository.save(autor4);
		
		registrovaniKorisnik = registrovaniKorisnikRepository.save(registrovaniKorisnik);
		
		urednik1 = urednikRepository.save(urednik1);
		urednik2 = urednikRepository.save(urednik2);
		urednik3 = urednikRepository.save(urednik3);
		urednik4 = urednikRepository.save(urednik4);
		urednik5 = urednikRepository.save(urednik5);
		urednik6 = urednikRepository.save(urednik6);
		urednik7 = urednikRepository.save(urednik7);
		
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
		
		k1.setAutor(autor1);
		k2.setRegistrovaniKorisnik(registrovaniKorisnik);
		k3.setRecenzent(rec1);
		k4.setUrednik(urednik1);
		k5.setRecenzent(rec2);
		k6.setRecenzent(rec3);
		k7.setRecenzent(rec4);
		k8.setRecenzent(rec5);
		k9.setRecenzent(rec6);
		k10.setRecenzent(rec7);
		k11.setAutor(autor2);
		k12.setAutor(autor3);
		k13.setAutor(autor4);
		k14.setUrednik(urednik2);
		k15.setUrednik(urednik3);
		k16.setUrednik(urednik4);
		k17.setUrednik(urednik5);
		k18.setUrednik(urednik6);
		k19.setUrednik(urednik7);
		
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
		k11 = korisnikRepository.save(k11);
		k12 = korisnikRepository.save(k12);
		k13 = korisnikRepository.save(k13);
		k14 = korisnikRepository.save(k14);
		k15 = korisnikRepository.save(k15);
		k16 = korisnikRepository.save(k16);
		k17 = korisnikRepository.save(k17);
		k18 = korisnikRepository.save(k18);
		k19 = korisnikRepository.save(k19);
		
		// *** Camunda Korisnici *** //
		
		User u = identityService.newUser("undefinedUser");
		u.setFirstName("Jonh");
		u.setLastName("Doe");
		u.setEmail("undefined@email.com");
		u.setPassword("undefinedpass");
		
		User u1 = createCamundaUser(k1);
		User u2 = createCamundaUser(k2);
		User u3 =createCamundaUser(k3);
		User u4 = createCamundaUser(k4);
		User u5 = createCamundaUser(k5);
		User u6 = createCamundaUser(k6);
		User u7 = createCamundaUser(k7);
		User u8 = createCamundaUser(k8);
		User u9 = createCamundaUser(k9);
		User u10 = createCamundaUser(k10);
		User u11 = createCamundaUser(k11);
		User u12 = createCamundaUser(k12);
		User u13 = createCamundaUser(k13);
		User u14 = createCamundaUser(k14);
		User u15 = createCamundaUser(k15);
		User u16 = createCamundaUser(k16);
		User u17 = createCamundaUser(k17);
		User u18 = createCamundaUser(k18);
		User u19 = createCamundaUser(k19);
		
		identityService.saveUser(u);
		identityService.saveUser(u1);
		identityService.saveUser(u2);
		identityService.saveUser(u3);
		identityService.saveUser(u4);
		identityService.saveUser(u5);
		identityService.saveUser(u6);
		identityService.saveUser(u7);
		identityService.saveUser(u8);
		identityService.saveUser(u9);
		identityService.saveUser(u10);
		identityService.saveUser(u11);
		identityService.saveUser(u12);
		identityService.saveUser(u13);
		identityService.saveUser(u14);
		identityService.saveUser(u15);
		identityService.saveUser(u16);
		identityService.saveUser(u17);
		identityService.saveUser(u18);
		identityService.saveUser(u19);
		
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
		
		Casopis c1 = new Casopis(null, "0001-184X", "Acta herbologica", true, naucneOblasti1, 11.99, 11.99, "CAS#1#MARA",urednik1, null, recenzenti3);
		Casopis c2 = new Casopis(null, "0002-1X22", "Arhitektura i urbanizam", false, naucneOblasti2, 14.99, 14.99, "CAS#2#MARA", urednik2, null, recenzenti2);
		Casopis c3 = new Casopis(null, "0003-45X6", "Balkan Journal of Dental Medicine", true, naucneOblasti3, null, 9.99, "CAS#3#MARA", urednik3, null, recenzenti1);
		Casopis c4 = new Casopis(null, "0004-7895", "Ekonomika poljoprivrede", false, naucneOblasti4, 7.99, null, "CAS#4#MARA", urednik4, null, recenzenti3);
		Casopis c5 = new Casopis(null, "0005-7802", "Civitas", true, naucneOblasti5, null, 12.99, "CAS#5#MARA", urednik5, null, recenzenti2);
		Casopis c6 = new Casopis(null, "0006-5630", "Geonauka", true, naucneOblasti1, null, 17.99, "CAS#6#MARA", urednik6, null, recenzenti1);
		Casopis c7 = new Casopis(null, "0007-XX78", "Geographica Pannonica", true, naucneOblasti2, null, 15.99, "CAS#7#MARA", urednik1, null, recenzenti3);
		Casopis c8 = new Casopis(null, "0009-8862", "Mathematica Moravica", true, naucneOblasti3, null, 19.99, "CAS#8#MARA", urednik2, null, recenzenti1);
		Casopis c9 = new Casopis(null, "0010-1403", "Medicinski časopis", true, naucneOblasti4, null, 14.99, "CAS#9#MARA", urednik3, null, recenzenti3);
		Casopis c10 = new Casopis(null, "0012-7863", "Spatium", false, naucneOblasti5, 16.54, null, "CAS#10MARA", urednik4, null, recenzenti1);
		Casopis c11 = new Casopis(null, "0013-1452", "Sociološki pregled", true, naucneOblasti1, null, 11.99, "CAS#11MARA", urednik5, null, recenzenti3);
		Casopis c12 = new Casopis(null, "0014-8787", "Tehnika - Elektrotehnika", true, naucneOblasti3, null, 17.99, "CAS#12MARA", urednik6, null, recenzenti2);
		
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
		
		Koautor koautor1 = new Koautor(null, "Milan", "Milanovic", "milanmilanovic@email.com", "Novi Sad, Srbija");
		Koautor koautor2 = new Koautor(null, "Sonja", "Simic", "sonjasimic@email.com", "Beograd, Srbija");
		Koautor koautor3 = new Koautor(null, "Milica", "Milovanovic", "milica89@email.com", "Valjevo, Srbija");
		Koautor koautor4 = new Koautor(null, "Dragan", "Dragic", "dragance@email.com", "Nis, Srbija");
		Koautor koautor5 = new Koautor(null, "Luka", "Lukic", "lukalukic@email.com", "Novi Sad, Srbija");
		Koautor koautor6 = new Koautor(null, "Ostoja", "Ostojic", "the.ostoja@email.com", "Sremska Mitrovica, Srbija");
		Koautor koautor7 = new Koautor(null, "Predrag", "Preradovic", "preradovic@email.com", "Uzice, Srbija");
		
		koautor1 = koautorRepository.save(koautor1);
		koautor2 = koautorRepository.save(koautor2);
		koautor3 = koautorRepository.save(koautor3);
		koautor4 = koautorRepository.save(koautor4);
		koautor5 = koautorRepository.save(koautor5);
		koautor6 = koautorRepository.save(koautor6);
		koautor7 = koautorRepository.save(koautor7);
		
		List<Koautor> koautori1 = new ArrayList<>();
		koautori1.add(koautor1);
		koautori1.add(koautor3);
		koautori1.add(koautor5);
		
		List<Koautor> koautori2 = new ArrayList<>();
		koautori2.add(koautor2);
		koautori2.add(koautor4);
		
		List<Koautor> koautori3 = new ArrayList<>();
		koautori3.add(koautor6);
		koautori3.add(koautor7);
		
		RevizijaRada revizija1 = new RevizijaRada(null, "Predikcija ishoda teniskih meceva", koautori1, 
				"U radu je opisana predkcija ishoda teniskih meceva.", "predikcija, tenis, mec, loptica, Naive Bayes", LIBRARY_DIR_PATH+"\\2015 Predikcija ishoda teniskih meceva.pdf", 
				true, true, true, autor1, c1, no1, recenzenti1, null);
		
		RevizijaRada revizija2 = new RevizijaRada(null, "Цене некретнина", koautori2, 
				"Кретање цена некретнина, опширно и детаљно,", "предикција, некретнине, некретнина, кластернованје, кластер, цена, новац, стан, кућа", LIBRARY_DIR_PATH+"\\2016 Predikcija cene nekretnina.pdf", 
				true, true, true, autor2, c2, no2, recenzenti2, null);
		
		RevizijaRada revizija3 = new RevizijaRada(null, "Predikcija musterija koje ce napustiti kompaniju", koautori3, 
				"Postoji li rizik da vasi zaposleni napuste kompaniju?", "predikcija, kompanija, zaposleni, nezadovoljstvo, klaster, klasterovanje", LIBRARY_DIR_PATH+"\\2016 Predikcija Churn (musterije koje ce napustiti kompaniju).pdf", 
				true, true, true, autor3, c2, no3, recenzenti3, null);
		
		RevizijaRada revizija4 = new RevizijaRada(null, "IMDB, predikcija ranka filmova", koautori1, 
				"Metode koriscenje kako bi se izvrsila predikcija ranka filma na IMDB sajtu.", "predikcija, film, uspeh, rank, zanr, faktori", LIBRARY_DIR_PATH+"\\2016 Predikcija ranka filmova na IMDB.pdf", 
				true, true, true, autor4, c3, no4, recenzenti1, null);
		
		RevizijaRada revizija5 = new RevizijaRada(null, "Анализа кашњења авионских летова", koautori2, 
				"Анализа и прикз доминантних разлога кашњења авионских летова на свим рутама.", "анализа, кашњење, касни, авион, лет, писта", LIBRARY_DIR_PATH+"\\2017 Analiza kasnjenja avionskih letova.pdf", 
				true, true, true, autor1, c1, no5, recenzenti2, null);
		
		RevizijaRada revizija6 = new RevizijaRada(null, "Hronicne bubrezne bolesti", koautori3, 
				"Predikcija hronicnih bubreznih bolesti iz prethodnih anamneza nekog pacijenta i procena rizika razvijanja.", "predikcija, bubreg, bubrezi, bolest, anamneza, hronicno", LIBRARY_DIR_PATH+"\\2017 Chronic kidney disease.pdf", 
				true, true, true, autor2, c1, no6, recenzenti3, null);
		
		RevizijaRada revizija7 = new RevizijaRada(null, "СМС спам", koautori1, 
				"Анализа смс порука и предикција спама на основу претходне анализе.", "предикција, анализа, смс, спам", LIBRARY_DIR_PATH+"\\2017 Predikcija SMS spama.pdf", 
				true, true, true, autor3, c3, no1, recenzenti1, null);
		
		RevizijaRada revizija8 = new RevizijaRada(null, "Modul za upravljanje sertifikatima u okviru informacionog sistema za rezervaciju smeštaja", koautori2, 
				"Tema ovog rada jeste jedna iz skupa prethodno navedenih aplikacija, čija je osnovna namena generisanje, distribucija i povlačenje digitalnih sertifikata. Upotreba digitalnih sertifikata je trenutno najčešći i najpouzdaniji mehanizam za autentifikaciju učesnika u komunikaciji i zaštitu sadržaja poruka koje se šalju od neovlašćenog čitanja ili izmene.", 
				"sertifikat, digitalni, CSR, SSL, model pretnji", LIBRARY_DIR_PATH+"\\DIPL_Marija_Joksimovic_BSEP-finalno.pdf", 
				true, true, true, autor4, c2, no2, recenzenti2, null);
		
		RevizijaRada revizija9 = new RevizijaRada(null, "Smernice za pisanje diplomskog rada", koautori3, 
				"Smernice za pisanje diplomskog rada.", "diplomski, rad, smernica, smernice", LIBRARY_DIR_PATH+"\\Smernice za pisanje diplomskog rada.pdf", 
				true, true, true, autor1, c1, no3, recenzenti3, null);
		
		RevizijaRada revizija10 = new RevizijaRada(null, "Upravljanje digitalnim dokumentima, kontrolna tačka 1", koautori1, 
				"Upravljanje digitalnim dokumentima.", "digitalni, dokument, kontrolna, tacka", LIBRARY_DIR_PATH+"\\UDD-2018-2019-KT1-Marija-Joksimović-E262-2018.pdf", 
				true, true, true, autor2, c3, no4, recenzenti1, null);
		
		RevizijaRada revizija11 = new RevizijaRada(null, "Upravljanje digitalnim dokumentima, kontrolna tačka 2", koautori2, 
				"Upravljanje digitalnim dokumentima.", "digitalni, dokument, kontrolna, tacka", LIBRARY_DIR_PATH+"\\UDD-2018-2019-KT2-Marija-Joksimovic.pdf", 
				true, true, true, autor3, c2, no5, recenzenti2, null);
		
		revizija1 = revizijaRadaRepository.save(revizija1);
		revizija2 = revizijaRadaRepository.save(revizija2);
		revizija3 = revizijaRadaRepository.save(revizija3);
		revizija4 = revizijaRadaRepository.save(revizija4);
		revizija5 = revizijaRadaRepository.save(revizija5);
		revizija6 = revizijaRadaRepository.save(revizija6);
		revizija7 = revizijaRadaRepository.save(revizija7);
		revizija8 = revizijaRadaRepository.save(revizija8);
		revizija9 = revizijaRadaRepository.save(revizija9);
		revizija10 = revizijaRadaRepository.save(revizija10);
		revizija11 = revizijaRadaRepository.save(revizija11);
		
		NaucniRad rad1 = new NaucniRad(revizija1, null, "RAD#1#MARA");
		NaucniRad rad2 = new NaucniRad(revizija2, 2.99, "RAD#2#MARA");
		NaucniRad rad3 = new NaucniRad(revizija3, 3.99, "RAD#3#MARA");
		NaucniRad rad4 = new NaucniRad(revizija4, null, "RAD#4#MARA");
		NaucniRad rad5 = new NaucniRad(revizija5, null, "RAD#5#MARA");
		NaucniRad rad6 = new NaucniRad(revizija6, null, "RAD#6#MARA");
		NaucniRad rad7 = new NaucniRad(revizija7, null, "RAD#7#MARA");
		NaucniRad rad8 = new NaucniRad(revizija8, null, "RAD#8#MARA");
		NaucniRad rad9 = new NaucniRad(revizija9, null, "RAD#9#MARA");
		NaucniRad rad10 = new NaucniRad(revizija10, null, "RAD10#MARA");
		NaucniRad rad11 = new NaucniRad(revizija11, null, "RAD11#MARA");
		
		rad1 = naucniRadRepository.save(rad1);
		rad2 = naucniRadRepository.save(rad2);
		rad3 = naucniRadRepository.save(rad3);
		rad4 = naucniRadRepository.save(rad4);
		rad5 = naucniRadRepository.save(rad5);
		rad6 = naucniRadRepository.save(rad6);
		rad7 = naucniRadRepository.save(rad7);
		rad8 = naucniRadRepository.save(rad8);
		rad9 = naucniRadRepository.save(rad9);
		rad10 = naucniRadRepository.save(rad10);
		rad11 = naucniRadRepository.save(rad11);
		
		Set<NaucniRad> radovi1 = new HashSet<>();
		radovi1.add(rad1);
		radovi1.add(rad5);
		radovi1.add(rad6);
		
		Set<NaucniRad> radovi2 = new HashSet<>();
		radovi2.add(rad2);
		radovi2.add(rad3);
		
		Set<NaucniRad> radovi3 = new HashSet<>();
		radovi3.add(rad4);
		radovi3.add(rad7);
	
		Izdanje izdanje1 = new Izdanje(null, new Date(System.currentTimeMillis()), c1, "Acta herbologica Izdanje 1", null, "IZD#1#MARA", naucneOblasti1, radovi1);
		Izdanje izdanje2 = new Izdanje(null, new Date(System.currentTimeMillis()), c2, "Arhitektura i urbanizam Izdanje 1", 2.99, "IZD#2#MARA", naucneOblasti2, radovi2);
		Izdanje izdanje3 = new Izdanje(null, new Date(System.currentTimeMillis()), c3, "Balkan Journal of Dental Medicine Izdanje 1", null, "IZD#3#MARA", naucneOblasti3, radovi3);
		
		izdanje1 = izdanjeRepository.save(izdanje1);
		izdanje2 = izdanjeRepository.save(izdanje2);
		izdanje3 = izdanjeRepository.save(izdanje3);
		
		PDFHandler pdfHandler = new PDFHandler(); 
		
		autor1.setRadovi(new HashSet<NaucniRad>());
		autor2.setRadovi(new HashSet<NaucniRad>());
		autor3.setRadovi(new HashSet<NaucniRad>());
		autor4.setRadovi(new HashSet<NaucniRad>());
		
		autor1.setRevizije(new HashSet<RevizijaRada>());
		autor2.setRevizije(new HashSet<RevizijaRada>());
		autor3.setRevizije(new HashSet<RevizijaRada>());
		autor4.setRevizije(new HashSet<RevizijaRada>());
		
		autor1.getRadovi().add(rad1);
		autor1.getRadovi().add(rad5);
		autor1.getRadovi().add(rad9);
		
		autor2.getRadovi().add(rad2);
		autor2.getRadovi().add(rad6);
		autor2.getRadovi().add(rad10);
		
		autor3.getRadovi().add(rad3);
		autor3.getRadovi().add(rad7);
		autor3.getRadovi().add(rad11);
		
		autor4.getRadovi().add(rad4);
		autor4.getRadovi().add(rad8);
		
		autor1.getRevizije().add(revizija1);
		autor1.getRevizije().add(revizija5);
		autor1.getRevizije().add(revizija9);
		
		autor2.getRevizije().add(revizija2);
		autor2.getRevizije().add(revizija6);
		autor2.getRevizije().add(revizija10);
		
		autor3.getRevizije().add(revizija3);
		autor3.getRevizije().add(revizija7);
		autor3.getRevizije().add(revizija11);
		
		autor4.getRevizije().add(revizija4);
		autor4.getRevizije().add(revizija8);
		
		autor1 = autorRepository.save(autor1);
		autor2 = autorRepository.save(autor2);
		autor3 = autorRepository.save(autor3);
		autor4 = autorRepository.save(autor4);
		
		IndexUnit iu1 = new IndexUnit(rad1, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad1.getPutanja())), recenzentiI1, noi1);
		IndexUnit iu2 = new IndexUnit(rad2, k11.getIme()+" "+k11.getPrezime(), pdfHandler.getText(new File(rad2.getPutanja())), recenzentiI2, noi2);
		IndexUnit iu3 = new IndexUnit(rad3, k12.getIme()+" "+k12.getPrezime(), pdfHandler.getText(new File(rad3.getPutanja())), recenzentiI3, noi3);
		IndexUnit iu4 = new IndexUnit(rad4, k13.getIme()+" "+k13.getPrezime(), pdfHandler.getText(new File(rad4.getPutanja())), recenzentiI1, noi4);
		IndexUnit iu5 = new IndexUnit(rad5, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad5.getPutanja())), recenzentiI2, noi5);
		IndexUnit iu6 = new IndexUnit(rad6, k11.getIme()+" "+k11.getPrezime(), pdfHandler.getText(new File(rad6.getPutanja())), recenzentiI3, noi6);
		IndexUnit iu7 = new IndexUnit(rad7, k12.getIme()+" "+k12.getPrezime(), pdfHandler.getText(new File(rad7.getPutanja())), recenzentiI1, noi1);
		IndexUnit iu8 = new IndexUnit(rad8, k13.getIme()+" "+k13.getPrezime(), pdfHandler.getText(new File(rad8.getPutanja())), recenzentiI2, noi2);
		IndexUnit iu9 = new IndexUnit(rad9, k1.getIme()+" "+k1.getPrezime(), pdfHandler.getText(new File(rad9.getPutanja())), recenzentiI3, noi3);
		IndexUnit iu10 = new IndexUnit(rad10, k11.getIme()+" "+k11.getPrezime(), pdfHandler.getText(new File(rad10.getPutanja())), recenzentiI1, noi4);
		IndexUnit iu11 = new IndexUnit(rad11, k12.getIme()+" "+k12.getPrezime(), pdfHandler.getText(new File(rad11.getPutanja())), recenzentiI2, noi5);
		
		iu1 = indexUnitRepository.index(iu1);
		iu2 = indexUnitRepository.index(iu2);
		iu3 = indexUnitRepository.index(iu3);
		iu4 = indexUnitRepository.index(iu4);
		iu5 = indexUnitRepository.index(iu5);
		iu6 = indexUnitRepository.index(iu6);
		iu7 = indexUnitRepository.index(iu7);
		iu8 = indexUnitRepository.index(iu8);
		iu9 = indexUnitRepository.index(iu9);
		iu10 = indexUnitRepository.index(iu10);
		iu11 = indexUnitRepository.index(iu11);
	
	}
	
	private User createCamundaUser(Korisnik k) {
		User retVal = identityService.newUser(k.getId().toString());
		retVal.setEmail(k.getEmail());
		retVal.setFirstName(k.getIme());
		retVal.setLastName(k.getPrezime());
		retVal.setPassword(k.getLozinka());
		return retVal;
	}
}
