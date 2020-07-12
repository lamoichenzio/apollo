package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;

@Repository
public interface SingleChoiceMatrixAnswerRepository extends MongoRepository<SingleChoiceMatrixAnswer, String>{
	
	/**
	 * Find all Single Choice Matrix answers by question.
	 * @param question
	 * @return List<SingleChoiceMatrixAnswer>
	 */
	List<SingleChoiceMatrixAnswer> findByQuestion(MatrixQuestion question);
}
