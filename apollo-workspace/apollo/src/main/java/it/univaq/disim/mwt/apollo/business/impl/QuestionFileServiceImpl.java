package it.univaq.disim.mwt.apollo.business.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.QuestionFileService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.QuestionFileRepository;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionFile;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class QuestionFileServiceImpl implements QuestionFileService {

	@Autowired
	private QuestionFileRepository repository;

	@Override
	public QuestionFile findById(String id) throws BusinessException {
		try {
			return repository.findById(id).get();
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void create(QuestionFile file) throws BusinessException {
		try {
			repository.save(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void update(QuestionFile file) throws BusinessException {
		try {
			repository.save(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(QuestionFile file) throws BusinessException {
		try {
			repository.delete(file);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

}
