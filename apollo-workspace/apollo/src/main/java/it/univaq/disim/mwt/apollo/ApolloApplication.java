package it.univaq.disim.mwt.apollo;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.RoleRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.UserRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.ChoiceQuestionRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyRepository;
import it.univaq.disim.mwt.apollo.domain.Role;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;


@SpringBootApplication
@EnableMongoAuditing
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
        	// ROLES
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("amministratore");
            roleRepository.save(adminRole);
            Role standardRole = new Role();
            standardRole.setName("STANDARD");
            standardRole.setDescription("standard user");
            roleRepository.save(standardRole);
            
            // USER
            User admin = new User();
            admin.setUsername("admin");
            admin.setFirstname("Admin");
            admin.setLastname("Admin");
            String password = encoder.encode("admin");
            admin.setPassword(password);
            admin.setPasswordConfirm(password);
            admin.setRole(adminRole);
            utenteRepository.save(admin);
            
            // STANDARD USER
            User standardUser = new User();
            standardUser.setUsername("pippo");
            standardUser.setPassword(encoder.encode("pippo"));
            standardUser.setPasswordConfirm(encoder.encode("pippo"));
            standardUser.setFirstname("Pippo");
            standardUser.setLastname("Franco");
            standardUser.setEmail("giordano.daloisio@gmail.com");
            standardUser.setRole(standardRole);
            utenteRepository.save(standardUser);
            
            // SURVEY
            Survey survey = new Survey();
            survey.setName("Test");
            survey.setDescription("Test");
            survey.setUser(standardUser);
            LocalDate now = LocalDate.now();
            survey.setStartDate(now);
            survey.setEndDate(now.plusDays(7));
            surveyRepository.save(survey);

            
            // CHOICE QUESTION
//            ChoiceQuestion question = new ChoiceQuestion();
//            question.setTitle("Test question");
//            question.setType(ChoiceType.RADIO);
//            question.setOtherChoice(false);
//			List<String> options = Arrays.asList("Option 1", "Option 2"); 
//            question.setOptions(options);
//            choiceQuestionRepository.save(question);
        };
    }
    
}
