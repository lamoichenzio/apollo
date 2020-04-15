package it.univaq.disim.mwt.apollo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.RoleRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.UserRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyRepository;
import it.univaq.disim.mwt.apollo.domain.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.Gender;
import it.univaq.disim.mwt.apollo.domain.Role;
import it.univaq.disim.mwt.apollo.domain.StandardUser;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;
import it.univaq.disim.mwt.apollo.domain.types.ChoiceType;


@SpringBootApplication
public class ApolloApplication {
	
	@Autowired
	SurveyService surveyService;
	
	@Autowired
	QuestionService questionService;
	
	public static void main(String[] args) {
		SpringApplication.run(ApolloApplication.class, args);
	}

	@Bean
    public CommandLineRunner loadData(
    		UserRepository utenteRepository, 
    		RoleRepository roleRepository, 
    		SurveyRepository surveyRepository,
    		ChoiceQuestionRepository choiceQuestionRepository,
    		PasswordEncoder encoder) {
		
        return (args) -> {
        	// ROLE
            Role adminRole = new Role();
            adminRole.setNome("ADMIN");
            adminRole.setDescrizione("amministratore");
            roleRepository.save(adminRole);
            
            // USER
            User admin = new User();
            admin.setUsername("admin");
            String password = encoder.encode("admin");
            admin.setPassword(password);
            admin.setRole(adminRole);
            utenteRepository.save(admin);
            
            // STANDARD USER
            StandardUser standardUser = new StandardUser();
            standardUser.setUsername("pippo");
            standardUser.setPassword(encoder.encode("pippo"));
            standardUser.setFirstname("Pippo");
            standardUser.setLastname("Franco");
            standardUser.setEmail("prova@example.it");
            Date birthdate = new GregorianCalendar(1994, Calendar.NOVEMBER, 13).getTime();
            standardUser.setBirthdate(birthdate);
            standardUser.setGender(Gender.MALE);
            utenteRepository.save(standardUser);
            
            // SURVEY
            Survey survey = new Survey();
            survey.setName("Test");
            survey.setDescription("Test");
            survey.setUser(standardUser);
            Date now = new Date();
            survey.setStartDate(now);
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DATE, 7);
            survey.setEndDate(cal.getTime());
            surveyRepository.save(survey);
            
            // CHOICE QUESTION
            ChoiceQuestion question = new ChoiceQuestion();
            question.setTitle("Test question");
            question.setType(ChoiceType.RADIO);
            question.setOtherChoice(false);
            @SuppressWarnings("serial")
			ArrayList<String> options = new ArrayList<String>() { 
                { 
                    add("Option 1"); 
                    add("Option 2"); 
                }
            };
            question.setOptions(options);
            choiceQuestionRepository.save(question);
        };
    }
    
}
