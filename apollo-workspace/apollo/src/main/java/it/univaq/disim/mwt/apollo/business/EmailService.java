package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;

public interface EmailService {

    void sendMail(String sendTo, String subject, String body) throws BusinessException;
}
