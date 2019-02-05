package com.ftn.nc.NCBackend.elastic.dto;

import com.ftn.nc.NCBackend.web.enums.SearchParamType;

public class QueryParamDTO {
	
	private String key;
	
	private String value;
	
	private boolean phraseQuery;
	
	private SearchParamType paramType;

	public QueryParamDTO() {
		super();
	}

	public QueryParamDTO(String key, String value, boolean phraseQuery, SearchParamType paramType) {
		super();
		this.key = key;
		this.value = value;
		this.phraseQuery = phraseQuery;
		this.paramType = paramType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isPhraseQuery() {
		return phraseQuery;
	}

	public void setPhraseQuery(boolean phraseQuery) {
		this.phraseQuery = phraseQuery;
	}

	public SearchParamType getParamType() {
		return paramType;
	}

	public void setParamType(SearchParamType paramType) {
		this.paramType = paramType;
	}

}
