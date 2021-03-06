package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;


public interface QuestionGroupService {

	QuestionGroup findQuestionGroupById(String id) throws BusinessException;
	
	void createQuestionGroup(QuestionGroup questionGroup) throws BusinessException;
	
	void updateQuestionGroup(QuestionGroup questionGroup) throws BusinessException;
	
	void deleteQuestionGroup(QuestionGroup questionGroup) throws BusinessException;
	
	void deleteQuestionGroupList(Iterable<? extends QuestionGroup> entities) throws BusinessException;
	
}
