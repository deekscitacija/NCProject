package com.ftn.nc.NCBackend.elastic.resultMappers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import com.ftn.nc.NCBackend.elastic.model.IndexUnit;
import com.ftn.nc.NCBackend.elastic.model.NaucnaOblastInfo;

public class ContentResultMapper implements SearchResultMapper{
	
	public ContentResultMapper() {
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
		
		List<IndexUnit> chunk = new ArrayList<IndexUnit>();
        for (SearchHit searchHit : response.getHits()) {
            if (response.getHits().getHits().length <= 0) {
                return null;
            }
            Map<String, Object> source = searchHit.getSource();
            IndexUnit indexUnit = new IndexUnit();
            
            indexUnit.setId(searchHit.getId());
            indexUnit.setAutor((String) source.get("autor"));
            indexUnit.setApstrakt((String) source.get("apstrakt"));
            indexUnit.setKljucne((String) source.get("kljucne"));
            indexUnit.setKoautori((String) source.get("koautori"));
            indexUnit.setNaslov((String) source.get("naslov"));
            indexUnit.setNaucneOblasti((List<NaucnaOblastInfo>) source.get("naucneOblasti"));
            indexUnit.setCasopis((String) source.get("casopis"));
            indexUnit.setOpenAccess((boolean) source.get("openAccess"));
            
            String highValue = "";
            try {
            	highValue = "..."+searchHit.getHighlightFields().get("tekst").fragments()[0].toString()+"...";
            }catch(Exception e) {
            	highValue = "";
            }
            
            indexUnit.setTekst(highValue);
            
            chunk.add(indexUnit);
        }
        if (chunk.size() > 0) {
            return new AggregatedPageImpl(chunk);
        }
       
        return null;
	}

}
