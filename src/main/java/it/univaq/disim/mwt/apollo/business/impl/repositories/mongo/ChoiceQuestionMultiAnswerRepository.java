package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;

@Repository
public interface ChoiceQuestionMultiAnswerRepository extends MongoRepository<ChoiceQuestionMultiAnswer, String> {

	/**
	 * Find all Multi Choice answers by question.
	 * @param question
	 * @return List<ChoiceQuestionMultiAnswer>
	 */
	List<ChoiceQuestionMultiAnswer> findByQuestion(ChoiceQuestion question);
	
}
