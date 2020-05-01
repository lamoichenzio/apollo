package it.univaq.disim.mwt.apollo.business.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.apollo.business.RoleService;
import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.exceptions.DuplicateEntryException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.UserRepository;
import it.univaq.disim.mwt.apollo.domain.Role;
import it.univaq.disim.mwt.apollo.domain.User;
import net.bytebuddy.agent.builder.AgentBuilder.PoolStrategy.Eager;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}

	@Override
	public void createUser(User user) throws BusinessException {
		Role standard = roleService.getStandardRole();
		user.setRole(standard);
		user.setPassword(encoder.encode(user.getPassword()));
		try {
			userRepository.save(user);
		}
		catch(DataIntegrityViolationException e) {
			throw new DuplicateEntryException(e);
		}
		catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

}
