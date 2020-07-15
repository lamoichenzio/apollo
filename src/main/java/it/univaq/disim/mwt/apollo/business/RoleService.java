package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Role;

public interface RoleService {
	Role getStandardRole() throws BusinessException;
	Role getAdminRole() throws BusinessException;
}
