package com.ftn.nc.NCBackend.elastic.dto;

import java.util.List;

public class QueryDTO {
	
	private List<QueryParamDTO> params;
	
	private int pageNum;
	
	private boolean allFields;

	public QueryDTO() {
		super();
	}

	public QueryDTO(int pageNum, List<QueryParamDTO> params, boolean allFields) {
		super();
		this.pageNum = pageNum;
		this.params = params;
		this.allFields = allFields;
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

	public boolean isAllFields() {
		return allFields;
	}

	public void setAllFields(boolean allFields) {
		this.allFields = allFields;
	}
	
}
