package com.ftn.nc.NCBackend.helpClasses;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PDFUtils {
		
	public static String saveUploadedFile(MultipartFile file, String folderPath) throws IOException {
	   	String retVal = null;
        if (! file.isEmpty()) {
	           byte[] bytes = file.getBytes();
	           String filePrefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	           Path path = Paths.get(getResourceFilePath(folderPath).getAbsolutePath() + File.separator + filePrefix + "-" +file.getOriginalFilename());
	           Files.write(path, bytes);
	           retVal = path.toString();
        }
        return retVal;
    }
	
	public static File getResourceFilePath(String path) {
		
	    return new File(path);
	}
	
}
