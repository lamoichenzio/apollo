package it.univaq.disim.mwt.apollo.business;

import it.univaq.disim.mwt.apollo.domain.User;

public interface UserService {
	
	User findUserByUsername(String username);
	
	void updateProfile(User user);
}
