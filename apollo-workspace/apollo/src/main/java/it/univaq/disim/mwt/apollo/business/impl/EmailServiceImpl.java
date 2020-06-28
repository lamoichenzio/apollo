package it.univaq.disim.mwt.apollo.business.impl;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;

import it.univaq.disim.mwt.apollo.domain.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.apollo.business.EmailService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Qualifier("templateEngine")
    @Autowired
    private TemplateEngine engine;

    @Override
    public void sendMail(String sendTo, String subject, String body) throws BusinessException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(sendTo);
        mail.setSubject(subject);
        mail.setText(body);
        mailSender.send(mail);
    }

	@Override
	public void sendInvitationMail(String[] addresses, String subject, String body) throws BusinessException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(subject);

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(addresses);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException ex) {
        	throw new BusinessException(ex);
        }
		
	}

    @Override
    public void sendSurveyInvitationMail(Survey survey) throws BusinessException{
        try{
            Context context = new Context(LocaleContextHolder.getLocale());
            context.setVariable("survey", survey);
            String body = engine.process("email/invitation.html", context);

            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject("Survey Invitation");

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(survey.getInvitationPool().getEmails().toArray(new String[0]));
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new BusinessException(e);
        }
    }


}
