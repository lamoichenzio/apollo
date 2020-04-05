package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.domain.QuestionGroup;


public interface QuestionGroupService {

	List<QuestionGroup> findAllQuestionGroups() throws BusinessException;
	
	ResponseGrid<QuestionGroup> findAllQuestionGroupsPaginated(RequestGrid request) throws BusinessException;
	
	QuestionGroup findQuestionById(String id) throws BusinessException;
	
	void createQuestionGrpup(QuestionGroup questionGroup) throws BusinessException;
	
	void updateQuestionGroup(QuestionGroup questionGroup) throws BusinessException;
	
	void deleteQuestionGroup(QuestionGroup questionGroup) throws BusinessException;
	
}
