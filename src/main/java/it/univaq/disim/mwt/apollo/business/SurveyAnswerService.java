package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;

public interface SurveyAnswerService {
	
	List<SurveyAnswer> findAllSurveyAnswers() throws BusinessException;
	
	List<SurveyAnswer> findAllBySurvey(Survey survey) throws BusinessException;
	
	ResponseGrid<SurveyAnswer> findAllSurveyAnswersPaginated(RequestGrid request, Survey survey) throws BusinessException;
	
	SurveyAnswer findSurveyAnswerById(String id) throws BusinessException;
	
	void createSurveyAnswer(SurveyAnswer surveyAnswer) throws BusinessException;

	boolean surveyExistsBySurveyAndEmail(Survey survey, String email) throws BusinessException;
	
}