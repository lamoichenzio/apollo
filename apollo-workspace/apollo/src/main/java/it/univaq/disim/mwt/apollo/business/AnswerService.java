package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;

public interface AnswerService {
	
	List<ChoiceQuestionMultiAnswer> findAllMultiAnswers() throws BusinessException;
	List<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswers() throws BusinessException;
	List<InputQuestionAnswer> findAllSingleAnswers() throws BusinessException;
	List<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswers() throws BusinessException;
	
	ResponseGrid<ChoiceQuestionMultiAnswer> findAllMultiAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<MultiChoiceMatrixAnswer> findAllMultiChoiceMatrixAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<InputQuestionAnswer> findAlSingleAnswersPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<SingleChoiceMatrixAnswer> findAllSingleChoiceMatrixAnswersPaginated(RequestGrid request) throws BusinessException;
	
	ChoiceQuestionMultiAnswer findMultiAnswerById(String id) throws BusinessException;
	MultiChoiceMatrixAnswer findMultiChoiceMatrixAnswerById(String id) throws BusinessException;
	InputQuestionAnswer findSingleAnswerById(String id) throws BusinessException;
	SingleChoiceMatrixAnswer findSingleChoiceMatrixAnswerById(String id) throws BusinessException;
	
	void createAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException;
	void createAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
	void createAnswer(InputQuestionAnswer answer) throws BusinessException;
	void createAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
	
	void updateAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException;
	void updateAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
	void updateAnswer(InputQuestionAnswer answer) throws BusinessException;
	void updateAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
	
	void deleteAnswer(ChoiceQuestionMultiAnswer answer) throws BusinessException;
	void deleteAnswer(MultiChoiceMatrixAnswer answer) throws BusinessException;
	void deleteAnswer(InputQuestionAnswer answer) throws BusinessException;
	void deleteAnswer(SingleChoiceMatrixAnswer answer) throws BusinessException;
}
