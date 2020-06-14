package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;

@Repository
public interface SelectionQuestionAnswerRepository extends MongoRepository<SelectionQuestionAnswer, String>{

	/**
	 * Find all Selection answers by question.
	 * @param question
	 * @return List<SelectionQuestionAnswer>
	 */
	List<SelectionQuestionAnswer> findByQuestion(SelectionQuestion question);
	
}
