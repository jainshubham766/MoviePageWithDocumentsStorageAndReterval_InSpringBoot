package com.connectingDotsInfotech.MoviePage.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//@Document(collection = "movie")  No DB info right!?
public class LoadFile {

	private String filename;
	private String fileType;
	private String fileSize;
	private byte[] file;
	//directly store files in the GridFS Collection.
}