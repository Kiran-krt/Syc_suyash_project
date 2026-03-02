package com.syc.dashboard.query.document.repository.jpa

import com.syc.dashboard.query.document.entity.Document
import org.springframework.data.mongodb.repository.MongoRepository

interface DocumentRepository : MongoRepository<Document, String>
