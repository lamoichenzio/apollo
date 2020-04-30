package it.univaq.disim.mwt.apollo.business.impl.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findRoleByName(String name) throws BusinessException;
}
