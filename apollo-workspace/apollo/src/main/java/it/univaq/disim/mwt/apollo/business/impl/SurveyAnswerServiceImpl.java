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
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.answers.GroupAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswerValue;
import it.univaq.disim.mwt.apollo.domain.answers.SingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceType;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;

@Service
@Transactional
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

	@Autowired
	SurveyAnswerRepository surveyAnswerRepository;
	
	@Override
	public List<SurveyAnswer> findAllSurveyAnswers() throws BusinessException {
		return surveyAnswerRepository.findAll();
	}

	@Override
	public ResponseGrid<SurveyAnswer> findAllSurveyAnswersPaginated(RequestGrid request) throws BusinessException {
		// TODO Implement me
		return null;
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
			GroupAnswer groupAnswer = new GroupAnswer();
			groupAnswer.setGroup(questionGroup);
			for(Question question : questionGroup.getQuestions()) {
				if(question instanceof InputQuestion || question instanceof SelectionQuestion) {
					SingleAnswer answer = new SingleAnswer();
					answer.setQuestion(question);
					groupAnswer.addAnswer(answer);
				}
				if(question instanceof ChoiceQuestion) {
					if(((ChoiceQuestion) question).getChoiceType().equals(ChoiceType.RADIO)) {
						SingleAnswer answer = new SingleAnswer();
						answer.setQuestion(question);
						groupAnswer.addAnswer(answer);
					}
					if(((ChoiceQuestion) question).getChoiceType().equals(ChoiceType.CHECK)) {
						MultiAnswer answer = new MultiAnswer();
						answer.setQuestion(question);
						groupAnswer.addAnswer(answer);
					}
				}
				if(question instanceof MatrixQuestion) {
					if(((MatrixQuestion) question).getType().equals(ChoiceType.RADIO)) {
						SingleChoiceMatrixAnswer answer = new SingleChoiceMatrixAnswer();
						answer.setQuestion(question);
						groupAnswer.addAnswer(answer);
					}
					if(((MatrixQuestion) question).getType().equals(ChoiceType.CHECK)) {
						MultiChoiceMatrixAnswer answer = new MultiChoiceMatrixAnswer();
						answer.setQuestion(question);
						groupAnswer.addAnswer(answer);
					}
					
				}
			}
			surveyAnswer.addGroupAnswer(groupAnswer);
		}
		return surveyAnswer;
	}
	
}
