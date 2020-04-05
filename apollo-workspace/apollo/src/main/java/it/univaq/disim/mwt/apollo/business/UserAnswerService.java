package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.domain.UserAnswer;

public interface UserAnswerService {
	
	List<UserAnswer> findAllUserAnswers() throws BusinessException;
	
	ResponseGrid<UserAnswer> findAllUserAnswersPaginated(RequestGrid request) throws BusinessException;
	
	UserAnswer findQuestionById(String id) throws BusinessException;
	
	void createQuestionGrpup(UserAnswer UserAnswer) throws BusinessException;
	
	void updateUserAnswer(UserAnswer UserAnswer) throws BusinessException;
	
	void deleteUserAnswer(UserAnswer UserAnswer) throws BusinessException;
}