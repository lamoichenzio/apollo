package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.univaq.disim.mwt.apollo.domain.DocumentFile;

public interface DocumentFileRepository extends MongoRepository<DocumentFile, String> {

}
