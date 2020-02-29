package it.univaq.disim.mwt.apollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.disim.mwt.apollo.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findUserByUsername(String username);

}
