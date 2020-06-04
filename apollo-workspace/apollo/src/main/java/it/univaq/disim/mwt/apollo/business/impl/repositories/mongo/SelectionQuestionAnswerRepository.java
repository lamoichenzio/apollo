package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;

public interface SelectionQuestionAnswerRepository extends MongoRepository<SelectionQuestionAnswer, String>{

}
