package it.univaq.disim.mwt.apollo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.univaq.disim.mwt.apollo.business.impl.repositories.RoleRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.SurveyRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.UserRepository;
import it.univaq.disim.mwt.apollo.domain.Gender;
import it.univaq.disim.mwt.apollo.domain.Role;
import it.univaq.disim.mwt.apollo.domain.StandardUser;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;

@SpringBootApplication
public class ApolloApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApolloApplication.class, args);
	}

	@Bean
    public CommandLineRunner loadData(UserRepository utenteRepository, RoleRepository roleRepository, 
    		SurveyRepository surveyRepository, PasswordEncoder encoder) {
        return (args) -> {
            Role adminRole = new Role();
            adminRole.setNome("ADMIN");
            adminRole.setDescrizione("amministratore");
            roleRepository.save(adminRole);
            
            User admin = new User();
            admin.setUsername("admin");
            String password = encoder.encode("admin");
            admin.setPassword(password);
            admin.setRole(adminRole);
            utenteRepository.save(admin);
            
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
            
            Survey survey = new Survey();
            survey.setName("Test");
            survey.setDescription("Test");
            survey.setOwner(standardUser);
            Date now = new Date();
            survey.setStartDate(now);
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DATE, 7);
            survey.setEndDate(cal.getTime());
            surveyRepository.save(survey);

        };
    }
}
