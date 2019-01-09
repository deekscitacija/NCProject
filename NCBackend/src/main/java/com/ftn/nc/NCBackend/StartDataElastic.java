package com.ftn.nc.NCBackend;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;
import com.ftn.nc.NCBackend.elastic.model.RecenzentInfo;
import com.ftn.nc.NCBackend.elastic.repository.IndexUnitRepository;
import com.ftn.nc.NCBackend.elastic.repository.NaucnaOblastInfoRepository;
import com.ftn.nc.NCBackend.elastic.repository.RecenzentInfoRepository;

@Component
public class StartDataElastic {
	
	
	@Autowired
	private IndexUnitRepository indexUnitRepository;
	
	@Autowired
	private NaucnaOblastInfoRepository naucnaOblastInfoRepository;
	
	@Autowired
	private RecenzentInfoRepository recenzentInfoRepository;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	public StartDataElastic() {
		
	}
	
	@PostConstruct
	private void init() {
		
		NaucnaOblastInfo no1 = new NaucnaOblastInfo("1", "Matematika", "MAT");
		NaucnaOblastInfo no2 = new NaucnaOblastInfo("2", "Fizika", "FIZ");
		NaucnaOblastInfo no3 = new NaucnaOblastInfo("3", "Psihologija", "PSI");
		NaucnaOblastInfo no4 = new NaucnaOblastInfo("4", "Geografija", "GEO");
		NaucnaOblastInfo no5 = new NaucnaOblastInfo("5", "Biologija", "BIO");
		NaucnaOblastInfo no6 = new NaucnaOblastInfo("6", "Hemija", "HEM");
		
		no1 = naucnaOblastInfoRepository.save(no1);
		no2 = naucnaOblastInfoRepository.save(no2);
		no3 = naucnaOblastInfoRepository.save(no3);
		no4 = naucnaOblastInfoRepository.save(no4);
		no5 = naucnaOblastInfoRepository.save(no5);
		no6 = naucnaOblastInfoRepository.save(no6);
		
		List<NaucnaOblastInfo> nol1 = new ArrayList<>();
		nol1.add(no1);
		nol1.add(no2);
		nol1.add(no3);
		nol1.add(no6);
		
		List<NaucnaOblastInfo> nol2 = new ArrayList<>();
		nol2.add(no2);
		nol2.add(no3);
		nol2.add(no4);
		nol2.add(no6);
		
		List<NaucnaOblastInfo> nol3 = new ArrayList<>();
		nol3.add(no1);
		nol3.add(no4);
		nol3.add(no5);
		nol3.add(no6);
		
		IndexUnit iu1 = new IndexUnit("1", "Rad o necem", "Pera Peric, Mika Mikic, Zika Zikic", "nesto kuca cvece solja", "Ima ovde svega...", "Mile Milic", 
				"Nacionalna Geografija", "Ovde se stvarno svasta moze naci. Ima jos tako necega, sta ja znam. Procitaj i vidi.", true, null, nol1);
		
		IndexUnit iu2 = new IndexUnit("2", "Rad o svemu i svacemu", "Mika Mikic, Zika Zikic, Sima Simic", "casa sveska krema lampa", "Probaj da dodjes do kraja.", "Sonja Simic", 
				"Patka Patka", "Morate stvarno ovo procitati tacno onako kako je napiasno. Kreni od pocetka, pa kroz sredinu i idemo polako.", false, null, nol2);
		
		IndexUnit iu3 = new IndexUnit("3", "Patuljak u kapici", "Mira Maric, Luka Lukic", "olovka bandera prozor vrata", "Probaj da procitas od svega po malo", "Zika Zikic", 
				"Nacionalna Geografija", "Hajde da vidim sta ovde uopste u opis da stavim?", true, null, nol3);
		
		IndexUnit iu4 = new IndexUnit("4", "Biljke", "Mira Maric, Mika Mikic", "olovka bandera prozor vrata", "Probaj da procitas od svega po malo", "Zika Zikic", 
				"Patka Patka", "Hajde da vidim sta ovde uopste u opis da stavim?", true, null, nol1);
		
		IndexUnit iu5 = new IndexUnit("5", "Biljke2", "Mira Maric, Mika Mikic", "olovka bandera prozor vrata", "Probaj da procitas od svega po malo", "Zika Zikic", 
				"Nacionalna Geografija", "Hajde da vidim sta ovde uopste u opis da stavim?", true, null, nol1);
		
		IndexUnit iu6 = new IndexUnit("6", "Sveska", "Mira Maric, Mika Mikic", "olovka bandera prozor vrata", "Probaj da procitas od svega po malo", "Mile Milic", 
				"Nacionalna Geografija", "Hajde da vidim sta ovde uopste u opis da stavim?", true, null, nol2);
		
		indexUnitRepository.save(iu1);
		indexUnitRepository.save(iu2);
		indexUnitRepository.save(iu3);
		indexUnitRepository.save(iu4);
		indexUnitRepository.save(iu5);
		indexUnitRepository.save(iu6);
		
	}
	
	

}
