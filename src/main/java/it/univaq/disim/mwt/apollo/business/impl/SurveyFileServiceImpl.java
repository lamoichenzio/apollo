package it.univaq.disim.mwt.apollo.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.SurveyFileService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyFileRepository;
import it.univaq.disim.mwt.apollo.domain.SurveyFile;

@Service
@Transactional
public class SurveyFileServiceImpl implements SurveyFileService {

	@Autowired
	private SurveyFileRepository surveyFileRepository;
	
	@Override
	@Transactional(readOnly = true)
	public SurveyFile findById(String id) throws BusinessException {
		try {
			return surveyFileRepository.findById(id).get();
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void create(SurveyFile file) throws BusinessException {
		try {
			surveyFileRepository.save(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void update(SurveyFile file) throws BusinessException {
		try {
			surveyFileRepository.save(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(SurveyFile file) throws BusinessException {
		try {
			surveyFileRepository.delete(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
		
	}

}
