package com.connectingDotsInfotech.MoviePage.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.lang.Nullable;

@Service
public class FileService {

	@Autowired
	private GridFsTemplate template;

	@Autowired
	private GridFsOperations operations;

	@Autowired
	MongoTemplate mongoTemplate;

	private final @Nullable String bucket = null;

	// store the file in the database and returns the Object ID
	public String addFile(MultipartFile upload) throws IOException {

		DBObject metadata = new BasicDBObject();
		metadata.put("fileSize", upload.getSize());

		Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(),
				metadata);

		return fileID.toString();
	}

	private GridFSBucket getGridFs() {

		MongoDatabase db = mongoTemplate.getDb();
		return bucket == null ? GridFSBuckets.create(db) : GridFSBuckets.create(db, bucket);

	}

	// return a specific file from the database.
	public GridFsResource downloadFile(String id) throws IOException {

		GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));

		return new GridFsResource(gridFSFile, getGridFs().openDownloadStream(gridFSFile.getObjectId()));

		// findOne method in GridFSTemplate will return the file that matches the query.
//		LoadFile loadFile = new LoadFile();
//
//		if (gridFSFile != null && gridFSFile.getMetadata() != null) {
//			loadFile.setFilename(gridFSFile.getFilename());
//
//			loadFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());
//
//			loadFile.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());
////IOUtils
//			// loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
//
//			// byte[] bytes = ByteStreams.toByteArray();
//
//			InputStream is = operations.getResource(gridFSFile).getInputStream();
//			byte[] array = is;
//			loadFile.setFile(array);
//		}
//		return loadFile;
	}

}