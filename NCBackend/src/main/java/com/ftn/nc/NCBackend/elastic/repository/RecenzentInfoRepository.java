package com.ftn.nc.NCBackend.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.nc.NCBackend.elastic.model.RecenzentInfo;

public interface RecenzentInfoRepository extends ElasticsearchRepository<RecenzentInfo, String>{

}
