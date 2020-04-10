package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
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
    
    /**
     * Find Surveys by id.
     * @param name
     * @return List<Survey>
     */
    @Query("{'name' : ?0")
    List<Survey> findSurveysByName(String name);
    
}
