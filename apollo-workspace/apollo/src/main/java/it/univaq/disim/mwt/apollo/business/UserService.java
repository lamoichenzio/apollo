package it.univaq.disim.mwt.apollo.business;

import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.User;

public interface UserService {

	User findById(Long id) throws BusinessException;
	User findByUsername(String username) throws BusinessException;

	void createUser(User user) throws BusinessException;
	void updateUser(User user, MultipartFile file) throws BusinessException;
	void deleteUser(User user, String password) throws BusinessException;
	boolean userExistsByUsername(String username) throws BusinessException; 
}
