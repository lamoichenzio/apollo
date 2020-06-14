package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;

@Repository
public interface InputQuestionAnswerRepository extends MongoRepository<InputQuestionAnswer, String> {
	
	/**
	 * Find all Input answers by question.
	 * @param question
	 * @return List<InputQuestionAnswer>
	 */
	List<InputQuestionAnswer> findByQuestion(InputQuestion question);
	
}
