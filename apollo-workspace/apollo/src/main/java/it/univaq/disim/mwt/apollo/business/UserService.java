package it.univaq.disim.mwt.apollo.business;

import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.apollo.domain.User;

public interface UserService {
	
	User findByUsername(String username) throws BusinessException;
	
}
