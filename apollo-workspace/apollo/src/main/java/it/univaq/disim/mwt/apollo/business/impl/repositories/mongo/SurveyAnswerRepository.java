package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;

@Repository
public interface SurveyAnswerRepository extends MongoRepository<SurveyAnswer, String>{

}