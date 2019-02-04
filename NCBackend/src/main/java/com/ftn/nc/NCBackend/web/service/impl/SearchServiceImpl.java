package com.ftn.nc.NCBackend.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.MoreLikeThisQuery;
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
import com.ftn.nc.NCBackend.web.dto.KorisnikDTO;
import com.ftn.nc.NCBackend.web.model.Casopis;
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
	
	public static final String LIBRARY_DIR_PATH = "D:\\TheMara\\Master PRNiI\\Naucna Centrala\\Biblioteka"; 
	
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

		queryParams.should(QueryBuilders.queryStringQuery("*"+queryString+"*").analyzeWildcard(true)
                .field("tekst", 2.0f).field("naslov", 2.0f).field("autor", 2.0f).field("casopis", 1.5f)
                .field("koautori").field("kljucne").field("apstrakt"));
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("naucneOblasti", boolQuery.should(QueryBuilders.queryStringQuery("*"+queryString+"*")
						.analyzeWildcard(true).field("naucneOblasti.naziv")), ScoreMode.None);
		
		queryParams.should(nestedQuery);
		
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

	@SuppressWarnings("deprecation")
	@Override
	public Page<IndexUnit> moreLikeThis(String documentId, int pageNum) {
		
		MoreLikeThisQuery moreLikeThisQuery = new MoreLikeThisQuery();
		moreLikeThisQuery.setIndexName("naucnacentrala");
		moreLikeThisQuery.setType("rad");
		moreLikeThisQuery.setId(documentId);
		moreLikeThisQuery.addFields("tekst");
		moreLikeThisQuery.setMinDocFreq(1);
		moreLikeThisQuery.setMaxQueryTerms(25);
		moreLikeThisQuery.setPageable(new PageRequest(pageNum-1, 3));
		
		return elasticsearchTemplate.moreLikeThis(moreLikeThisQuery, IndexUnit.class);
	}

	@Override
	public List<RecenzentInfo> geoSearch(double lat, double lon) {
		
		QueryBuilder filter = QueryBuilders.geoDistanceQuery("lokacija")
				.point(lat, lon)
				.distance(100, DistanceUnit.KILOMETERS);
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.mustNot(filter);
		
		SearchQuery theQuery =new NativeSearchQueryBuilder().withQuery(boolQuery).build();
		
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
	
	private void indexUploadedFile(IndexUnitDTO paperInfo, Korisnik autor, Casopis casopis) throws IOException{
    	
    	for (MultipartFile file : paperInfo.getFajlovi()) {

            if (file.isEmpty()) {
                continue;
            }
            
            String filePath = saveUploadedFile(file);
            if(filePath != null){
            	PDFHandler pdfHandler = new PDFHandler();
            	String tekst = pdfHandler.getText(getResourceFilePath(filePath));
            	
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
            			paperInfo.getKoautori(), 
            			paperInfo.getApstrakt(),
            			paperInfo.getKljucne(), 
            			filePath, 
            			true, true, true, 
            			autorRepository.findById(autor.getId()).get(), 
            			casopis, 
            			new HashSet<NaucnaOblast>(paperInfo.getNaucneOblasti()), 
            			new HashSet<Recenzent>(recenzenti), null);
            	
            	
            	revizija = revizijaRadaRepository.save(revizija);
            	
            	Set<NaucnaOblast> naucneOblasti1 = new HashSet<NaucnaOblast>();
            	naucneOblasti1.addAll(revizija.getNaucneOblasti());
            	
            	NaucniRad naucniRad = new NaucniRad(revizija);
            	naucniRad.setNaucneOblasti(naucneOblasti1);
            	naucniRad = naucniRadRepository.save(naucniRad);
            	
            	IndexUnit newPaper = new IndexUnit(naucniRad.getId().toString(), paperInfo, tekst, autor, casopis, recenzentiInfo, naucneOblasti);
            	indexUnitRepository.index(newPaper);
            }
    	}
    }
	
	private String saveUploadedFile(MultipartFile file) throws IOException {
	   	String retVal = null;
        if (! file.isEmpty()) {
	           byte[] bytes = file.getBytes();
	           Path path = Paths.get(getResourceFilePath(LIBRARY_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
	           Files.write(path, bytes);
	           retVal = path.toString();
        }
        return retVal;
    }
	
	private File getResourceFilePath(String path) {
		
	    return new File(path);
	}
	
}
