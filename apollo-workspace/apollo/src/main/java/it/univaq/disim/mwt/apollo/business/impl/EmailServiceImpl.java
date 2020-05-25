package it.univaq.disim.mwt.apollo.business.impl;

import it.univaq.disim.mwt.apollo.business.EmailService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String sendTo, String subject, String body) throws BusinessException {

    }
}
