package com.ftn.nc.NCBackend.web.service.impl;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.elastic.dto.QueryDTO;
import com.ftn.nc.NCBackend.elastic.dto.QueryParamDTO;
import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.resultMappers.ContentResultMapper;
import com.ftn.nc.NCBackend.web.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	

	@SuppressWarnings("deprecation")
	@Override
	public Page<IndexUnit> executeSearch(QueryDTO searchParams) {
		
		int pageNum = searchParams.getPageNum();
		
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
		BoolQueryBuilder queryParams = QueryBuilders.boolQuery();
		
		for(QueryParamDTO searchParam : searchParams.getParams()) {
			String key = searchParam.getKey();
			String value = searchParam.getValue();
			if((key != null && value != null)) {
				
				if(key.equals("naucna")) {
					buildNestedParam(queryParams, value, searchParam.isOptional(), searchParam.isPhraseQuery());
				}else {
					buildParam(queryParams, key, value, searchParam.isOptional(), searchParam.isPhraseQuery());
				}
			}
		}
		
		SearchQuery theQuery = searchQueryBuilder.withQuery(queryParams).withHighlightFields(
	            new HighlightBuilder.Field("tekst")
	                .preTags("<b>")
	                .postTags("</b>")
	                .numOfFragments(10)
	                .fragmentSize(250)
	    ).withPageable(new PageRequest(pageNum-1, 3))
		.build();
		
		return elasticsearchTemplate.queryForPage(theQuery, IndexUnit.class, new ContentResultMapper());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Page<IndexUnit> executeSearchAll(QueryDTO searchParams) {
		
		int pageNum = searchParams.getPageNum();
		String queryString = searchParams.getParams().get(0).getValue();
		
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
		BoolQueryBuilder queryParams = new BoolQueryBuilder();

		queryParams.should(QueryBuilders.queryStringQuery("*"+queryString+"*")
	    		.defaultOperator(Operator.OR)
	    		.analyzeWildcard(true));
		
	    SearchQuery theQuery = searchQueryBuilder.withQuery(queryParams).withHighlightFields(
	            new HighlightBuilder.Field("tekst")
	                .preTags("<b>")
	                .postTags("</b>")
	                .numOfFragments(10)
	                .fragmentSize(250)
	    ).withPageable(new PageRequest(pageNum-1, 3))
		.build();
		
		return elasticsearchTemplate.queryForPage(theQuery, IndexUnit.class, new ContentResultMapper());
	}

	
	private void buildParam(BoolQueryBuilder queryParams, String key, String value, boolean isOptional, boolean isPhraseQuery) {
		
		if(isOptional) {
			if(isPhraseQuery) {
				queryParams.should(QueryBuilders.matchPhraseQuery(key, value));
			}else {
				queryParams.should(QueryBuilders.commonTermsQuery(key, value));
			}
		}else {
			if(isPhraseQuery){
				queryParams.must(QueryBuilders.matchPhraseQuery(key, value));
			}else {
				queryParams.must(QueryBuilders.commonTermsQuery(key, value));
			}
		}
	}
	
	private void buildNestedParam(BoolQueryBuilder queryParams, String value, boolean isOptional, boolean isPhraseQuery) {
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		NestedQueryBuilder nestedQuery = null;
		
		if(isOptional) {
			if(isPhraseQuery) {
				nestedQuery = QueryBuilders.nestedQuery("naucneOblasti", boolQuery.should(QueryBuilders.matchPhraseQuery("naucneOblasti.naziv", value)), ScoreMode.None);
			}else {
				nestedQuery = QueryBuilders.nestedQuery("naucneOblasti", boolQuery.should(QueryBuilders.commonTermsQuery("naucneOblasti.naziv", value)), ScoreMode.None);
			}
			queryParams.should(nestedQuery);
		}else {
			if(isPhraseQuery) {
				nestedQuery = QueryBuilders.nestedQuery("naucneOblasti", boolQuery.must(QueryBuilders.matchPhraseQuery("naucneOblasti.naziv", value)), ScoreMode.None);
			}else {
				nestedQuery = QueryBuilders.nestedQuery("naucneOblasti", boolQuery.must(QueryBuilders.commonTermsQuery("naucneOblasti.naziv", value)), ScoreMode.None);
			}
			queryParams.must(nestedQuery);
		}
		
		
	}

}
