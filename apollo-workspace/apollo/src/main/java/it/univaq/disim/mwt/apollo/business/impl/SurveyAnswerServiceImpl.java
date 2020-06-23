package it.univaq.disim.mwt.apollo.business.impl;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.ConversionUtility;
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
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;
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

			ExampleMatcher matcher = ExampleMatcher
					.matchingAll()
					.withMatcher("survey", GenericPropertyMatchers.ignoreCase())
					.withIgnorePaths("inputQuestionAnswers")
					.withIgnorePaths("choiceQuestionSingleAnswers")
					.withIgnorePaths("selectionQuestionAnswers")
					.withIgnorePaths("choiceQuestionMultiAnswers")
					.withIgnorePaths("singleChoiceMatrixAnswers")
					.withIgnorePaths("multiChoiceMatrixAnswers")
					.withIgnorePaths("totAnswers")
					.withIgnoreNullValues();
			
			Example<SurveyAnswer> example = Example.of(surveyAnswer, matcher);

			Pageable pageable = ConversionUtility.requestGrid2Pageable(request);
			Page<SurveyAnswer> page = surveyAnswerRepository.findAll(example, pageable);
			
			log.info(page.getContent().toString());
			
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

		int totAnswers = 0;

		try {
			for (InputQuestionAnswer answer : inputQuestionAnswers) {
				if (answer.getAnswer() != null && !answer.getAnswer().equals("")) {
					totAnswers += 1;
				}
				answerService.createAnswer(answer);

			}
			for (ChoiceQuestionSingleAnswer answer : choiceQuestionSingleAnswers) {
				if (answer.getAnswer()!= null && !answer.getAnswer().equals("")) {
					totAnswers += 1;
				}
				answerService.createAnswer(answer);
			}
			for (ChoiceQuestionMultiAnswer answer : choiceQuestionMultiAnswers) {
				if (answer.getAnswers() != null && answer.getAnswers().size() > 0) {
					totAnswers += 1;
				}
				answerService.createAnswer(answer);
			}
			for (SelectionQuestionAnswer answer : selectionQuestionAnswers) {
				if (answer.getAnswer() != null && !answer.getAnswer().equals("")) {
					totAnswers += 1;
				}
				answerService.createAnswer(answer);
			}
			for (SingleChoiceMatrixAnswer answer : singleChoiceMatrixAnswers) {
				if (answer.getAnswers() != null && answer.getAnswers().size() > 0) {
					totAnswers += 1;
				}
				answerService.createAnswer(answer);
			}
			for (MultiChoiceMatrixAnswer answer : multiChoiceMatrixAnswers) {
				if (answer.getAnswers() != null && answer.getAnswers().size() > 0) {
					totAnswers += 1;
				}
				answerService.createAnswer(answer);
			}
			
			surveyAnswer.setTotAnswers(totAnswers);
			surveyAnswerRepository.save(surveyAnswer);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public boolean surveyExistsBySurveyAndEmail(Survey survey, String email) throws BusinessException {
		try{
			return surveyAnswerRepository.existsBySurveyAndEmail(survey, email);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

}
