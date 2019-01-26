package com.ftn.nc.NCBackend.web.dto;

import org.springframework.core.io.ByteArrayResource;

public class FileMessageResourceDTO extends ByteArrayResource {

	private final String filename;
	
	public FileMessageResourceDTO(final byte[] byteArray, final String filename) {
        super(byteArray);
        this.filename = filename;
    }
	
	@Override
    public String getFilename() {
        return filename;
    }

}
