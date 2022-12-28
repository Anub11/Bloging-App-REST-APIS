package com.anubhav.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

	String uploadimage(String path,MultipartFile file) throws IOException;
	
	InputStream getResourec(String path,String fileName) throws FileNotFoundException;
	
}
