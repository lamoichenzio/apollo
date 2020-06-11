package it.univaq.disim.mwt.apollo.business.impl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.exceptions.DoubleEntryException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.UserRepository;
import it.univaq.disim.mwt.apollo.domain.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = DoubleEntryException.class)
@Slf4j
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
	public void updateUser(User user, MultipartFile file) throws BusinessException {
		try {
			if(file != null && !file.isEmpty()) {
				user.setPic(new String(Base64.getEncoder().encode(file.getBytes()),"UTF-8"));
			}
			userRepository.save(user);
		}
		catch(DataIntegrityViolationException e) {
			throw new DoubleEntryException();
		}
		catch(DataAccessException e) {
			throw new BusinessException(e);
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

}
