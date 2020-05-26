package it.univaq.disim.mwt.apollo.business.impl;

import it.univaq.disim.mwt.apollo.business.EmailService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String sendTo, String subject, String body) throws BusinessException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(sendTo);
        mail.setSubject(subject);
        mail.setText(body);
        mailSender.send(mail);
    }

}
