package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.SurveyIcon;

public interface DocumentFileService {

	SurveyIcon findById(String id) throws BusinessException;
	void create(SurveyIcon file) throws BusinessException;
	void update(SurveyIcon file) throws BusinessException;
	void delete(SurveyIcon file) throws BusinessException;
}
