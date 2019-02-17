package com.ftn.nc.NCBackend.web.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder.Item;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.nc.NCBackend.elastic.dto.IndexUnitDTO;
import com.ftn.nc.NCBackend.elastic.dto.QueryDTO;
import com.ftn.nc.NCBackend.elastic.dto.QueryParamDTO;
import com.ftn.nc.NCBackend.elastic.handler.PDFHandler;
import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;
import com.ftn.nc.NCBackend.elastic.model.RecenzentInfo;
import com.ftn.nc.NCBackend.elastic.repository.IndexUnitRepository;
import com.ftn.nc.NCBackend.elastic.repository.NaucnaOblastInfoRepository;
import com.ftn.nc.NCBackend.elastic.repository.RecenzentInfoRepository;
import com.ftn.nc.NCBackend.elastic.resultMappers.ContentResultMapper;
import com.ftn.nc.NCBackend.helpClasses.PDFUtils;
import com.ftn.nc.NCBackend.web.dto.KorisnikDTO;
import com.ftn.nc.NCBackend.web.enums.SearchParamType;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Koautor;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.NaucniRad;
import com.ftn.nc.NCBackend.web.model.Recenzent;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.repository.AutorRepository;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;
import com.ftn.nc.NCBackend.web.repository.NaucniRadRepository;
import com.ftn.nc.NCBackend.web.repository.RecenzentRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;
import com.ftn.nc.NCBackend.web.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private IndexUnitRepository indexUnitRepository;
	
	@Autowired
	private NaucnaOblastInfoRepository naucnaOblastInfoRepository;
	
	@Autowired
	private NaucniRadRepository naucniRadRepository;
	
	@Autowired
	private RevizijaRadaRepository revizijaRadaRepository;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private RecenzentRepository recenzentRepository;
	
	@Autowired
	private RecenzentInfoRepository recenzentInfoRepository;
	
	@Autowired
	private AutorRepository autorRepository;
	

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
					buildNestedParam(queryParams, value, searchParam.getParamType(), searchParam.isPhraseQuery());
				}else {
					buildParam(queryParams, key, value, searchParam.getParamType(), searchParam.isPhraseQuery());
				}
			}
		}
		
		return elasticsearchTemplate.queryForPage(buildQueryWithHilight(searchQueryBuilder, queryParams, pageNum), IndexUnit.class, new ContentResultMapper());
	}
	
	@Override
	public Page<IndexUnit> executeSearchAll(QueryDTO searchParams) {
		
		int pageNum = searchParams.getPageNum();
		String queryString = searchParams.getParams().get(0).getValue();
		
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
		BoolQueryBuilder queryParams = new BoolQueryBuilder();
		
		queryParams.should(QueryBuilders.commonTermsQuery("tekst", queryString));
		queryParams.should(QueryBuilders.commonTermsQuery("naslov", queryString));
		queryParams.should(QueryBuilders.commonTermsQuery("autor", queryString));
		queryParams.should(QueryBuilders.commonTermsQuery("casopis", queryString));
		queryParams.should(QueryBuilders.commonTermsQuery("koautori", queryString));
		queryParams.should(QueryBuilders.commonTermsQuery("kljucne", queryString));
		queryParams.should(QueryBuilders.commonTermsQuery("apstrakt", queryString));
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("naucnaOblast", boolQuery.should(
				QueryBuilders.commonTermsQuery("naucnaOblast.naziv", queryString)), ScoreMode.Total);
		
		queryParams.should(nestedQuery);
		
		return elasticsearchTemplate.queryForPage(buildQueryWithHilight(searchQueryBuilder, queryParams, pageNum), IndexUnit.class, new ContentResultMapper());
	}
	
	@Override
	public List<IndexUnit> moreLikeThis(String documentId, int pageNum) {
		
		String[] fields = new String[1];
		String[] tekst = new String[1];
		Item[] items = new Item[1];
		
		IndexUnit indexUnit = null;
		
		try {
			indexUnit = indexUnitRepository.findById(documentId).get();
		}catch(Exception e) {
			return null;
		}
		
		fields[0] = "tekst";
		tekst[0] = indexUnit.getTekst();
		
		Item searchIn = new Item("naucnacentrala", "rad", indexUnit.getId());
		items[0] = searchIn;
		
		MoreLikeThisQueryBuilder moreLikeThisQuery = QueryBuilders.moreLikeThisQuery(fields, tekst, items)
				.minDocFreq(5)
				.maxDocFreq(25)
				.minTermFreq(5)
				.minimumShouldMatch("75%");
		
		SearchQuery theQuery = new NativeSearchQueryBuilder().withQuery(moreLikeThisQuery).build();
		
		return elasticsearchTemplate.queryForList(theQuery, IndexUnit.class);
	}

	@Override
	public List<RecenzentInfo> geoSearch(double lat, double lon) {
		
		QueryBuilder filter = QueryBuilders.geoDistanceQuery("lokacija")
				.point(lat, lon)
				.distance(100, DistanceUnit.KILOMETERS);
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.mustNot(filter);
		
		SearchQuery theQuery = new NativeSearchQueryBuilder().withQuery(boolQuery).build();
		
		return elasticsearchTemplate.queryForList(theQuery, RecenzentInfo.class);
	}
	
	@Override
	public IndexUnit saveIndexUnit(IndexUnit newPaper) {
		
		return indexUnitRepository.save(newPaper);
	}
	
	@Override
	public boolean uploadAndIndex(IndexUnitDTO paperInfo, Korisnik autor, Casopis casopis) {
		
		if(paperInfo == null) {
			return false;
		}
		
		try {
			indexUploadedFile(paperInfo, autor, casopis);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private void buildParam(BoolQueryBuilder queryParams, String key, String value, SearchParamType paramType, boolean isPhraseQuery) {
		
		if(paramType.equals(SearchParamType.OR)) {
			if(isPhraseQuery) {
				queryParams.should(QueryBuilders.matchPhraseQuery(key, value));
			}else {
				queryParams.should(QueryBuilders.commonTermsQuery(key, value));
			}
		}else if(paramType.equals(SearchParamType.AND)) {
			if(isPhraseQuery){
				queryParams.must(QueryBuilders.matchPhraseQuery(key, value));
			}else {
				queryParams.must(QueryBuilders.commonTermsQuery(key, value));
			}
		}else if(paramType.equals(SearchParamType.MUST_NOT)) {
			if(isPhraseQuery){
				queryParams.mustNot(QueryBuilders.matchPhraseQuery(key, value));
			}else {
				queryParams.mustNot(QueryBuilders.commonTermsQuery(key, value));
			}
		}
	}
	
	private void buildNestedParam(BoolQueryBuilder queryParams, String value, SearchParamType paramType, boolean isPhraseQuery) {
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		NestedQueryBuilder nestedQuery = null;
		
		if(paramType.equals(SearchParamType.OR)) {
			if(isPhraseQuery) {
				nestedQuery = QueryBuilders.nestedQuery("naucnaOblast", boolQuery.should(QueryBuilders.matchPhraseQuery("naucnaOblast.naziv", value)), ScoreMode.None);
			}else {
				nestedQuery = QueryBuilders.nestedQuery("naucnaOblast", boolQuery.should(QueryBuilders.commonTermsQuery("naucnaOblast.naziv", value)), ScoreMode.None);
			}
			queryParams.should(nestedQuery);
			
		}else if(paramType.equals(SearchParamType.AND)){
			if(isPhraseQuery) {
				nestedQuery = QueryBuilders.nestedQuery("naucnaOblast", boolQuery.must(QueryBuilders.matchPhraseQuery("naucnaOblast.naziv", value)), ScoreMode.None);
			}else {
				nestedQuery = QueryBuilders.nestedQuery("naucnaOblast", boolQuery.must(QueryBuilders.commonTermsQuery("naucnaOblast.naziv", value)), ScoreMode.None);
			}
			queryParams.must(nestedQuery);
		
		}else if(paramType.equals(SearchParamType.MUST_NOT)){
			if(isPhraseQuery) {
				nestedQuery = QueryBuilders.nestedQuery("naucnaOblast", boolQuery.mustNot(QueryBuilders.matchPhraseQuery("naucnaOblast.naziv", value)), ScoreMode.None);
			}else {
				nestedQuery = QueryBuilders.nestedQuery("naucnaOblast", boolQuery.mustNot(QueryBuilders.commonTermsQuery("naucnaOblast.naziv", value)), ScoreMode.None);
			}
			queryParams.mustNot(nestedQuery);
		}
		
	}
	
	private SearchQuery buildQueryWithHilight(NativeSearchQueryBuilder searchQueryBuilder, BoolQueryBuilder queryParams, int pageNum) {
		
		return searchQueryBuilder.withQuery(queryParams).withHighlightFields(
	            new HighlightBuilder.Field("tekst")
	                .preTags("<b>")
	                .postTags("</b>")
	                .numOfFragments(1)
	                .fragmentSize(250)
	    ).withPageable(PageRequest.of(pageNum-1, 3)).build();
	}
	
	private void indexUploadedFile(IndexUnitDTO paperInfo, Korisnik autor, Casopis casopis) throws IOException{
    	
    	for (MultipartFile file : paperInfo.getFajlovi()) {

            if (file.isEmpty()) {
                continue;
            }
            
            String filePath = PDFUtils.saveUploadedFile(file, PDFUtils.LIBRARY_DIR_PATH);
            if(filePath != null){
            	PDFHandler pdfHandler = new PDFHandler();
            	String tekst = pdfHandler.getText(PDFUtils.getResourceFilePath(filePath));
            	
            	List<NaucnaOblastInfo> naucneOblasti = new ArrayList<NaucnaOblastInfo>();
            	List<RecenzentInfo> recenzentiInfo = new ArrayList<RecenzentInfo>();
            	List<Recenzent> recenzenti = new ArrayList<Recenzent>();
            	
            	for(NaucnaOblast naucna : paperInfo.getNaucneOblasti()) {
            		NaucnaOblastInfo noi = naucnaOblastInfoRepository.findById(naucna.getId().toString()).get();
            		naucneOblasti.add(noi);
            	}
            	
            	for(KorisnikDTO recenzentData : paperInfo.getRecenzenti()) {
            		Korisnik k = korisnikRepository.findByEmail(recenzentData.getEmail());
            		Recenzent r = recenzentRepository.getOne(k.getId());
            		RecenzentInfo recenzentInfo = recenzentInfoRepository.findById(k.getId().toString()).get();
            		recenzentiInfo.add(recenzentInfo);
            		recenzenti.add(r);
            	}
            	
            	RevizijaRada revizija = new RevizijaRada(null, 
            			paperInfo.getNaslov(), 
            			new ArrayList<Koautor>(), 
            			paperInfo.getApstrakt(),
            			paperInfo.getKljucne(), 
            			filePath, 
            			true, true, true, 
            			autorRepository.findById(autor.getId()).get(), 
            			casopis, 
            			paperInfo.getNaucneOblasti().get(0),
            			null, null, null);
            	
            	
            	revizija = revizijaRadaRepository.save(revizija);
            	
            	NaucniRad naucniRad = new NaucniRad(revizija, null, "");
            	naucniRad.setNaucnaOblast(revizija.getNaucnaOblast());
            	naucniRad = naucniRadRepository.save(naucniRad);
            	
            	IndexUnit newPaper = new IndexUnit(naucniRad.getId().toString(), paperInfo, tekst, autor, casopis, recenzentiInfo, naucneOblasti.get(0));
            	indexUnitRepository.index(newPaper);
            }
    	}
    }
	
}
