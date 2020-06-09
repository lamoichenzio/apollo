package it.univaq.disim.mwt.apollo.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.DocumentFileService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.DocumentFileRepository;
import it.univaq.disim.mwt.apollo.domain.DocumentFile;

@Service
@Transactional
public class DocumentFileServiceImpl implements DocumentFileService {

	@Autowired
	DocumentFileRepository documentFileRepository;
	
	@Override
	@Transactional(readOnly = true)
	public DocumentFile findById(String id) throws BusinessException {
		try {
			return documentFileRepository.findById(id).get();
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void create(DocumentFile file) throws BusinessException {
		try {
			documentFileRepository.save(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void update(DocumentFile file) throws BusinessException {
		try {
			documentFileRepository.save(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(DocumentFile file) throws BusinessException {
		try {
			documentFileRepository.delete(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
		
	}

}
