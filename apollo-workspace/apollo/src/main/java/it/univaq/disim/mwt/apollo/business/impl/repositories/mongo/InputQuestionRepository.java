package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;

@Repository
public interface InputQuestionRepository extends MongoRepository<InputQuestion, String> {
    
	/**
	 * Find first InputQuestion by id.
	 * @param id This is the id of the InputQuestion you want to find.
	 * @return InputQuestion
	 */
	InputQuestion findFirstById(String id);

}

