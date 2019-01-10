package com.ftn.nc.NCBackend.elastic.dto;

public class QueryParamDTO {
	
	private String key;
	
	private String value;
	
	private boolean phraseQuery;

	public QueryParamDTO() {
		super();
	}

	public QueryParamDTO(String key, String value, boolean phraseQuery) {
		super();
		this.key = key;
		this.value = value;
		this.phraseQuery = phraseQuery;
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
	
	

}
