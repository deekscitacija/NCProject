package com.ftn.nc.NCBackend.web.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ftn.nc.NCBackend.elastic.dto.QueryDTO;
import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;

public interface SearchService {
	
	public Page<IndexUnit> executeSearch(QueryDTO searchParams);
	
	public Page<IndexUnit> executeSearchAll(QueryDTO searchParams);

}
