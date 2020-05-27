package it.univaq.disim.mwt.apollo.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.SurveyAnswerService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyAnswerRepository;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;

@Service
@Transactional
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

	@Autowired
	SurveyAnswerRepository surveyAnswerRepository;
	
	@Override
	public List<SurveyAnswer> findAllUserAnswers() throws BusinessException {
		return surveyAnswerRepository.findAll();
	}

	@Override
	public ResponseGrid<SurveyAnswer> findAllUserAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Implement me
		return null;
	}

	@Override
	public SurveyAnswer findUserAnswerById(String id) throws BusinessException {
		return surveyAnswerRepository.findById(id).get();
	}

	@Override
	public void createUserAnswer(SurveyAnswer userAnswer) throws BusinessException {
		surveyAnswerRepository.save(userAnswer);
	}

	@Override
	public void updateUserAnswer(SurveyAnswer userAnswer) throws BusinessException {
		surveyAnswerRepository.save(userAnswer);
	}

	@Override
	public void deleteUserAnswer(SurveyAnswer userAnswer) throws BusinessException {
		surveyAnswerRepository.delete(userAnswer);
	}

	@Override
	public void deleteUserAnswerById(String id) throws BusinessException {
		surveyAnswerRepository.deleteById(id);
	}
	
}
