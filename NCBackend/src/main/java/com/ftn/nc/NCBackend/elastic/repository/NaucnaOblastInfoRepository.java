package com.ftn.nc.NCBackend.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;

public interface NaucnaOblastInfoRepository extends ElasticsearchRepository<NaucnaOblastInfo, String>{

}
