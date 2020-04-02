package it.univaq.disim.mwt.apollo.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyRepository;
import it.univaq.disim.mwt.apollo.domain.Survey;

@Service
public class SurveyServiceImpl implements SurveyService {
	
	@Autowired
	SurveyRepository surveyRepository;

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
		return new ResponseGrid<Survey>(requestGrid.getDraw(), page.getTotalElements(), 
				page.getTotalElements(), page.getContent());
	}

	@Override
	public Survey findSurveyById(Long id) throws BusinessException {
		return surveyRepository.findFirstById(id);
	}

	@Override
	public void createSurvey(Survey survey) throws BusinessException {
		surveyRepository.save(survey);
	}

	@Override
	public void updateSurvey(Survey survey) throws BusinessException {
		surveyRepository.save(survey);
	}

	@Override
	public void deleteSurvey(Survey survey) throws BusinessException {
		surveyRepository.delete(survey);
	}
	
}
