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
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionSingleAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.InputQuestionAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.MultiChoiceMatrixAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SelectionQuestionAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SingleChoiceMatrixAnswerRepository;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.Question;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	ChoiceQuestionMultiAnswerRepository choiceQuestionMultiAnswerRepository;
	
	@Autowired
	ChoiceQuestionSingleAnswerRepository choiceQuestionSingleAnswerRepository;
	
	@Autowired
	InputQuestionAnswerRepository inputQuestionAnswerRepository;
	
	@Autowired
	MultiChoiceMatrixAnswerRepository multiChoiceMatrixAnswerRepository;
	
	@Autowired
	SelectionQuestionAnswerRepository selectionQuestionAnswerRepository;
	
	@Autowired
	SingleChoiceMatrixAnswerRepository singleChoiceMatrixAnswerRepository;
	
	
	/** FIND ALL **/
	
	@Override
	public List<ChoiceQuestionMultiAnswer> findAllChoiceQuestionMultiAnswers() throws BusinessException {
		return choiceQuestionMultiAnswerRepository.findAll();
	}

	@Override
	public List<ChoiceQuestionSingleAnswer> findAllChoiceQuestionSingleAnswer() throws BusinessException {
		return choiceQuestionSingleAnswerRepository.findAll();
	}

	@Override
	public List<InputQuestionAnswer> findAllInputQuestionAnswers() throws BusinessException {
		return inputQuestionAnswerRepository.findAll();
	}

	@Override
	public List<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswer() throws BusinessException {
		return multiChoiceMatrixAnswerRepository.findAll();
	}
	
	@Override
	public List<SelectionQuestionAnswer> findAllSelectionQuestionAnswers() throws BusinessException {
		return selectionQuestionAnswerRepository.findAll();
	}
	
	@Override
	public List<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswers() throws BusinessException {
		return singleChoiceMatrixAnswerRepository.findAll();
	}

	/** FIND ALL PAGINATED **/
	
	@Override
	public ResponseGrid<ChoiceQuestionMultiAnswer> findAllChoiceQuestionMultiAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<ChoiceQuestionSingleAnswer> findAllChoiceQuestionSingleAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<InputQuestionAnswer> findAllInputQuestionAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResponseGrid<SelectionQuestionAnswer> findAllSelectionQuestionAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResponseGrid<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** FIND BY QUESTION **/
	
	@Override
	public List<ChoiceQuestionMultiAnswer> findChoiceQuestionMultiAnswersByQuestion(Question question) throws BusinessException {
		return choiceQuestionMultiAnswerRepository.findByQuestion(question);
	}

	@Override
	public List<ChoiceQuestionSingleAnswer> findChoiceQuestionSingleAnswersByQuestion(Question question) throws BusinessException {
		return choiceQuestionSingleAnswerRepository.findByQuestion(question);
	}

	@Override
	public List<InputQuestionAnswer> findInputQuestionAnswersByQuestion(Question question) throws BusinessException {
		return inputQuestionAnswerRepository.findByQuestion(question);
	}

	@Override
	public List<MultiChoiceMatrixAnswer> findMultiChoiceMatrixAnswersByQuestion(Question question) throws BusinessException {
		return multiChoiceMatrixAnswerRepository.findByQuestion(question);
	}
	
	@Override
	public List<SelectionQuestionAnswer> findSelectionQuestionAnswersByQuestion(Question question) throws BusinessException {
		return selectionQuestionAnswerRepository.findByQuestion(question);
	}
	
	@Override
	public List<SingleChoiceMatrixAnswer> findSingleChoiceMatrixAnswersByQuestion(Question question) throws BusinessException {
		return singleChoiceMatrixAnswerRepository.findByQuestion(question);
	}

	/** FIND BY ID **/
	
	@Override
	public ChoiceQuestionMultiAnswer findChoiceQuestionMultiAnswerById(String id) throws BusinessException {
		return null;
	}

	@Override
	public ChoiceQuestionSingleAnswer findChoiceQuestionSingleAnswerById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputQuestionAnswer findInputQuestionAnswerById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiChoiceMatrixAnswer findMultiChoiceMatrixAnswerById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SelectionQuestionAnswer findSelectionQuestionAnswerById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleChoiceMatrixAnswer findSingleChoiceMatrixAnswerById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
