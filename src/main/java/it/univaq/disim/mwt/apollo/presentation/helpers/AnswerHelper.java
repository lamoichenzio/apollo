package it.univaq.disim.mwt.apollo.presentation.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputType;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import it.univaq.disim.mwt.apollo.presentation.model.AnswerBody;
import it.univaq.disim.mwt.apollo.presentation.model.AnswerResponseBody;
import it.univaq.disim.mwt.apollo.presentation.model.AnswerType;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionBody;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionType;

public class AnswerHelper {

	/**
	 * Map input question answers to answer response body.
	 * @param question InputQuestion
	 * @param answers List<InputQuestionAnswer>
	 * @return AnswerResponseBody
	 */
	public static AnswerResponseBody inputQuestionAnswers2AnswerResponseBody(InputQuestion question, List<InputQuestionAnswer> answers) {
		AnswerResponseBody response = new AnswerResponseBody();

		// Set QuestionBody
		response.setQuestion(QuestionBody.builder()
				.id(question.getId())
				.type(QuestionType.INPUT)
				.build());
		
		// Set Answers
		for(InputQuestionAnswer answer : answers) {
			AnswerBody body = AnswerBody.builder()
					.answers(new ArrayList<String>(Arrays.asList(answer.getAnswer())))
					.build();
			response.addAnswerBody(body);
		}
		
		if (question.getType().equals(InputType.TEXT) || question.getType().equals(InputType.TEXTAREA)) {
			response.setType(AnswerType.TEXT);
		} else if (question.getType().equals(InputType.NUMBER)) {
			response.setType(AnswerType.NUMBER);
		} else if (question.getType().equals(InputType.DATE)) {
			response.setType(AnswerType.DATE);
		}
		
		return response;
	}
	
	/**
	 * Map choice question single answers to answer response body.
	 * @param question ChoiceQuestion
	 * @param answers List<ChoiceQuestionSingleAnswer>
	 * @return AnswerResponseBody
	 */
	public static AnswerResponseBody choiceQuestionSingleAnswers2AnswerResponseBody(ChoiceQuestion question, List<ChoiceQuestionSingleAnswer> answers) {
		AnswerResponseBody response = new AnswerResponseBody();
		
		AnswerHelper.setChoiceQuestionInAnswerResponseBody(question, response);
		
		// Set Answers
		for(ChoiceQuestionSingleAnswer answer : answers) {
			AnswerBody body = AnswerBody.builder()
					.answers(new ArrayList<String>(Arrays.asList(answer.getAnswer())))
					.build();
			response.addAnswerBody(body);
		}
		
		response.setType(AnswerType.SINGLE);
		
		return response;
	}
	
	/**
	 * Map choice question multi answers to answer response body.
	 * @param question ChoiceQuestion
	 * @param answers List<ChoiceQuestionMultiAnswer>
	 * @return AnswerResponseBody
	 */
	public static AnswerResponseBody choiceQuestionMultiAnswers2AnswerResponseBody(ChoiceQuestion question, List<ChoiceQuestionMultiAnswer> answers) {
		AnswerResponseBody response = new AnswerResponseBody();
		
		AnswerHelper.setChoiceQuestionInAnswerResponseBody(question, response);
		
		// Set Answers
		for(ChoiceQuestionMultiAnswer answer : answers) {
			AnswerBody body = AnswerBody.builder()
					.answers(answer.getAnswers())
					.build();
			response.addAnswerBody(body);
		}
		response.setType(AnswerType.MULTIPLE);
		
		return response;
	}
	
	/**
	 * Map single choice matrix answers to answer response body.
	 * @param question MatrixQuestion
	 * @param answers List<SingleChoiceMatrixAnswer>
	 * @return AnswerResponseBody
	 */
	public static AnswerResponseBody singleChoiceMatrixAnswers2AnswerResponseBody(MatrixQuestion question, List<SingleChoiceMatrixAnswer> answers) {
		AnswerResponseBody response = new AnswerResponseBody();
		
		AnswerHelper.setMatrixQuestionInAnswerResponseBody(question, response);
		
		// Set Answers
		for(SingleChoiceMatrixAnswer answer : answers) {
			Map<String, List<String>> awr = new HashMap<>();
			
			for (String key : answer.getAnswers().keySet()) {
				awr.put(key, Arrays.asList(answer.getAnswers().get(key)));
			}
			
			AnswerBody body = AnswerBody.builder()
					.matrixAnswers(awr)
					.build();
			response.addAnswerBody(body);
		}
		response.setType(AnswerType.SINGLE);
		
		return response;
	}
	
	/**
	 * Map multi choice matrix answers to answer response body.
	 * @param question MatrixQuestion
	 * @param answers List<MultiChoiceMatrixAnswer>
	 * @return AnswerResponseBody
	 */
	public static AnswerResponseBody multiChoiceMatrixAnswers2AnswerResponseBody(MatrixQuestion question, List<MultiChoiceMatrixAnswer> answers) {
		AnswerResponseBody response = new AnswerResponseBody();
		
		AnswerHelper.setMatrixQuestionInAnswerResponseBody(question, response);
		
		// Set Answers
		for(MultiChoiceMatrixAnswer answer : answers) {
			Map<String, List<String>> awr = new HashMap<>();
			
			for (String key : answer.getAnswers().keySet()) {
				awr.put(key, answer.getAnswers().get(key).getValues());
			}
			
			response.addAnswerBody(AnswerBody.builder().matrixAnswers(awr).build());
		}
		response.setType(AnswerType.MULTIPLE);
		
		return response;
	}
	
	/**
	 * Map selection question answers to answer response body.
	 * @param question SelectionQuestion
	 * @param answers List<SelectionQuestionAnswer>
	 * @return AnswerResponseBody
	 */
	public static AnswerResponseBody selectionQuestionAnswersAnswers2AnswerResponseBody(SelectionQuestion question, List<SelectionQuestionAnswer> answers) {
		AnswerResponseBody response = new AnswerResponseBody();
		
		// Set QuestionBody
		response.setQuestion(QuestionBody.builder()
				.id(question.getId())
				.options(question.getOptions())
				.type(QuestionType.SELECTION)
				.build());
		
		// Set Answers
		for(SelectionQuestionAnswer answer : answers) {
			AnswerBody body = AnswerBody.builder()
					.answers(new ArrayList<String>(Arrays.asList(answer.getAnswer())))
					.build();
			response.addAnswerBody(body);
		}
		response.setType(AnswerType.SINGLE);
		
		return response;
	}
	
	/**
	 * Set choice question in answer response body. 
	 * @param question ChoiceQuestion
	 * @param response AnswerResponseBody
	 */
	private static void setChoiceQuestionInAnswerResponseBody(ChoiceQuestion question, AnswerResponseBody response) {
		// Set QuestionBody
		response.setQuestion(QuestionBody.builder()
				.id(question.getId())
				.options(question.getOptions())
				.type(QuestionType.CHOICE)
				.otherChoice(question.isOtherChoice())
				.build());
	}
	
	/**
	 * Set matrix question in answer response body. 
	 * @param question ChoiceQuestion
	 * @param response AnswerResponseBody
	 */
	private static void setMatrixQuestionInAnswerResponseBody(MatrixQuestion question, AnswerResponseBody response) {
		// Set QuestionBody
		response.setQuestion(QuestionBody.builder()
				.id(question.getId())
				.options(question.getOptions())
				.optionValues(question.getOptionValues())
				.type(QuestionType.MATRIX)
				.build());
	}
}
