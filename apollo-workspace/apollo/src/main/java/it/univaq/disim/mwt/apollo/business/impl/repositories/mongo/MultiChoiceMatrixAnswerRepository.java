package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;

@Repository
public interface MultiChoiceMatrixAnswerRepository extends MongoRepository<MultiChoiceMatrixAnswer, String> {

	/**
	 * Find all Multi Choice Matrix answers by question.
	 * @param question
	 * @return List<MultiChoiceMatrixAnswer>
	 */
	List<MultiChoiceMatrixAnswer> findByQuestion(MatrixQuestion question);
	
}
