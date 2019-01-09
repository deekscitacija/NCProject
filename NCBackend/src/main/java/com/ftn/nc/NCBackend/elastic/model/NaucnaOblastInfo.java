package com.ftn.nc.NCBackend.elastic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "naucnacentrala", type = "naucnaoblast")
public class NaucnaOblastInfo {
	
	@Id
	@Field(type = FieldType.text)
	private String id;
	
	@Field(type = FieldType.text)
	private String naziv;
	
	@Field(type = FieldType.text)
	private String kod;

	public NaucnaOblastInfo() {
		super();
	}

	public NaucnaOblastInfo(String id, String naziv, String kod) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.kod = kod;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getKod() {
		return kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}
	
}
