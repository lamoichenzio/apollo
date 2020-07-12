package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;

public interface EmailService {

    /**
     * Send the survey activation/deactivation email to the survey creator
     * @param survey the survey being activated/deactivated
     * @param activated true if the survey is being activated
     * @throws BusinessException
     */
    void sendActivationMail(Survey survey, boolean activated) throws BusinessException;

    /**
     * Send the private survey invitation email to the invitation pool emails
     * @param survey the private survey being activated
     * @throws BusinessException
     */
    void sendSurveyInvitationMail(Survey survey) throws BusinessException;
}
