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
	SurveyAnswerRepository surveyAnswerRepository;
	
	@Override
	public List<SurveyAnswer> findAllSurveyAnswers() throws BusinessException {
		return surveyAnswerRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseGrid<SurveyAnswer> findAllSurveyAnswersPaginated(RequestGrid request, Survey survey) throws BusinessException {
		try{
			SurveyAnswer surveyAnswer = new SurveyAnswer();
			surveyAnswer.setSurvey(survey);

			ExampleMatcher matcher = ExampleMatcher.matchingAll()
					.withMatcher("survey", GenericPropertyMatchers.ignoreCase())
					.withIgnoreNullValues();
			Example<SurveyAnswer> example = Example.of(surveyAnswer, matcher);

			Pageable pageable = ConversionUtility.requestGrid2Pageable(request);
			Page<SurveyAnswer> page = surveyAnswerRepository.findAll(example, pageable);
			page.getContent().forEach(item -> {
				log.info(item.toString());
			});

			return new ResponseGrid<SurveyAnswer>(request.getDraw(), page.getTotalElements(), page.getTotalElements(),
					page.getContent());
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}

	}

	@Override
	public SurveyAnswer findSurveyAnswerById(String id) throws BusinessException {
		return surveyAnswerRepository.findById(id).get();
	}

	@Override
	public void createSurveyAnswer(SurveyAnswer userAnswer) throws BusinessException {
		surveyAnswerRepository.save(userAnswer);
	}

	@Override
	public void updateSurveyAnswer(SurveyAnswer userAnswer) throws BusinessException {
		surveyAnswerRepository.save(userAnswer);
	}

	@Override
	public void deleteSurveyAnswer(SurveyAnswer userAnswer) throws BusinessException {
		surveyAnswerRepository.delete(userAnswer);
	}

	@Override
	public void deleteSurveyAnswerById(String id) throws BusinessException {
		surveyAnswerRepository.deleteById(id);
	}

	@Override
	public SurveyAnswer mapSurveyToSurveyAnswer(Survey survey) throws BusinessException {
		SurveyAnswer surveyAnswer = new SurveyAnswer();
		surveyAnswer.setSurvey(survey);
		for(QuestionGroup questionGroup : survey.getQuestionGroups()) {
			for(Question question : questionGroup.getQuestions()) {
				if(question instanceof InputQuestion) {
					InputQuestionAnswer answer = new InputQuestionAnswer();
					answer.setQuestion((InputQuestion)question);
					surveyAnswer.addInputQuestionAnswer(answer);
				}
				if(question instanceof ChoiceQuestion) {
					if(((ChoiceQuestion) question).getChoiceType().equals(ChoiceType.RADIO)) {
						ChoiceQuestionSingleAnswer answer = new ChoiceQuestionSingleAnswer();
						answer.setQuestion((ChoiceQuestion)question);
						surveyAnswer.addChoiceQuestionSingleAnswer(answer);
					}
					if(((ChoiceQuestion) question).getChoiceType().equals(ChoiceType.CHECK)) {
						ChoiceQuestionMultiAnswer answer = new ChoiceQuestionMultiAnswer();
						answer.setQuestion((ChoiceQuestion)question);
						surveyAnswer.addChoiceQuestionMultiAnswer(answer);
					}
				}
				if(question instanceof SelectionQuestion) {
					SelectionQuestionAnswer answer = new SelectionQuestionAnswer();
					answer.setQuestion((SelectionQuestion)question);
					surveyAnswer.addSelectionQuestionAnswer(answer);
				}
				if(question instanceof MatrixQuestion) {
					if(((MatrixQuestion) question).getType().equals(ChoiceType.RADIO)) {
						SingleChoiceMatrixAnswer answer = new SingleChoiceMatrixAnswer();
						answer.setQuestion((MatrixQuestion)question);
						surveyAnswer.addSingleChoiceMatrixAnswer(answer);
					}
					if(((MatrixQuestion) question).getType().equals(ChoiceType.CHECK)) {
						MultiChoiceMatrixAnswer answer = new MultiChoiceMatrixAnswer();
						answer.setQuestion((MatrixQuestion)question);
						surveyAnswer.addMultiChoiceMatrixAnswer(answer);
					}
					
				}
			}
		}
		return surveyAnswer;
	}
	
}
