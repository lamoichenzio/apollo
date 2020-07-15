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

	/** FIND BY QUESTION **/

	@Override
	public List<ChoiceQuestionMultiAnswer> findChoiceQuestionMultiAnswersByQuestion(ChoiceQuestion question)
			throws BusinessException {
		return choiceQuestionMultiAnswerRepository.findByQuestion(question);
	}

	@Override
	public List<ChoiceQuestionSingleAnswer> findChoiceQuestionSingleAnswersByQuestion(ChoiceQuestion question)
			throws BusinessException {
		return choiceQuestionSingleAnswerRepository.findByQuestion(question);
	}

	@Override
	public List<InputQuestionAnswer> findInputQuestionAnswersByQuestion(InputQuestion question) throws BusinessException {
		return inputQuestionAnswerRepository.findByQuestion(question);
	}

	@Override
	public List<MultiChoiceMatrixAnswer> findMultiChoiceMatrixAnswersByQuestion(MatrixQuestion question)
			throws BusinessException {
		return multiChoiceMatrixAnswerRepository.findByQuestion(question);
	}

	@Override
	public List<SelectionQuestionAnswer> findSelectionQuestionAnswersByQuestion(SelectionQuestion question)
			throws BusinessException {
		return selectionQuestionAnswerRepository.findByQuestion(question);
	}

	@Override
	public List<SingleChoiceMatrixAnswer> findSingleChoiceMatrixAnswersByQuestion(MatrixQuestion question)
			throws BusinessException {
		return singleChoiceMatrixAnswerRepository.findByQuestion(question);
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
