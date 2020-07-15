package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.SurveyFile;

public interface SurveyFileService {

	SurveyFile findById(String id) throws BusinessException;
	void create(SurveyFile file) throws BusinessException;
	void update(SurveyFile file) throws BusinessException;
	void delete(SurveyFile file) throws BusinessException;
}
