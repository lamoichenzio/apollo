package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.SingleChoiceMatrixAnswer;

@Repository
public interface SingleChoiceMatrixAnswerRepository extends MongoRepository<SingleChoiceMatrixAnswer, String>{

	/**
	 * Find first SingleChoiceMatrixAnswer by id.
	 * @param id This is the id of the SingleChoiceMatrixAnswer you want to find.
	 * @return SingleChoiceMatrixAnswer
	 */
	SingleChoiceMatrixAnswer findFirstById(String id);
	
}
