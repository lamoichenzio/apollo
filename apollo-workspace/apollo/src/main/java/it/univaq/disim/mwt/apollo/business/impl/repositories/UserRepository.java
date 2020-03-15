package it.univaq.disim.mwt.apollo.business.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
