package com.connectingDotsInfotech.MoviePage.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.connectingDotsInfotech.MoviePage.Service.FileService;

@RestController
@RequestMapping("file")
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
		return new ResponseEntity<>(fileService.addFile(file), HttpStatus.OK);
	}

	// download() method, receive the LoadFile object first, and then map the
	// necessary details to a Response Header to make it downloadable.
	@GetMapping("/download/{id}")
	public ResponseEntity<GridFsResource> download(@PathVariable String id) throws IOException {
		GridFsResource gridFsResource = fileService.downloadFile(id);

		return ResponseEntity.ok().contentLength(gridFsResource.contentLength())
				.cacheControl(CacheControl.maxAge(20, TimeUnit.DAYS))
				.contentType(MediaType.parseMediaType(gridFsResource.getContentType())).body(gridFsResource);
	}

}