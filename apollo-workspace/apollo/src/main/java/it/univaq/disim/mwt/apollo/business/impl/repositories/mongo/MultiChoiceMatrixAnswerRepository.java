package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.MultiChoiceMatrixAnswer;

@Repository
public interface MultiChoiceMatrixAnswerRepository extends MongoRepository<MultiChoiceMatrixAnswer, String> {

	/**
	 * Find first MultiChoiceMatrixAnswer by id.
	 * @param id This is the id of the MultiChoiceMatrixAnswer you want to find.
	 * @return MultiChoiceMatrixAnswer
	 */
	MultiChoiceMatrixAnswer findFirstById(String id);
}
