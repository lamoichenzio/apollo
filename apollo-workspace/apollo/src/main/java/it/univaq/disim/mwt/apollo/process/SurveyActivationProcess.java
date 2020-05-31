package it.univaq.disim.mwt.apollo.process;

import it.univaq.disim.mwt.apollo.business.EmailService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;
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
			User user = survey.getUser();
			if (survey.getStartDate() != null && survey.getStartDate().equals(date)) {
				if (!survey.isActive()) {
					survey.setActive(true);
					survey.createSurveyUrl(survey.getId());
					service.updateSurvey(survey, null);
					String body = "The survey" +
							survey.getName() +
							" for user " +
							user.getUsername() +
							" has been published";
					emailService.sendMail(user.getEmail(), "Pubblicazione sondaggio",
							body);
				}
			}
			if (survey.getEndDate() != null && survey.getEndDate().equals(date)) {
				if (survey.isActive()) {
					survey.setActive(false);
					survey.removeSurveyUrl();
					service.updateSurvey(survey, null);
					String body = "The survey" +
							survey.getName() +
							" for user " +
							user.getUsername() +
							" has been deactivated";
					emailService.sendMail(survey.getUser().getEmail(), "Pubblicazione sondaggio",
							body.toString());
				}
			}
		}
	}

}
