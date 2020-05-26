package it.univaq.disim.mwt.apollo.process;

import it.univaq.disim.mwt.apollo.business.EmailService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SurveyActivationProcess {

	@Autowired
	private SurveyService service;

	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "${cron.expression}", zone="Europe/Rome")
	@Retryable(value = Exception.class, backoff = @Backoff(delay = 5000))
	public void activateSurvey() throws BusinessException {
		LocalDate date = LocalDate.now();
		List<Survey> surveys = service.findSurveysByStartDateOrEndDate(date, date);
		for (Survey survey : surveys) {
			if (survey.getStartDate() != null && survey.getStartDate().equals(date)) {
				if (!survey.isActive()) {
					survey.setActive(true);
					survey.createSurveyUrl(survey.getId());
					service.updateSurvey(survey, null);
					emailService.sendMail(survey.getUser().getEmail(), "Pubblicazione sondaggio",
							"Sondaggio pubblicato");
				}
			}
			if (survey.getEndDate() != null && survey.getEndDate().equals(date)) {
				if (survey.isActive()) {
					survey.setActive(false);
					survey.removeSurveyUrl();
					service.updateSurvey(survey, null);
					emailService.sendMail(survey.getUser().getEmail(), "Pubblicazione sondaggio",
							"Sondaggio disattivato");
				}
			}
		}
		log.info("done");
	}

}
