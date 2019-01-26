package com.ftn.nc.NCBackend.elastic.dto;

import java.util.List;

public class QueryDTO {
	
	private List<QueryParamDTO> params;
	
	private int pageNum;

	public QueryDTO() {
		super();
	}

	public QueryDTO(int pageNum, List<QueryParamDTO> params, List<QueryParamDTO> naucneOblasti) {
		super();
		this.pageNum = pageNum;
		this.params = params;
	}

	public List<QueryParamDTO> getParams() {
		return params;
	}

	public void setParams(List<QueryParamDTO> params) {
		this.params = params;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
}
