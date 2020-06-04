package it.univaq.disim.mwt.apollo.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.AnswerService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionMultiAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.InputQuestionAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.MultiChoiceMatrixAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SingleChoiceMatrixAnswerRepository;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	ChoiceQuestionMultiAnswerRepository multiAnswerRepository;
	
	@Autowired
	MultiChoiceMatrixAnswerRepository multiChoiceMatrixAnswerRepository;
	
	@Autowired
	InputQuestionAnswerRepository singleAnswerRepository;
	
	@Autowired
	SingleChoiceMatrixAnswerRepository singleChoiceMatrixAnswerRepository;
	
	@Override
	public List<ChoiceQuestionMultiAnswer> findAllMultiAnswers() throws BusinessException {
		return multiAnswerRepository.findAll();
	}

	@Override
	public List<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswers() throws BusinessException {
		return multiChoiceMatrixAnswerRepository.findAll();
	}

	@Override
	public List<InputQuestionAnswer> findAllSingleAnswers() throws BusinessException {
		return singleAnswerRepository.findAll();
	}

	@Override
	public List<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswers() throws BusinessException {
		return singleChoiceMatrixAnswerRepository.findAll();
	}

	@Override
	public ResponseGrid<ChoiceQuestionMultiAnswer> findAllMultiAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswersPaginated(RequestGrid request)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<InputQuestionAnswer> findAlSingleAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswersPaginated(RequestGrid request)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChoiceQuestionMultiAnswer findMultiAnswerById(String id) throws BusinessException {
		return multiAnswerRepository.findById(id).get();
	}

	@Override
	public MultiChoiceMatrixAnswer findMultiChoiceMatrixAnswerById(String id) throws BusinessException {
		return multiChoiceMatrixAnswerRepository.findById(id).get();
	}

	@Override
	public InputQuestionAnswer findSingleAnswerById(String id) throws BusinessException {
		return singleAnswerRepository.findById(id).get();
	}

	@Override
	public SingleChoiceMatrixAnswer findSingleChoiceMatrixAnswerById(String id) throws BusinessException {
		return singleChoiceMatrixAnswerRepository.findById(id).get();
	}

	@Override
	public void createAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException {
		multiAnswerRepository.save(answer);
	}

	@Override
	public void createAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException {
		multiChoiceMatrixAnswerRepository.save(answer);
	}

	@Override
	public void createAnswer(InputQuestionAnswer answer) throws BusinessException {
		singleAnswerRepository.save(answer);
	}

	@Override
	public void createAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException {
		singleChoiceMatrixAnswerRepository.save(answer);
	}

	@Override
	public void updateAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException {
		multiAnswerRepository.save(answer);
	}

	@Override
	public void updateAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException {
		multiChoiceMatrixAnswerRepository.save(answer);	
	}

	@Override
	public void updateAnswer(InputQuestionAnswer answer) throws BusinessException {
		singleAnswerRepository.save(answer);
	}

	@Override
	public void updateAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException {
		singleChoiceMatrixAnswerRepository.save(answer);
	}

	@Override
	public void deleteAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException {
		multiAnswerRepository.delete(answer);
	}

	@Override
	public void deleteAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException {
		multiChoiceMatrixAnswerRepository.delete(answer);
	}

	@Override
	public void deleteAnswer(InputQuestionAnswer answer) throws BusinessException {
		singleAnswerRepository.delete(answer);
	}

	@Override
	public void deleteAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException {
		singleChoiceMatrixAnswerRepository.delete(answer);
	}

}
