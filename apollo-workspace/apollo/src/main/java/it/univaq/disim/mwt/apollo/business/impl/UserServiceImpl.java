package it.univaq.disim.mwt.apollo.business.impl;

import java.io.IOException;
import java.util.Base64;

import it.univaq.disim.mwt.apollo.business.exceptions.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
		if(userExistsByUsername(user.getUsername())) {
			throw new DoubleEntryException("double username");
		}
		user.setPassword(encoder.encode(user.getPassword()));
		try {
			userRepository.save(user);
		}
		catch(DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void updateUser(User user, MultipartFile file) throws BusinessException {
		try {
			if(!user.getNewPassword().isEmpty()) {
				String passEncoded = encoder.encode(user.getNewPassword());
				user.setPassword(passEncoded);
			}
			if(file != null && !file.isEmpty()) {
				user.setPic(new String(Base64.getEncoder().encode(file.getBytes()),"UTF-8"));
			}
			userRepository.save(user);
		}
		catch(DataAccessException | IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteUser(User user, String password) throws BusinessException {
		if(!encoder.matches(password, user.getPassword())){
			log.info("true");
			throw new WrongPasswordException("wrong password");
		}
		try{
			userRepository.delete(user);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
	public boolean userExistsByUsername(String username) throws BusinessException {
		try {
			return userRepository.existsByUsername(username);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	public User findById(Long id) throws BusinessException {
		try{
			return userRepository.findById(id).get();
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

}
