package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.Survey;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {
	
	/**
	 * Find first Survey by id.
	 * @param id This is the id of the Survey you want to find.
	 * @return Survey
	 */
    Survey findFirstById(String id);
    
}
