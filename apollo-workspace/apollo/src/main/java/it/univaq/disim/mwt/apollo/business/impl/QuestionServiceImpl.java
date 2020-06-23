package it.univaq.disim.mwt.apollo.business.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import it.univaq.disim.mwt.apollo.business.ConversionUtility;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.DocumentFileService;
import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.InputQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.MatrixQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SelectQuestionRepository;
import it.univaq.disim.mwt.apollo.domain.DocumentFile;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
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
	private DocumentFileService documentFileService;

	@Override
	@Transactional(readOnly = true)
	public ChoiceQuestion findChoiceQuestionById(String id) throws BusinessException {
		try{
			return choiceQuestionRepository.findById(id).get();
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public InputQuestion findInputQuestionById(String id) throws BusinessException {
		try {
			return inputQuestionRepository.findById(id).get();
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MatrixQuestion findMatrixQuestionById(String id) throws BusinessException {
		try{
			return matrixQuestionRepository.findById(id).get();
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public SelectionQuestion findSelectionQuestionById(String id) throws BusinessException {
		try{
			return selectQuestionRepository.findById(id).get();
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	public void createQuestion(Question question, MultipartFile file) throws BusinessException {
		if(!question.getQuestionGroup().getSurvey().isActive()) {
			try {
				question.setCreationDate(new Date());
				if (file != null && !file.isEmpty()) {
					DocumentFile documentFile = ConversionUtility.multipartFile2DocumentFile(file);
					documentFileService.create(documentFile);
					question.setFile(documentFile);
				}
				saveQuestion(question);
			} catch (IOException | DataAccessException e) {
				throw new BusinessException(e);
			}
		}
	}

	@Override
	public void updateQuestion(Question question, MultipartFile file) throws BusinessException {
		if(!question.getQuestionGroup().getSurvey().isActive()) {
			try {
				question.setCreationDate(new Date());
				if (file != null && !file.isEmpty()) {
					DocumentFile documentFile = ConversionUtility.multipartFile2DocumentFile(file);
					documentFileService.create(documentFile);
					if (question.getFile() != null) {
						documentFileService.delete(question.getFile());
					}
					question.setFile(documentFile);
				}
				saveQuestion(question);
			} catch (DataAccessException | IOException e) {
				throw new BusinessException(e);
			}
		}
	}

	@Override
	public void deleteQuestion(Question question) throws BusinessException {
		if(!question.getQuestionGroup().getSurvey().isActive()) {
			try {
				if (question.getFile() != null) {
					documentFileService.delete(question.getFile());
				}
				if (question instanceof InputQuestion) {
					inputQuestionRepository.delete((InputQuestion) question);
				}
				if (question instanceof ChoiceQuestion) {
					choiceQuestionRepository.delete((ChoiceQuestion) question);
				}
				if (question instanceof SelectionQuestion) {
					selectQuestionRepository.delete((SelectionQuestion) question);
				}
				if (question instanceof MatrixQuestion) {
					matrixQuestionRepository.delete((MatrixQuestion) question);
				}
			} catch (DataAccessException e) {
				throw new BusinessException(e);
			}
		}
	}
	
	@Override
	public void deleteQuestionList(Iterable<? extends Question> entities) throws BusinessException {
		try {
			for(Question question : entities){
				deleteQuestion(question);
			}
		} catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}


	private void saveQuestion(Question question) {
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
	}

}
