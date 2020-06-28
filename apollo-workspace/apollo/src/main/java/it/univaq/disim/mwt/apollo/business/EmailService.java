package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;

public interface EmailService {

    void sendMail(String sendTo, String subject, String body) throws BusinessException;
    
    void sendInvitationMail(String[] addresses, String subject, String body) throws BusinessException;

    void sendSurveyInvitationMail(Survey survey) throws BusinessException;
}
