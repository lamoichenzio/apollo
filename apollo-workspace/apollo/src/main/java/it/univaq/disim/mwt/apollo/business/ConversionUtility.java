package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.domain.DocumentFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ConversionUtility {
	
	public static Pageable requestGrid2Pageable(RequestGrid requestGrid) {
        return PageRequest.of((requestGrid.getStart().intValue() / requestGrid.getLength().intValue()), requestGrid.getLength().intValue(),
                "asc".equals(requestGrid.getSortDir()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                requestGrid.getSortCol());
    }


    /**
    * Builds a SurveyAnswer object based on the survey passed as input
    * @param survey The survey used to create the answer
    *
     */
	public static SurveyAnswer survey2SurveyAnswer(Survey survey){
		SurveyAnswer surveyAnswer = new SurveyAnswer();
		surveyAnswer.setSurvey(survey);
		for (QuestionGroup questionGroup : survey.getQuestionGroups()) {
			for (Question question : questionGroup.getQuestions()) {

				if (question instanceof InputQuestion) {
					InputQuestionAnswer answer = new InputQuestionAnswer();
					answer.setQuestion((InputQuestion) question);
					surveyAnswer.addInputQuestionAnswer(answer);
				}
				if (question instanceof ChoiceQuestion) {
					if (((ChoiceQuestion) question).getChoiceType().equals(ChoiceType.RADIO)) {
						ChoiceQuestionSingleAnswer answer = new ChoiceQuestionSingleAnswer();
						answer.setQuestion((ChoiceQuestion) question);
						surveyAnswer.addChoiceQuestionSingleAnswer(answer);
					}
					if (((ChoiceQuestion) question).getChoiceType().equals(ChoiceType.CHECK)) {
						ChoiceQuestionMultiAnswer answer = new ChoiceQuestionMultiAnswer();
						answer.setQuestion((ChoiceQuestion) question);
						surveyAnswer.addChoiceQuestionMultiAnswer(answer);
					}
				}
				if (question instanceof SelectionQuestion) {
					SelectionQuestionAnswer answer = new SelectionQuestionAnswer();
					answer.setQuestion((SelectionQuestion) question);
					surveyAnswer.addSelectionQuestionAnswer(answer);
				}
				if (question instanceof MatrixQuestion) {
					if (((MatrixQuestion) question).getType().equals(ChoiceType.RADIO)) {
						SingleChoiceMatrixAnswer answer = new SingleChoiceMatrixAnswer();
						answer.setQuestion((MatrixQuestion) question);
						surveyAnswer.addSingleChoiceMatrixAnswer(answer);
					}
					if (((MatrixQuestion) question).getType().equals(ChoiceType.CHECK)) {
						MultiChoiceMatrixAnswer answer = new MultiChoiceMatrixAnswer();
						answer.setQuestion((MatrixQuestion) question);
						surveyAnswer.addMultiChoiceMatrixAnswer(answer);
					}

				}
			}
		}
		return surveyAnswer;
	}

	public static DocumentFile multipartFile2DocumentFile(MultipartFile file) throws IOException {
		DocumentFile icon = new DocumentFile();
		icon.setName(file.getOriginalFilename());
		icon.setData(new String(Base64.getEncoder().encode(file.getBytes()), StandardCharsets.UTF_8));
		return icon;
	}
}
