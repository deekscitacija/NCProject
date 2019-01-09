package com.ftn.nc.NCBackend.web.service.impl;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;
import com.ftn.nc.NCBackend.web.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public Page<IndexUnit> executeSearch(int pageNum, String autor, String casopis, String naslov, String kljucne, String tekst,
			List<NaucnaOblastInfo> naucne) {
		
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
		BoolQueryBuilder queryParams = QueryBuilders.boolQuery();
	
		if(autor != null) {
			queryParams.should(QueryBuilders.matchQuery("autor", autor).operator(Operator.AND));
		}
		
		if(casopis != null) {
			queryParams.should(QueryBuilders.matchQuery("nazivCasopisa", casopis).operator(Operator.AND));
		}		
		
		if(naslov != null) {
			queryParams.should(QueryBuilders.matchQuery("naslov", naslov).operator(Operator.AND));
		}		
		
		return elasticsearchTemplate.queryForPage(searchQueryBuilder.withQuery(queryParams).build(), IndexUnit.class);
	}

	

}
