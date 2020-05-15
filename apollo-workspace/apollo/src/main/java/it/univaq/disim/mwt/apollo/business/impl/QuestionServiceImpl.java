package it.univaq.disim.mwt.apollo.business.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.InputQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.MatrixQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SelectQuestionRepository;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.SelectQuestion;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	ChoiceQuestionRepository choiceQuestionRepository;
	
	@Autowired
	InputQuestionRepository inputQuestionRepository;
	
	@Autowired
	MatrixQuestionRepository matrixQuestionRepository;
	
	@Autowired
	SelectQuestionRepository selectQuestionRepository;
	
	@Override
	public List<ChoiceQuestion> findAllChoiceQuestions() throws BusinessException {
		return choiceQuestionRepository.findAll();
	}

	@Override
	public List<InputQuestion> findAllInputQuestions() throws BusinessException {
		return inputQuestionRepository.findAll();
	}

	@Override
	public List<MatrixQuestion> findAllMatrixQuestions() throws BusinessException {
		return matrixQuestionRepository.findAll();
	}

	@Override
	public List<SelectQuestion> findAllSelectQuestions() throws BusinessException {
		return selectQuestionRepository.findAll();
	}

	@Override
	public ResponseGrid<ChoiceQuestion> findAllChoiceQuestionsPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<InputQuestion> findAllInputQuestionsPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<MatrixQuestion> findAllMatrixQuestionsPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseGrid<SelectQuestion> findAllSelectQuestionsPaginated(RequestGrid request) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChoiceQuestion findChoiceQuestionById(String id) throws BusinessException {
		return choiceQuestionRepository.findById(id).get();
	}

	@Override
	public InputQuestion findInputQuestionById(String id) throws BusinessException {
		return inputQuestionRepository.findById(id).get();
	}

	@Override
	public MatrixQuestion findMatrixQuestionById(String id) throws BusinessException {
		return matrixQuestionRepository.findById(id).get();
	}

	@Override
	public SelectQuestion findSelectQuestionById(String id) throws BusinessException {
		return selectQuestionRepository.findById(id).get();
	}

	@Override
	public void createQuestion(ChoiceQuestion question, MultipartFile file) throws BusinessException {
		choiceQuestionRepository.save(question);
	}

	@Override
	public void createQuestion(InputQuestion question, MultipartFile file) throws BusinessException {
		try {
			question.setCreationDate(new Date());
			question.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
			inputQuestionRepository.save(question);
		} catch (IOException e) {
			throw new BusinessException(e);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void createQuestion(MatrixQuestion question, MultipartFile file) throws BusinessException {
		matrixQuestionRepository.save(question);
	}

	@Override
	public void createQuestion(SelectQuestion question, MultipartFile file) throws BusinessException {
		selectQuestionRepository.save(question);
	}

	@Override
	public void updateQuestion(ChoiceQuestion question) throws BusinessException {
		choiceQuestionRepository.save(question);
	}

	@Override
	public void updateQuestion(InputQuestion question) throws BusinessException {
		inputQuestionRepository.save(question);
	}

	@Override
	public void updateQuestion(MatrixQuestion question) throws BusinessException {
		matrixQuestionRepository.save(question);
	}

	@Override
	public void updateQuestion(SelectQuestion question) throws BusinessException {
		selectQuestionRepository.save(question);
	}

	@Override
	public void deleteQuestion(ChoiceQuestion question) throws BusinessException {
		choiceQuestionRepository.delete(question);
	}

	@Override
	public void deleteQuestion(InputQuestion question) throws BusinessException {
		inputQuestionRepository.delete(question);
	}

	@Override
	public void deleteQuestion(MatrixQuestion question) throws BusinessException {
		matrixQuestionRepository.delete(question);
	}

	@Override
	public void deleteQuestion(SelectQuestion question) throws BusinessException {
			selectQuestionRepository.delete(question);
	}

}
