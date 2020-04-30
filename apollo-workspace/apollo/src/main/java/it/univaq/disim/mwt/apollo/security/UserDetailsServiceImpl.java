package it.univaq.disim.mwt.apollo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.domain.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO cambiare username con email
		User user;
		try {
			user = service.findByUsername(username);
		} catch (BusinessException e) {
			throw new UsernameNotFoundException("utente non trovato");
		}

		if (user == null) {
			throw new UsernameNotFoundException("utente non trovato");
		}
		return new UserDetailsImpl(user);

	}

}
