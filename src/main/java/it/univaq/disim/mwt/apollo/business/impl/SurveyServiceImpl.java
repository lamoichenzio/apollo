package it.univaq.disim.mwt.apollo.business.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.ConversionUtility;
import it.univaq.disim.mwt.apollo.business.SurveyFileService;
import it.univaq.disim.mwt.apollo.business.InvitationPoolService;
import it.univaq.disim.mwt.apollo.business.QuestionGroupService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyRepository;
import it.univaq.disim.mwt.apollo.domain.SurveyFile;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionGroupService questionGroupService;

    @Autowired
    private SurveyFileService surveyFileService;

    @Autowired
    private InvitationPoolService invitationPoolService;

    @Override
    @Transactional(readOnly = true)
    public ResponseGrid<Survey> findAllSurveysByUserPaginated(RequestGrid requestGrid, User user)
            throws BusinessException {
        try {
            Survey survey = new Survey();
            survey.setName(requestGrid.getSearch().getValue());
            survey.setUser(user);

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withMatcher("name", GenericPropertyMatchers.contains().ignoreCase())
                    .withIgnorePaths("secret")
                    .withIgnorePaths("active")
                    .withIgnorePaths("questionGroups")
                    .withIgnorePaths("user.password", "user.email", "user.username", "user.firstname", "user.lastname", "user.pic")
                    .withIgnoreNullValues();
            Example<Survey> example = Example.of(survey, matcher);

            Pageable pageable = ConversionUtility.requestGrid2Pageable(requestGrid);
            Page<Survey> page = surveyRepository.findAll(example, pageable);
            return new ResponseGrid<Survey>(requestGrid.getDraw(), page.getTotalElements(), page.getTotalElements(),
                    page.getContent());
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int findSurveysCountByUserId(Long userId) throws BusinessException {
        try {
            return surveyRepository.findSurveysCountByUser(userId);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int findSurveysActiveCountByUserId(Long userId) throws BusinessException {
        try {
            return surveyRepository.findSurveysActiveCountByUser(userId);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public List<Survey> findSurveysByStartDateOrEndDate(LocalDate startDate, LocalDate endDate) throws BusinessException {
        try {
            return surveyRepository.findSurveyByStartDateOrEndDate(startDate, endDate);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Survey findSurveyById(String id) throws BusinessException {
        try {
            return surveyRepository.findById(id).get();
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void createSurvey(Survey survey, MultipartFile file) throws BusinessException {
        try {
            if (file != null && !file.isEmpty()) {
                if (!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
                    throw new BusinessException("File format not valid: " + file.getContentType());
                }
                SurveyFile icon = ConversionUtility.multipartFile2DocumentFile(file);
                surveyFileService.create(icon);
                survey.setIcon(icon);
            }

            surveyRepository.save(survey);
        } catch (IOException | DataAccessException e) {
            throw new BusinessException(e);
        }

    }

    @Override
    public void updateSurvey(Survey survey, MultipartFile file) throws BusinessException {
        try {
            if (file != null && !file.isEmpty()) {
                if (!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
                    throw new BusinessException("File format not valid: " + file.getContentType());
                }
                SurveyFile icon = ConversionUtility.multipartFile2DocumentFile(file);
                if (survey.getIcon() != null) {
                    // Delete old icon
                    surveyFileService.delete(survey.getIcon());
                }
                // Set new icon
                surveyFileService.create(icon);
                survey.setIcon(icon);
            }

            surveyRepository.save(survey);
        } catch (IOException | DataAccessException e) {
            throw new BusinessException(e);
        }

    }

    @Override
    public void deleteSurvey(Survey survey) throws BusinessException {
        if (!survey.isActive()) {
            if (survey.getQuestionGroups() != null && survey.getQuestionGroups().size() > 0) {
                Set<QuestionGroup> groups = survey.getQuestionGroups();
                questionGroupService.deleteQuestionGroupList(groups);
            }
            if (survey.getInvitationPool() != null) {
                invitationPoolService.deleteInvitationPool(survey.getInvitationPool());
            }
            surveyRepository.delete(survey);
        }
    }

}
