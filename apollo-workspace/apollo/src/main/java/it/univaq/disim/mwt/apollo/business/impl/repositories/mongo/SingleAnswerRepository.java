package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.SingleAnswer;

@Repository
public interface SingleAnswerRepository extends MongoRepository<SingleAnswer, String> {

	/**
	 * Find first SingleAnswer by id.
	 * @param id This is the id of the SingleAnswer you want to find.
	 * @return SingleAnswer
	 */
	SingleAnswer findFirstById(String id);
	
}