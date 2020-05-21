package it.univaq.disim.mwt.apollo.business.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.QuestionFileService;
import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.InputQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.MatrixQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SelectQuestionRepository;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionFile;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	private ChoiceQuestionRepository choiceQuestionRepository;
	
	@Autowired
	private InputQuestionRepository inputQuestionRepository;
	
	@Autowired
	private MatrixQuestionRepository matrixQuestionRepository;
	
	@Autowired
	private SelectQuestionRepository selectQuestionRepository;
	
	@Autowired
	private QuestionFileService questionFileService;
	
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
	public List<SelectionQuestion> findAllSelectionQuestions() throws BusinessException {
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
	public ResponseGrid<SelectionQuestion> findAllSelectionQuestionsPaginated(RequestGrid request) throws BusinessException {
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
	public SelectionQuestion findSelectionQuestionById(String id) throws BusinessException {
		return selectQuestionRepository.findById(id).get();
	}

	@Override
	public void createQuestion(Question question, MultipartFile file) throws BusinessException {
		try {
			question.setCreationDate(new Date());
			if(file != null && !file.isEmpty()) {
				if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
					throw new BusinessException("File format not valid: "+ file.getContentType());
				}
				QuestionFile questionFile = new QuestionFile();
				questionFile.setName(file.getOriginalFilename());
				questionFile.setData(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
				questionFileService.create(questionFile);
				question.setFile(questionFile);
			}
			if(question instanceof InputQuestion) {
				inputQuestionRepository.save((InputQuestion)question);
			}
			if(question instanceof ChoiceQuestion) {
				choiceQuestionRepository.save((ChoiceQuestion)question);
			}
			if(question instanceof SelectionQuestion) {
				selectQuestionRepository.save((SelectionQuestion)question);
			}
			if(question instanceof MatrixQuestion) {
				matrixQuestionRepository.save((MatrixQuestion)question);
			}
		}catch(IOException e) {
			throw new BusinessException(e);
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void updateQuestion(Question question) throws BusinessException {
		try {
			question.setCreationDate(new Date());
			if(question instanceof InputQuestion) {
				inputQuestionRepository.save((InputQuestion)question);
			}
			if(question instanceof ChoiceQuestion) {
				choiceQuestionRepository.save((ChoiceQuestion)question);
			}
			if(question instanceof SelectionQuestion) {
				selectQuestionRepository.save((SelectionQuestion)question);
			}
			if(question instanceof MatrixQuestion) {
				matrixQuestionRepository.save((MatrixQuestion)question);
			}
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteQuestion(Question question) throws BusinessException {
		try {
			if(question.getFile()!=null) {
				questionFileService.delete(question.getFile());
			}
			if(question instanceof InputQuestion) {
				inputQuestionRepository.delete((InputQuestion)question);
			}
			if(question instanceof ChoiceQuestion) {
				choiceQuestionRepository.delete((ChoiceQuestion)question);
			}
			if(question instanceof SelectionQuestion) {
				selectQuestionRepository.delete((SelectionQuestion)question);
			}
			if(question instanceof MatrixQuestion) {
				matrixQuestionRepository.delete((MatrixQuestion)question);
			}
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void deleteQuestionList(Iterable<? extends Question> entities) throws BusinessException {
		try {
			Iterator<Question> iter = (Iterator<Question>)entities.iterator();
			
			while(iter.hasNext()) {
				deleteQuestion(iter.next());
			}

		} catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	

}
