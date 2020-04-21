package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;

@Repository
public interface ChoiceQuestionRepository extends MongoRepository<ChoiceQuestion, String> {
    
	/**
	 * Find first ChoiceQuestion by id.
	 * @param id This is the id of the ChoiceQuestion you want to find.
	 * @return ChoiceQuestion
	 */
	ChoiceQuestion findFirstById(String id);

}