package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;

@Repository
public interface QuestionGroupRepository extends MongoRepository<QuestionGroup, String> {
    
	/**
	 * Find first QuestionGroup by id.
	 * @param id This is the id of the QuestionGroup you want to find.
	 * @return QuestionGroup
	 */
	QuestionGroup findFirstById(String id);

}
