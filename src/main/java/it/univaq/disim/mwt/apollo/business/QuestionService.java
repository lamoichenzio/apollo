package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.questions.*;
import org.springframework.web.multipart.MultipartFile;


public interface QuestionService {

	ChoiceQuestion findChoiceQuestionById(String id) throws BusinessException;
	InputQuestion findInputQuestionById(String id) throws BusinessException;
	MatrixQuestion findMatrixQuestionById(String id) throws BusinessException;
	SelectionQuestion findSelectionQuestionById(String id) throws BusinessException;
	
	void createQuestion(Question question, MultipartFile file) throws BusinessException;
	void updateQuestion(Question question, MultipartFile file, Boolean deleteFile) throws BusinessException;
	void deleteQuestion(Question question) throws BusinessException;
	
	void deleteQuestionList(Iterable<? extends Question> entities) throws BusinessException;
	
}
