package com.ftn.nc.NCBackend.elastic.dto;

public class QueryParamDTO {
	
	private String key;
	
	private String value;
	
	private boolean phraseQuery;
	
	private boolean optional;

	public QueryParamDTO() {
		super();
	}

	public QueryParamDTO(String key, String value, boolean phraseQuery, boolean optional) {
		super();
		this.key = key;
		this.value = value;
		this.phraseQuery = phraseQuery;
		this.optional = optional;
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

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}
