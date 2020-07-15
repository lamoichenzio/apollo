package it.univaq.disim.mwt.apollo.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.AnswerService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionMultiAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionSingleAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.InputQuestionAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.MultiChoiceMatrixAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SelectionQuestionAnswerRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SingleChoiceMatrixAnswerRepository;
import it.univaq.disim.mwt.apollo.domain.answers.Answer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private ChoiceQuestionMultiAnswerRepository choiceQuestionMultiAnswerRepository;

	@Autowired
	private ChoiceQuestionSingleAnswerRepository choiceQuestionSingleAnswerRepository;

	@Autowired
	private InputQuestionAnswerRepository inputQuestionAnswerRepository;

	@Autowired
	private MultiChoiceMatrixAnswerRepository multiChoiceMatrixAnswerRepository;

	@Autowired
	private SelectionQuestionAnswerRepository selectionQuestionAnswerRepository;

	@Autowired
	private SingleChoiceMatrixAnswerRepository singleChoiceMatrixAnswerRepository;

	/** FIND BY QUESTION **/

	@Override
	@Transactional(readOnly = true)
	public List<ChoiceQuestionMultiAnswer> findChoiceQuestionMultiAnswersByQuestion(ChoiceQuestion question)
			throws BusinessException {
		try{
			return choiceQuestionMultiAnswerRepository.findByQuestion(question);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ChoiceQuestionSingleAnswer> findChoiceQuestionSingleAnswersByQuestion(ChoiceQuestion question)
			throws BusinessException {
		try{
			return choiceQuestionSingleAnswerRepository.findByQuestion(question);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<InputQuestionAnswer> findInputQuestionAnswersByQuestion(InputQuestion question) throws BusinessException {
		try{
			return inputQuestionAnswerRepository.findByQuestion(question);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<MultiChoiceMatrixAnswer> findMultiChoiceMatrixAnswersByQuestion(MatrixQuestion question)
			throws BusinessException {
		try{
			return multiChoiceMatrixAnswerRepository.findByQuestion(question);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<SelectionQuestionAnswer> findSelectionQuestionAnswersByQuestion(SelectionQuestion question)
			throws BusinessException {
		try{
			return selectionQuestionAnswerRepository.findByQuestion(question);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<SingleChoiceMatrixAnswer> findSingleChoiceMatrixAnswersByQuestion(MatrixQuestion question)
			throws BusinessException {
		try{
			return singleChoiceMatrixAnswerRepository.findByQuestion(question);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	public void createAnswer(Answer answer) throws BusinessException {
		try {
			if (answer instanceof InputQuestionAnswer) {
				inputQuestionAnswerRepository.save((InputQuestionAnswer) answer);
			}
			if (answer instanceof ChoiceQuestionSingleAnswer) {
				choiceQuestionSingleAnswerRepository.save((ChoiceQuestionSingleAnswer) answer);
			}
			if (answer instanceof ChoiceQuestionMultiAnswer) {
				choiceQuestionMultiAnswerRepository.save((ChoiceQuestionMultiAnswer) answer);
			}
			if (answer instanceof SelectionQuestionAnswer) {
				selectionQuestionAnswerRepository.save((SelectionQuestionAnswer) answer);
			}
			if (answer instanceof SingleChoiceMatrixAnswer) {
				singleChoiceMatrixAnswerRepository.save((SingleChoiceMatrixAnswer) answer);
			}
			if (answer instanceof MultiChoiceMatrixAnswer) {
				multiChoiceMatrixAnswerRepository.save((MultiChoiceMatrixAnswer) answer);
			}
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

}
