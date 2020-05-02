package it.univaq.disim.mwt.apollo.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.UserAnswerService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.UserAnswerRepository;
import it.univaq.disim.mwt.apollo.domain.answers.UserAnswer;

@Service
@Transactional
public class UserAnswerServiceImpl implements UserAnswerService {

	@Autowired
	UserAnswerRepository userAnswerRepository;
	
	@Override
	public List<UserAnswer> findAllUserAnswers() throws BusinessException {
		return userAnswerRepository.findAll();
	}

	@Override
	public ResponseGrid<UserAnswer> findAllUserAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Implement me
		return null;
	}

	@Override
	public UserAnswer findUserAnswerById(String id) throws BusinessException {
		return userAnswerRepository.findById(id).get();
	}

	@Override
	public void createUserAnswer(UserAnswer userAnswer) throws BusinessException {
		userAnswerRepository.save(userAnswer);
	}

	@Override
	public void updateUserAnswer(UserAnswer userAnswer) throws BusinessException {
		userAnswerRepository.save(userAnswer);
	}

	@Override
	public void deleteUserAnswer(UserAnswer userAnswer) throws BusinessException {
		userAnswerRepository.delete(userAnswer);
	}

}
