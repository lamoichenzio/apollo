package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.SelectQuestion;

@Repository
public interface SelectQuestionRepository extends MongoRepository<SelectQuestion, String> {
    
	/**
	 * Find first SelectQuestion by id.
	 * @param id This is the id of the SelectQuestion you want to find.
	 * @return SelectQuestion
	 */
	SelectQuestion findFirstById(String id);

}