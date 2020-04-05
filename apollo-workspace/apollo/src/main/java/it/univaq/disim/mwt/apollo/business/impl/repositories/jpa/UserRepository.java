package it.univaq.disim.mwt.apollo.business.impl.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
