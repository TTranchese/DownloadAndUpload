package com.example.DownloadAndUpload.controllers;

import com.example.DownloadAndUpload.services.StorageService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
	@Autowired
	StorageService storageService;
	@PostMapping("/upload")
	public String upload(@RequestParam MultipartFile file) throws IOException {
		return storageService.upload(file);
	}
	@GetMapping("download")
	public @ResponseBody byte[] download(@RequestParam String fileName, HttpServletResponse response) throws Exception{
		System.out.println("Downloading: "+fileName);
		String extension = FilenameUtils.getExtension(fileName);
		switch (extension) {
			case "gif" -> response.setContentType(MediaType.IMAGE_GIF_VALUE);
			case "jpeg", "jpg" -> response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			case "png" -> response.setContentType(MediaType.IMAGE_PNG_VALUE);
		}
		response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
		return storageService.download(fileName);
	}
}
