package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;

@Repository
public interface ChoiceQuestionSingleAnswerRepository extends MongoRepository<ChoiceQuestionSingleAnswer, String> {

	/**
	 * Find all Single Choice answers by question.
	 * @param question 
	 * @return List<ChoiceQuestionSingleAnswer>
	 */
	List<ChoiceQuestionSingleAnswer> findByQuestion(ChoiceQuestion question);
	
}
