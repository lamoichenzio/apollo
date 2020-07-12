package it.univaq.disim.mwt.apollo.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.apollo.business.AnswerService;
import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceType;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import it.univaq.disim.mwt.apollo.presentation.helpers.AnswerHelper;
import it.univaq.disim.mwt.apollo.presentation.model.AnswerResponseBody;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionType;
import it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus;

@Controller
@RequestMapping("/answers")
public class AnswerController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;

	@GetMapping("/findanswers/{type}/{questionId}")
	@ResponseBody
	public ResponseEntity<AnswerResponseBody> getAnswersData(@PathVariable("type") QuestionType type, @PathVariable("questionId") String questionId) throws BusinessException {
		
		AnswerResponseBody response = new AnswerResponseBody();
		
		// Input
		if(type.equals(QuestionType.INPUT)) {
			InputQuestion question = questionService.findInputQuestionById(questionId);
			List<InputQuestionAnswer> answers = answerService.findInputQuestionAnswersByQuestion(question);
			
			response = AnswerHelper.inputQuestionAnswers2AnswerResponseBody(question, answers);
		}
		
		// Choice
		if(type.equals(QuestionType.CHOICE)) {
			ChoiceQuestion question = questionService.findChoiceQuestionById(questionId);
			
			if(question.getChoiceType().equals(ChoiceType.RADIO)) {
				List<ChoiceQuestionSingleAnswer> answers = answerService.findChoiceQuestionSingleAnswersByQuestion(question);
				response = AnswerHelper.choiceQuestionSingleAnswers2AnswerResponseBody(question, answers);
			}
			
			if(question.getChoiceType().equals(ChoiceType.CHECK)) {
				List<ChoiceQuestionMultiAnswer> answers = answerService.findChoiceQuestionMultiAnswersByQuestion(question);
				response = AnswerHelper.choiceQuestionMultiAnswers2AnswerResponseBody(question, answers);
			}
		}
		
		// Matrix
		if(type.equals(QuestionType.MATRIX)) {
			MatrixQuestion question = questionService.findMatrixQuestionById(questionId);
			
			
			if(question.getType().equals(ChoiceType.RADIO)) {
				List<SingleChoiceMatrixAnswer> answers = answerService.findSingleChoiceMatrixAnswersByQuestion(question);
				response = AnswerHelper.singleChoiceMatrixAnswers2AnswerResponseBody(question, answers);
			}
			
			if(question.getType().equals(ChoiceType.CHECK)) {
				List<MultiChoiceMatrixAnswer> answers = answerService.findMultiChoiceMatrixAnswersByQuestion(question);
				response = AnswerHelper.multiChoiceMatrixAnswers2AnswerResponseBody(question, answers);

			}
		}
		
		// Selection
		if (type.equals(QuestionType.SELECTION)) {
			SelectionQuestion question = questionService.findSelectionQuestionById(questionId);
			List<SelectionQuestionAnswer> answers = answerService.findSelectionQuestionAnswersByQuestion(question);
			
			response = AnswerHelper.selectionQuestionAnswersAnswers2AnswerResponseBody(question, answers);
		}
		
		response.setStatus(ResponseStatus.OK);
		
		return ResponseEntity.ok(response);
	}
	
}
