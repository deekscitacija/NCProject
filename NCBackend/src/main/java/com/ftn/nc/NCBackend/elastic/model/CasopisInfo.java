package com.ftn.nc.NCBackend.elastic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.ftn.nc.NCBackend.web.model.Casopis;

@Document(indexName = "naucnacentrala", type = "casopis")
public class CasopisInfo {
	
	@Id
	@Field(type = FieldType.text)
	private String id;
	
	@Field(type = FieldType.text)
	private String ime;

	public CasopisInfo() {
		super();
	}

	public CasopisInfo(String id, String ime) {
		super();
		this.id = id;
		this.ime = ime;
	}
	
	public CasopisInfo(Casopis casopis) {
		super();
		this.id = casopis.getId().toString();
		this.ime = casopis.getNaziv();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

}
