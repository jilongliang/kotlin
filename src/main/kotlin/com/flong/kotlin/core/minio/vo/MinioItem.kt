package com.flong.kotlin.core.minio.vo

import io.minio.messages.Item;
import io.minio.messages.Owner;

import java.util.Date;

open class MinioItem {
	var objectName: String? = null
	var lastModified: Date? = null
	var etag: String? = null
	var size: Int? = null
	var storageClass: String? = null
	var owner: Owner? = null
	var type: String? = null

	constructor(item: Item) {
		this.objectName = item.objectName();
		this.lastModified = item.lastModified();
		this.etag = item.etag();
		this.size = item.size;
		this.storageClass = item.storageClass();
		this.owner = item.owner();
		this.type = if(item.isDir())  "directory" else "file"
	}

}