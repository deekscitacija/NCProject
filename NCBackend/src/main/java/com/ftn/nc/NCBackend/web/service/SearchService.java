package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ftn.nc.NCBackend.elastic.dto.IndexUnitDTO;
import com.ftn.nc.NCBackend.elastic.dto.QueryDTO;
import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.RecenzentInfo;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Korisnik;

public interface SearchService {
	
	public Page<IndexUnit> executeSearch(QueryDTO searchParams);
	
	public Page<IndexUnit> executeSearchAll(QueryDTO searchParams);
	
	public List<IndexUnit> moreLikeThis(String documentId, int pageNum);
	
	public List<RecenzentInfo> geoSearch(double lat, double lon);
	
	public IndexUnit saveIndexUnit(IndexUnit newPaper);
	
	public boolean uploadAndIndex(IndexUnitDTO paperInfo, Korisnik autor, Casopis casopis);

}
