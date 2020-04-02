package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.Survey;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, Long> {
	
	// CRUD Operations
    Survey findFirstById(Long Id);
    
}
