package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.Question;

@Repository
public interface MultiChoiceMatrixAnswerRepository extends MongoRepository<MultiChoiceMatrixAnswer, String> {

	@Query("{'question : ?0'}")
	List<MultiChoiceMatrixAnswer> findByQuestion(Question question);
}
