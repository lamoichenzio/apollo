package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.domain.Survey;

public interface SurveyService {
	
	List<Survey> findAllSurveys() throws BusinessException;
	
	ResponseGrid<Survey> findAllSurveysPaginated(RequestGrid request) throws BusinessException;
	
	List<Survey> findSurveysByName(String name) throws BusinessException;
	
	Survey findSurveyById(String id) throws BusinessException;
	
	void createSurvey(Survey survey) throws BusinessException;
	
	void updateSurvey(Survey survey) throws BusinessException;
	
	void deleteSurvey(Survey survey) throws BusinessException;
}
