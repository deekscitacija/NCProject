package com.ftn.nc.NCBackend.elastic.handler;

import java.io.File;

import com.ftn.nc.NCBackend.elastic.model.IndexUnit;

public abstract class DocumentHandler {

	public abstract IndexUnit getIndexUnit(File file);
	public abstract String getText(File file);

}
