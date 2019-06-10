package com.flong.kotlin.core.minio.vo

import io.minio.ObjectStat;

import java.util.Date;

class MinioObject {

	var bucketName: String = "";
	var name: String = "";
	var createdTime: Date? = null;
	var length: Long = 0;
	var etag: String = ""
	var contentType: String = ""
	var matDesc: String = ""


	constructor(os: ObjectStat) {
		this.bucketName = os.bucketName();
		this.name = os.name();
		this.createdTime = os.createdTime();
		this.length = os.length();
		this.etag = os.etag();
		this.contentType = os.contentType();
		this.matDesc = os.matDesc();
	}
}