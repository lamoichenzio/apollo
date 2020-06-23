package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;

@Repository
public interface SurveyAnswerRepository extends MongoRepository<SurveyAnswer, String>{

	/**
	 * Find all survey answers by survey.
	 * @param survey
	 * @return List<SurveyAnswer>
	 */
	List<SurveyAnswer> findAllBySurvey(Survey survey);

	boolean existsBySurveyAndEmail(Survey survey, String email);
}
