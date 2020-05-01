package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.exceptions.DoubleEntryException;
import it.univaq.disim.mwt.apollo.domain.User;

public interface UserService {
	
	User findByUsername(String username) throws BusinessException;
	void createUser(User user) throws BusinessException;
	
}
