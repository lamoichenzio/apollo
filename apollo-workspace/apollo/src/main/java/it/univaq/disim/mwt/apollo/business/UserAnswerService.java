package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.answers.UserAnswer;

public interface UserAnswerService {
	
	List<UserAnswer> findAllUserAnswers() throws BusinessException;
	
	ResponseGrid<UserAnswer> findAllUserAnswersPaginated(RequestGrid request) throws BusinessException;
	
	UserAnswer findUserAnswerById(String id) throws BusinessException;
	
	void createUserAnswer(UserAnswer userAnswer) throws BusinessException;
	
	void updateUserAnswer(UserAnswer userAnswer) throws BusinessException;
	
	void deleteUserAnswer(UserAnswer userAnswer) throws BusinessException;
}