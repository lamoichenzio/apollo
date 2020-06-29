package it.univaq.disim.mwt.apollo.business;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;

public interface SurveyService {

	List<Survey> findAllSurveys() throws BusinessException;

	ResponseGrid<Survey> findAllSurveysByUserPaginated(RequestGrid request, User user) throws BusinessException;

	List<Survey> findSurveysByName(String name) throws BusinessException;

	int findSurveysCountByUserId(Long userId) throws BusinessException;

	int findSurveysActiveCountByUserId(Long userId) throws BusinessException;

	List<Survey> findSurveysByStartDateOrEndDate(LocalDate startDate, LocalDate endDate) throws BusinessException;

	Survey findSurveyById(String id) throws BusinessException;

	void createSurvey(Survey survey, MultipartFile file) throws BusinessException;

	void updateSurvey(Survey survey, MultipartFile file) throws BusinessException;

	void deleteSurvey(Survey survey) throws BusinessException;

}
