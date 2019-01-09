package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;

public interface SearchService {
	
	public Page<IndexUnit> executeSearch(int pageNum, String autor, String casopis, String naslov, String kljucne, String tekst, List<NaucnaOblastInfo> naucne);

}
