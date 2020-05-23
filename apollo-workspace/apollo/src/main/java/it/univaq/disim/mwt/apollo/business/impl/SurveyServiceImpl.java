package it.univaq.disim.mwt.apollo.business.impl;

import it.univaq.disim.mwt.apollo.business.DocumentFileService;
import it.univaq.disim.mwt.apollo.business.QuestionGroupService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyRepository;
import it.univaq.disim.mwt.apollo.domain.DocumentFile;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	SurveyRepository surveyRepository;
	
	@Autowired
	QuestionGroupService questionGroupService;

	@Autowired
	DocumentFileService documentFileService;
	
	@Override
	public List<Survey> findAllSurveys() throws BusinessException {
		return surveyRepository.findAll();
	}

	@Override
	public ResponseGrid<Survey> findAllSurveysPaginated(RequestGrid requestGrid) throws BusinessException {
		Survey survey = new Survey();

		survey.setName(requestGrid.getSearch().getValue());

		ExampleMatcher matcher = ExampleMatcher.matchingAny()
				.withMatcher("name", GenericPropertyMatchers.ignoreCase());
		Example<Survey> example = Example.of(survey, matcher);

		Pageable pageable = ConversionUtility.requestGrid2Pageable(requestGrid);
		Page<Survey> page = surveyRepository.findAll(example, pageable);

		return new ResponseGrid<Survey>(requestGrid.getDraw(), page.getTotalElements(), page.getTotalElements(),
				page.getContent());
	}

	@Override
	public ResponseGrid<Survey> findAllSurveysByUserPaginated(RequestGrid requestGrid, User user)
			throws BusinessException {
		Survey survey = new Survey();
		survey.setName(requestGrid.getSearch().getValue());
		survey.setUser(user);

		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("name", GenericPropertyMatchers.ignoreCase())
				.withIgnoreNullValues();
		Example<Survey> example = Example.of(survey, matcher);

		Pageable pageable = ConversionUtility.requestGrid2Pageable(requestGrid);
		Page<Survey> page = surveyRepository.findAll(example, pageable);
		page.getContent().forEach(item -> {
			log.info("qui");
			log.info(item.toString());
		});
		
		return new ResponseGrid<Survey>(requestGrid.getDraw(), page.getTotalElements(), page.getTotalElements(),
				page.getContent());
	}

	@Override
	public List<Survey> findSurveysByName(String name) throws BusinessException {
		return surveyRepository.findSurveysByName(name);
	}

	@Override
	public Survey findSurveyById(String id) throws BusinessException {
		return surveyRepository.findFirstById(id);
	}

	@Override
	public void createSurvey(Survey survey, MultipartFile file) throws BusinessException {
		try {
			if(file != null && !file.isEmpty()) {
				if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
					throw new BusinessException("File format not valid: "+ file.getContentType());
				}
				DocumentFile icon = new DocumentFile();
				icon.setName(file.getOriginalFilename());
				icon.setData(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
				documentFileService.create(icon);
				survey.setIcon(icon);
			}
			
			surveyRepository.save(survey);
		}catch(IOException | DataAccessException e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public void updateSurvey(Survey survey, MultipartFile file) throws BusinessException {
		try {
			if(file != null && !file.isEmpty()) {
				if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
					throw new BusinessException("File format not valid: "+ file.getContentType());
				}
				DocumentFile icon = new DocumentFile();
				icon.setName(file.getOriginalFilename());
				icon.setData(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
				if(survey.getIcon() != null){
					// Delete old icon
					documentFileService.delete(survey.getIcon());
				}
				// Set new icon
				documentFileService.create(icon);
				survey.setIcon(icon);
			}
			
			surveyRepository.save(survey);
		}catch(IOException | DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteSurvey(Survey survey) throws BusinessException {
		
		if (survey.getQuestionGroups() != null && survey.getQuestionGroups().size() > 0) {
			Iterable<QuestionGroup> groups = (Iterable<QuestionGroup>)survey.getQuestionGroups();
			questionGroupService.deleteQuestionGroupList(groups);
		} 
		
		surveyRepository.delete(survey);

	}

}
