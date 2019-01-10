package com.ftn.nc.NCBackend.elastic.dto;

import java.util.List;

public class QueryDTO {
	
	private List<QueryParamDTO> params;
	
	private List<String> naucneOblasti;

	public QueryDTO() {
		super();
	}

	public QueryDTO(List<QueryParamDTO> params, List<String> naucneOblasti) {
		super();
		this.params = params;
		this.naucneOblasti = naucneOblasti;
	}

	public List<QueryParamDTO> getParams() {
		return params;
	}

	public void setParams(List<QueryParamDTO> params) {
		this.params = params;
	}

	public List<String> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<String> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

}
