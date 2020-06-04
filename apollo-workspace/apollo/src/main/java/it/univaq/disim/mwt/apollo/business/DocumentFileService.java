package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.DocumentFile;

public interface DocumentFileService {

	DocumentFile findById(String id) throws BusinessException;
	void create(DocumentFile file) throws BusinessException;
	void update(DocumentFile file) throws BusinessException;
	void delete(DocumentFile file) throws BusinessException;
}
