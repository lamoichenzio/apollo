package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.Question;

public interface AnswerService {
	
	List<ChoiceQuestionMultiAnswer> findAllChoiceQuestionMultiAnswers() throws BusinessException;
	List<ChoiceQuestionSingleAnswer> findAllChoiceQuestionSingleAnswer() throws BusinessException;
	List<InputQuestionAnswer> findAllInputQuestionAnswers() throws BusinessException;
	List<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswer() throws BusinessException;
	List<SelectionQuestionAnswer> findAllSelectionQuestionAnswers() throws BusinessException;
	List<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswers() throws BusinessException;
	
	ResponseGrid<ChoiceQuestionMultiAnswer> findAllChoiceQuestionMultiAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<ChoiceQuestionSingleAnswer> findAllChoiceQuestionSingleAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<InputQuestionAnswer> findAllInputQuestionAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<SelectionQuestionAnswer> findAllSelectionQuestionAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswersPaginated(RequestGrid request) throws BusinessException;

	List<ChoiceQuestionMultiAnswer> findChoiceQuestionMultiAnswersByQuestion(Question question) throws BusinessException;
	List<ChoiceQuestionSingleAnswer> findChoiceQuestionSingleAnswersByQuestion(Question question) throws BusinessException;
	List<InputQuestionAnswer> findInputQuestionAnswersByQuestion(Question question) throws BusinessException;
	List<MultiChoiceMatrixAnswer> findMultiChoiceMatrixAnswersByQuestion(Question question) throws BusinessException;
	List<SelectionQuestionAnswer> findSelectionQuestionAnswersByQuestion(Question question) throws BusinessException;
	List<SingleChoiceMatrixAnswer> findSingleChoiceMatrixAnswersByQuestion(Question question) throws BusinessException;

	ChoiceQuestionMultiAnswer findChoiceQuestionMultiAnswerById(String id) throws BusinessException;
	ChoiceQuestionSingleAnswer findChoiceQuestionSingleAnswerById(String id) throws BusinessException;
	InputQuestionAnswer findInputQuestionAnswerById(String id) throws BusinessException;
	MultiChoiceMatrixAnswer findMultiChoiceMatrixAnswerById(String id) throws BusinessException;
	SelectionQuestionAnswer findSelectionQuestionAnswerById(String id) throws BusinessException;
	SingleChoiceMatrixAnswer findSingleChoiceMatrixAnswerById(String id) throws BusinessException;

//	void createAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException;
//	void createAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
//	void createAnswer(InputQuestionAnswer answer) throws BusinessException;
//	void createAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
//	
//	void updateAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException;
//	void updateAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
//	void updateAnswer(InputQuestionAnswer answer) throws BusinessException;
//	void updateAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
//	
//	void deleteAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException;
//	void deleteAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
//	void deleteAnswer(InputQuestionAnswer answer) throws BusinessException;
//	void deleteAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
}
