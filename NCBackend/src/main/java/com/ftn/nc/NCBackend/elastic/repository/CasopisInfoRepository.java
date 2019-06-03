package com.ftn.nc.NCBackend.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.nc.NCBackend.elastic.model.CasopisInfo;

public interface CasopisInfoRepository extends ElasticsearchRepository<CasopisInfo, String>{

}
