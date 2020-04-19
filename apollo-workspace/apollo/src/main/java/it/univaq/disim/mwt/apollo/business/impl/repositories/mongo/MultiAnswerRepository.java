package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.MultiAnswer;

@Repository
public interface MultiAnswerRepository extends MongoRepository<MultiAnswer, String> {
	
	/**
	 * Find first MultiAnswer by id.
	 * @param id This is the id of the MultiAnswer you want to find.
	 * @return MultiAnswer
	 */
	MultiAnswer findFirstById(String id);

}
