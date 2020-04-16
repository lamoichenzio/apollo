package it.univaq.disim.mwt.apollo.business.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.business.QuestionGroupService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.QuestionGroupRepository;
import it.univaq.disim.mwt.apollo.domain.QuestionGroup;

@Service
@Transactional
public class QuestionGroupServiceImpl implements QuestionGroupService {
	
	@Autowired
	QuestionGroupRepository questionGroupRepository;
	
	@Override
	public List<QuestionGroup> findAllQuestionGroups() throws BusinessException {
		return questionGroupRepository.findAll();
	}

	@Override
	public ResponseGrid<QuestionGroup> findAllQuestionGroupsPaginated(RequestGrid request) throws BusinessException {
		// TODO implement me
		return null;
	}

	@Override
	public QuestionGroup findQuestionById(String id) throws BusinessException {
		return questionGroupRepository.findFirstById(id);
	}

	@Override
	public void createQuestionGrpup(QuestionGroup questionGroup) throws BusinessException {
		questionGroupRepository.save(questionGroup);
	}

	@Override
	public void updateQuestionGroup(QuestionGroup questionGroup) throws BusinessException {
		questionGroupRepository.save(questionGroup);
	}

	@Override
	public void deleteQuestionGroup(QuestionGroup questionGroup) throws BusinessException {
		questionGroupRepository.delete(questionGroup);
	}

}
