package it.univaq.disim.mwt.apollo.business.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.RoleService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.RoleRepository;
import it.univaq.disim.mwt.apollo.domain.Role;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository repository;
	
	private static final String STANDARD = "STANDARD";
	private static final String ADMIN = "ADMIN";
	
	@Override
	public Role getStandardRole() throws BusinessException {
		return repository.findRoleByName(STANDARD);
	}

	@Override
	public Role getAdminRole() throws BusinessException {
		return repository.findRoleByName(ADMIN);
	}

}
