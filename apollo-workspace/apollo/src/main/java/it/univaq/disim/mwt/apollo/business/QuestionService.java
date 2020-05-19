package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;


public interface QuestionService {
	
	List<ChoiceQuestion> findAllChoiceQuestions() throws BusinessException;
	List<InputQuestion> findAllInputQuestions() throws BusinessException;
	List<MatrixQuestion> findAllMatrixQuestions() throws BusinessException;
	List<SelectionQuestion> findAllSelectionQuestions() throws BusinessException;
	
	ResponseGrid<ChoiceQuestion> findAllChoiceQuestionsPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<InputQuestion> findAllInputQuestionsPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<MatrixQuestion> findAllMatrixQuestionsPaginated(RequestGrid request) throws BusinessException;
	ResponseGrid<SelectionQuestion> findAllSelectionQuestionsPaginated(RequestGrid request) throws BusinessException;
	
	ChoiceQuestion findChoiceQuestionById(String id) throws BusinessException;
	InputQuestion findInputQuestionById(String id) throws BusinessException;
	MatrixQuestion findMatrixQuestionById(String id) throws BusinessException;
	SelectionQuestion findSelectionQuestionById(String id) throws BusinessException;
	
	void createQuestion(Question question, MultipartFile file) throws BusinessException;
	void updateQuestion(Question question) throws BusinessException;
	void deleteQuestion(Question question) throws BusinessException;
	
	void deleteQuestionList(Iterable<? extends Question> entities) throws BusinessException;
	
}
