package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionFile;

public interface QuestionFileService {

	QuestionFile findById(String id) throws BusinessException;
	void create(QuestionFile file) throws BusinessException;
	void update(QuestionFile file) throws BusinessException;
	void delete(QuestionFile file) throws BusinessException;
	
}
