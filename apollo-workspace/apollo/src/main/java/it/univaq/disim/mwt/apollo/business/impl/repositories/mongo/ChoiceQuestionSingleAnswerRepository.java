package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;

public interface ChoiceQuestionSingleAnswerRepository extends MongoRepository<ChoiceQuestionSingleAnswer, String> {

}
