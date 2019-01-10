package com.ftn.nc.NCBackend.web.service.impl;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.elastic.dto.QueryDTO;
import com.ftn.nc.NCBackend.elastic.dto.QueryParamDTO;
import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;
import com.ftn.nc.NCBackend.elastic.resultMappers.ContentResultMapper;
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
			queryParams.must(QueryBuilders.commonTermsQuery("autor", autor));
		}
		
		if(casopis != null) {
			queryParams.must(QueryBuilders.commonTermsQuery("casopis", casopis));
		}		
		
		if(naslov != null) {
			queryParams.must(QueryBuilders.commonTermsQuery("naslov", naslov));
		}		
		
		if(tekst != null) {
			queryParams.must(QueryBuilders.commonTermsQuery("tekst", tekst));
		}
		
		if(kljucne != null) {
			queryParams.must(QueryBuilders.commonTermsQuery("kljucne", kljucne));
		}
		
		SearchQuery theQuery = searchQueryBuilder.withQuery(queryParams).withHighlightFields(
	            new HighlightBuilder.Field("tekst")
	                .preTags("<b>")
	                .postTags("</b>")
	                .numOfFragments(10)
	                .fragmentSize(250)
	    ).build();
		
		if(tekst != null) {
			return elasticsearchTemplate.queryForPage(theQuery, IndexUnit.class, new ContentResultMapper("tekst"));
		}
		
		return elasticsearchTemplate.queryForPage(theQuery, IndexUnit.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Page<IndexUnit> executeSearch(QueryDTO searchParams) {
		
		boolean textSearch = false;
		int pageNum = 1;
		
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
		BoolQueryBuilder queryParams = QueryBuilders.boolQuery();
		
		for(QueryParamDTO searchParam : searchParams.getParams()) {
			String key = searchParam.getKey();
			String value = searchParam.getValue();
			if((key != null && value != null)) {
				if((key.equals("autor") || key.equals("casopis") || key.equals("naslov") || key.equals("tekst") || key.equals("kljucne"))){
					queryParams.must(QueryBuilders.commonTermsQuery(key, value));
					if(key.equals("tekst")) {
						textSearch = true;
					}
				}
				
				if(key.equals("pageNum")) {
					pageNum = Integer.parseInt(value);
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
		
		if(textSearch) {
			return elasticsearchTemplate.queryForPage(theQuery, IndexUnit.class, new ContentResultMapper("tekst"));
		}
		
		return elasticsearchTemplate.queryForPage(theQuery, IndexUnit.class);
	}

	

}