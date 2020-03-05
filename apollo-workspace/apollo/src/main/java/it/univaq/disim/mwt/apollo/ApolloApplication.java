package it.univaq.disim.mwt.apollo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.univaq.disim.mwt.apollo.business.impl.repositories.RoleRepository;
import it.univaq.disim.mwt.apollo.business.impl.repositories.UserRepository;
import it.univaq.disim.mwt.apollo.domain.Role;
import it.univaq.disim.mwt.apollo.domain.User;

@SpringBootApplication
public class ApolloApplication {

	/*
	 * @Autowired private PasswordEncoder passwordEncoder;
	 */
    
	public static void main(String[] args) {
		SpringApplication.run(ApolloApplication.class, args);
	}

	@Bean
    public CommandLineRunner loadData(UserRepository utenteRepository, RoleRepository roleRepository) {
        return (args) -> {
            Role adminRole = new Role();
            adminRole.setNome("ADMIN");
            adminRole.setDescrizione("amministratore");
            roleRepository.save(adminRole);
            
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");

            admin.setRole(adminRole);
            utenteRepository.save(admin);

        };
    }
}
