package it.univaq.disim.mwt.apollo.business.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.ConversionUtility;
import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.exceptions.DoubleEntryException;
import it.univaq.disim.mwt.apollo.business.exceptions.WrongPasswordException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.jpa.UserRepository;
import it.univaq.disim.mwt.apollo.domain.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public User findByUsername(String username) throws BusinessException {
        try {
            return userRepository.findByUsername(username);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseGrid<User> findAllPaginated(RequestGrid requestGrid) throws BusinessException {
        try {
        	
            User user = new User();
            user.setUsername(requestGrid.getSearch().getValue());

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
            		.withMatcher("username", GenericPropertyMatchers.contains().ignoreCase())
                    .withIgnoreNullValues();
            Example<User> example = Example.of(user, matcher);

            Pageable pageable = ConversionUtility.requestGrid2Pageable(requestGrid);
            Page<User> page = userRepository.findAll(example, pageable);
            return new ResponseGrid<User>(requestGrid.getDraw(), page.getTotalElements(), page.getTotalElements(),
                    page.getContent());
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }


    @Override
    public void createUser(User user) throws BusinessException {
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void updateUser(User user, MultipartFile file) throws BusinessException {
        try {
            if (!user.getNewPassword().isEmpty()) {
                String passEncoded = encoder.encode(user.getNewPassword());
                user.setPassword(passEncoded);
            }
            if (file != null && !file.isEmpty()) {
                user.setPic(new String(Base64.getEncoder().encode(file.getBytes()), StandardCharsets.UTF_8));
            }
            userRepository.save(user);
        } catch (DataAccessException | IOException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void deleteUser(User user, String password) throws BusinessException {
        if (!encoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException("wrong password");
        }
        try {
            userRepository.delete(user);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void deleteUserByAdmin(User admin, User userToDelete, String password) throws BusinessException {
        if (!encoder.matches(password, admin.getPassword())) {
            throw new WrongPasswordException("wrong password");
        }
        try {
            userRepository.delete(userToDelete);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public boolean userExistsByUsername(String username) throws BusinessException {
        try {
            return userRepository.existsByUsername(username);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public User findById(Long id) throws BusinessException {
        try {
            return userRepository.findById(id).get();
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }


}
