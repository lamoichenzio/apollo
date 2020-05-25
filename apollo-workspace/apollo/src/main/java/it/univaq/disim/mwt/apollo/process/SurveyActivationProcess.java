package it.univaq.disim.mwt.apollo.process;

import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SurveyActivationProcess {

    @Autowired
    private SurveyService service;

    //@Scheduled(cron = "${cron.expression}", zone="Europe/Rome")
    @Scheduled(fixedRate = 5000)
    @Retryable(
        value = Exception.class,
            backoff = @Backoff(delay = 5000)
    )
    public void activateSurvey() throws BusinessException {
        Date date = new Date();
        List<Survey> surveys = service.findSurveysByStartDateOrEndDate(date, date);
        for(Survey survey : surveys){
            if(survey.getStartDate().equals(date)){
                survey.setActive(true);
                survey.createSurveyUrl(survey.getId());
                service.updateSurvey(survey, null);
            }
            if(survey.getEndDate().equals(date)){
                survey.setActive(false);
                survey.removeSurveyUrl();
                service.updateSurvey(survey, null);
            }
        }
        log.info("done");
    }

}
