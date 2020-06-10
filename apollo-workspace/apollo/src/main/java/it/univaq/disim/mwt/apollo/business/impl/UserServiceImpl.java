package it.univaq.disim.mwt.apollo.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.disim.mwt.apollo.business.RoleService;
import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.exceptions.DoubleEntryException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.UserRepository;
import it.univaq.disim.mwt.apollo.domain.Role;
import it.univaq.disim.mwt.apollo.domain.User;

@Service
@Transactional(rollbackFor = DoubleEntryException.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
		
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
	public User findByUsername(String username) throws BusinessException{
		try {			
			return userRepository.findByUsername(username);
		}catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void createUser(User user) throws BusinessException{
		user.setPassword(encoder.encode(user.getPassword()));
		try {
			userRepository.save(user);
		}
		catch(DataIntegrityViolationException e) {
			throw new DoubleEntryException();
		}
		catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void updateUser(User user) throws BusinessException {
		try {
			userRepository.save(user);
		}
		catch(DataIntegrityViolationException e) {
			throw new DoubleEntryException(e);
		}
		catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

}
