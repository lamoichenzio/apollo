package it.univaq.disim.mwt.apollo.business.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.QuestionGroupService;
import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.QuestionGroupRepository;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;

@Service
@Transactional
public class QuestionGroupServiceImpl implements QuestionGroupService {
	
	@Autowired
	QuestionGroupRepository questionGroupRepository;
	
	@Autowired
	QuestionService questionService;
	
	@Override
	@Transactional(readOnly = true)
	public List<QuestionGroup> findAllQuestionGroups() throws BusinessException {
		try {			
			return questionGroupRepository.findAll();
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public ResponseGrid<QuestionGroup> findAllQuestionGroupsPaginated(RequestGrid request) throws BusinessException {
		// TODO implement me
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public QuestionGroup findQuestionGroupById(String id) throws BusinessException {
		try {			
			return questionGroupRepository.findById(id).get();
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void createQuestionGroup(QuestionGroup questionGroup) throws BusinessException {
		try {			
			questionGroupRepository.save(questionGroup);
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void updateQuestionGroup(QuestionGroup questionGroup) throws BusinessException {
		try {			
			questionGroupRepository.save(questionGroup);
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteQuestionGroup(QuestionGroup questionGroup) throws BusinessException {
		try {	
			// Delete related questions, if exists
			if (questionGroup.getQuestions() != null && questionGroup.getQuestions().size() > 0) {
				Iterable<Question> questions = (Iterable<Question>)questionGroup.getQuestions();
				questionService.deleteQuestionList(questions);
			}
			// Delete group
			questionGroupRepository.delete(questionGroup);
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteQuestionGroupById(String id) throws BusinessException {
		try {			
			questionGroupRepository.deleteById(id);
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void deleteQuestionGroupList(Iterable<? extends QuestionGroup> entities) throws BusinessException {
		try {
			Iterator<QuestionGroup> iter = (Iterator<QuestionGroup>)entities.iterator();
			
			while(iter.hasNext()) {
				QuestionGroup group = iter.next();
				
				if (group.getQuestions() != null && group.getQuestions().size() > 0) {
					Iterable<Question> questions = (Iterable<Question>)group.getQuestions();
					questionService.deleteQuestionList(questions);
				}

			}
			questionGroupRepository.deleteAll(entities);
		} catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}
	
}
