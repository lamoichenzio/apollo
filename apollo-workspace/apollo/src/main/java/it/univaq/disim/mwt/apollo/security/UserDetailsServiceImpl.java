package it.univaq.disim.mwt.apollo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.domain.User;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		User user;
		try {
			user = service.findByUsername(username);
		} catch (BusinessException e) {
			throw new UsernameNotFoundException("username not found");
		}
		if(user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return new UserDetailsImpl(user);
	}

}
