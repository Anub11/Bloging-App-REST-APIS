package com.anubhav.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anubhav.blog.services.FileService;
import com.fasterxml.jackson.core.sym.Name;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadimage(String path, MultipartFile file) throws IOException {

		// file name
		String name = file.getOriginalFilename();

		// random id generate for image
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

		// full path
		String filePath = path + File.separator + fileName1;

		// create folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
			

		// file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileName1;
	}

	@Override
	public InputStream getResourec(String path, String fileName) throws FileNotFoundException {
		String filePath = path + File.separator + fileName;
		InputStream is = new FileInputStream(filePath);
		return is ;
	}

}
