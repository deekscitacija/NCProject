package com.ftn.nc.NCBackend.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ftn.nc.NCBackend.elastic.model.IndexUnit;


public interface IndexUnitRepository extends ElasticsearchRepository<IndexUnit, String>{

}
