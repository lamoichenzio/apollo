package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;


public interface QuestionGroupService {

	List<QuestionGroup> findAllQuestionGroups() throws BusinessException;
	
	ResponseGrid<QuestionGroup> findAllQuestionGroupsPaginated(RequestGrid request) throws BusinessException;
	
	QuestionGroup findQuestionGroupById(String id) throws BusinessException;
	
	void createQuestionGroup(QuestionGroup questionGroup) throws BusinessException;
	
	void updateQuestionGroup(QuestionGroup questionGroup) throws BusinessException;
	
	void deleteQuestionGroup(QuestionGroup questionGroup) throws BusinessException;
	
	void deleteQuestionGroupById(String id) throws BusinessException;
	
	void deleteQuestionGroupList(Iterable<? extends QuestionGroup> entities) throws BusinessException;
	
}
