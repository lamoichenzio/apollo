package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
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
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;

public interface AnswerService {

	List<ChoiceQuestionMultiAnswer> findChoiceQuestionMultiAnswersByQuestion(ChoiceQuestion question) throws BusinessException;
	List<ChoiceQuestionSingleAnswer> findChoiceQuestionSingleAnswersByQuestion(ChoiceQuestion question) throws BusinessException;
	List<InputQuestionAnswer> findInputQuestionAnswersByQuestion(InputQuestion question) throws BusinessException;
	List<MultiChoiceMatrixAnswer> findMultiChoiceMatrixAnswersByQuestion(MatrixQuestion question) throws BusinessException;
	List<SelectionQuestionAnswer> findSelectionQuestionAnswersByQuestion(SelectionQuestion question) throws BusinessException;
	List<SingleChoiceMatrixAnswer> findSingleChoiceMatrixAnswersByQuestion(MatrixQuestion question) throws BusinessException;

	void createAnswer(Answer answer) throws BusinessException;

}
