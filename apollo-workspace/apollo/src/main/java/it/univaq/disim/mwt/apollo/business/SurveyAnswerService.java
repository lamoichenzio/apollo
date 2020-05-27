package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;

public interface SurveyAnswerService {
	
	List<SurveyAnswer> findAllUserAnswers() throws BusinessException;
	
	ResponseGrid<SurveyAnswer> findAllUserAnswersPaginated(RequestGrid request) throws BusinessException;
	
	SurveyAnswer findUserAnswerById(String id) throws BusinessException;
	
	void createUserAnswer(SurveyAnswer userAnswer) throws BusinessException;
	
	void updateUserAnswer(SurveyAnswer userAnswer) throws BusinessException;
	
	void deleteUserAnswer(SurveyAnswer userAnswer) throws BusinessException;
	
	void deleteUserAnswerById(String id) throws BusinessException;
}