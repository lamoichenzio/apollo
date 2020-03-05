package it.univaq.disim.mwt.apollo.business.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
