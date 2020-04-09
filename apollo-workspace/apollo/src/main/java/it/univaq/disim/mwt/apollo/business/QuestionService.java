package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.domain.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.SelectQuestion;


public interface QuestionService {
	
	List<ChoiceQuestion> findAllChoiceQuestions() throws BusinessException;
	List<InputQuestion> findAllInputQuestions() throws BusinessException;
	List<MatrixQuestion> findAllMatrixQuestions() throws BusinessException;
	List<SelectQuestion> findAllSelectQuestions() throws BusinessException;
	
	ResponseGrid<ChoiceQuestion> findAllChoiceQuestionsPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<InputQuestion> findAllInputQuestionsPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<MatrixQuestion> findAllMatrixQuestionsPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<SelectQuestion> findAllSelectQuestionsPaginated(RequestGrid request) throws BusinessException;
	
	ChoiceQuestion findChoiceQuestionById(String id) throws BusinessException;
	InputQuestion findInputQuestionById(String id) throws BusinessException;
	MatrixQuestion findMatrixQuestionById(String id) throws BusinessException;
	SelectQuestion findSelectQuestionById(String id) throws BusinessException;
	
	void createQuestion(ChoiceQuestion question) throws BusinessException;
	void createQuestion(InputQuestion question) throws BusinessException;
	void createQuestion(MatrixQuestion question) throws BusinessException;
	void createQuestion(SelectQuestion question) throws BusinessException;
	
	void updateQuestion(ChoiceQuestion question) throws BusinessException;
	void updateQuestion(InputQuestion question) throws BusinessException;
	void updateMatrixQuestion(MatrixQuestion question) throws BusinessException;
	void updateSelectQuestion(SelectQuestion question) throws BusinessException;
	
	void deleteQuestion(ChoiceQuestion question) throws BusinessException;
	void deleteQuestion(InputQuestion question) throws BusinessException;
	void deleteQuestion(MatrixQuestion question) throws BusinessException;
	void deleteQuestion(SelectQuestion question) throws BusinessException;
}
