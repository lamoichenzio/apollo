package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.Question;

@Repository
public interface InputQuestionAnswerRepository extends MongoRepository<InputQuestionAnswer, String> {
	
	@Query("{'question' : ?0}")
	List<InputQuestionAnswer> findByQuestion(Question question);
	
}
