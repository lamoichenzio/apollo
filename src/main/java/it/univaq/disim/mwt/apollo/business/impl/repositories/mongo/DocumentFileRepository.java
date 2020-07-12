package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.SurveyIcon;

@Repository
public interface DocumentFileRepository extends MongoRepository<SurveyIcon, String> {

}