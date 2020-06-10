package it.univaq.disim.mwt.apollo.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.AnswerService;
import it.univaq.disim.mwt.apollo.business.SurveyAnswerService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyAnswerRepository;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.answers.Answer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceType;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

	@Autowired
	private SurveyAnswerRepository surveyAnswerRepository;

	@Autowired
	private AnswerService answerService;

	@Override
	public List<SurveyAnswer> findAllSurveyAnswers() throws BusinessException {
		return surveyAnswerRepository.findAll();
	}

	@Override
	public List<SurveyAnswer> findAllBySurvey(Survey survey) throws BusinessException {
		return surveyAnswerRepository.findAllBySurvey(survey);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ResponseGrid<SurveyAnswer> findAllSurveyAnswersPaginated(RequestGrid request, Survey survey)
			throws BusinessException {
		try {
			SurveyAnswer surveyAnswer = new SurveyAnswer();
			surveyAnswer.setSurvey(survey);

			ExampleMatcher matcher = ExampleMatcher.matchingAll()
					.withMatcher("survey", GenericPropertyMatchers.ignoreCase()).withIgnoreNullValues();
			Example<SurveyAnswer> example = Example.of(surveyAnswer, matcher);

			Pageable pageable = ConversionUtility.requestGrid2Pageable(request);
			Page<SurveyAnswer> page = surveyAnswerRepository.findAll(example, pageable);
			page.getContent().forEach(item -> {
				log.info(item.toString());
			});

			return new ResponseGrid<SurveyAnswer>(request.getDraw(), page.getTotalElements(), page.getTotalElements(),
					page.getContent());
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public SurveyAnswer findSurveyAnswerById(String id) throws BusinessException {
		return surveyAnswerRepository.findById(id).get();
	}

	@Override
	public void createSurveyAnswer(SurveyAnswer surveyAnswer) throws BusinessException {

		List<InputQuestionAnswer> inputQuestionAnswers = surveyAnswer.getInputQuestionAnswers();
		List<ChoiceQuestionSingleAnswer> choiceQuestionSingleAnswers = surveyAnswer.getChoiceQuestionSingleAnswers();
		List<ChoiceQuestionMultiAnswer> choiceQuestionMultiAnswers = surveyAnswer.getChoiceQuestionMultiAnswers();
		List<SelectionQuestionAnswer> selectionQuestionAnswers = surveyAnswer.getSelectionQuestionAnswers();
		List<SingleChoiceMatrixAnswer> singleChoiceMatrixAnswers = surveyAnswer.getSingleChoiceMatrixAnswers();
		List<MultiChoiceMatrixAnswer> multiChoiceMatrixAnswers = surveyAnswer.getMultiChoiceMatrixAnswers();

		int totAnswers = inputQuestionAnswers.size() + choiceQuestionSingleAnswers.size()
				+ choiceQuestionMultiAnswers.size() + selectionQuestionAnswers.size() + singleChoiceMatrixAnswers.size()
				+ multiChoiceMatrixAnswers.size();

		surveyAnswer.setTotAnswers(totAnswers);
		try {
			for (Answer answer : inputQuestionAnswers) {
				answerService.createAnswer(answer);
			}
			for (Answer answer : choiceQuestionSingleAnswers) {
				answerService.createAnswer(answer);
			}
			for (Answer answer : choiceQuestionMultiAnswers) {
				answerService.createAnswer(answer);
			}
			for (Answer answer : selectionQuestionAnswers) {
				answerService.createAnswer(answer);
			}
			for (Answer answer : singleChoiceMatrixAnswers) {
				answerService.createAnswer(answer);
			}
			for (Answer answer : multiChoiceMatrixAnswers) {
				answerService.createAnswer(answer);
			}
			surveyAnswerRepository.save(surveyAnswer);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

}
