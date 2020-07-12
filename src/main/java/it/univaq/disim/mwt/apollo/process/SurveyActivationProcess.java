package it.univaq.disim.mwt.apollo.process;

import it.univaq.disim.mwt.apollo.business.EmailService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SurveyActivationProcess {

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "${cron.expression}", zone="Europe/Rome")
	@Retryable(value = Exception.class, backoff = @Backoff(delay = 5000))
	public void activateSurvey() throws BusinessException {
		LocalDate date = LocalDate.now();
		List<Survey> surveys = surveyService.findSurveysByStartDateOrEndDate(date, date);
		for (Survey survey : surveys) {

			// Survey activation
			if (survey.getStartDate() != null && survey.getStartDate().equals(date)) {
				if (!survey.isActive()) {
					survey.setActive(true);
					survey.createSurveyUrl(survey.getId());
					surveyService.updateSurvey(survey, null);
					emailService.sendActivationMail(survey, true);
					if(survey.isSecret() && survey.getInvitationPool() != null
							&& !survey.getInvitationPool().getEmails().isEmpty()){
						emailService.sendSurveyInvitationMail(survey);
					}
				}
			}

			// Survey deactivation
			if (survey.getEndDate() != null && survey.getEndDate().equals(date)) {
				if (survey.isActive()) {
					survey.setActive(false);
					survey.removeSurveyUrl();
					surveyService.updateSurvey(survey, null);
					emailService.sendActivationMail(survey, false);
				}
			}
		}
	}

}
