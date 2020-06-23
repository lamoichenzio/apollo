package it.univaq.disim.mwt.apollo.business.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.apollo.business.EmailService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

	@Override
	public void sendHTMLMail(String[] addresses, String subject, String body) throws BusinessException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(subject);
            
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(addresses);
            helper.setText(body, true);
            
            mailSender.send(message);
        } catch (MessagingException ex) {
        	log.info(ex.getMessage());
        }
		
	}

}
