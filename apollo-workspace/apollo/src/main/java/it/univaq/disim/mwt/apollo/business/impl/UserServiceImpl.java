package it.univaq.disim.mwt.apollo.business.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import it.univaq.disim.mwt.apollo.business.ConversionUtility;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.WrongPasswordException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.SurveyRepository;
import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public User findByUsername(String username) throws BusinessException {
        try {
            return userRepository.findByUsername(username);
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public ResponseGrid<User> findAllPaginated(RequestGrid requestGrid) throws BusinessException {
        try {
            User user = new User();

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withIgnoreNullValues();
            Example<User> example = Example.of(user, matcher);

            Pageable pageable = ConversionUtility.requestGrid2Pageable(requestGrid);
            Page<User> page = userRepository.findAll(example, pageable);
            page.getContent().forEach(item -> {
                log.info(item.toString());
            });

            return new ResponseGrid<User>(requestGrid.getDraw(), page.getTotalElements(), page.getTotalElements(),
                    page.getContent());
        } catch (DataAccessException e) {
            throw new BusinessException(e);
        }
    }


    @Override
    public void createUser(User user) throws BusinessException {
        if (userExistsByUsername(user.getUsername())) {
            throw new DoubleEntryException("double username");
        }
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
                user.setPic(new String(Base64.getEncoder().encode(file.getBytes()), "UTF-8"));
            }
            userRepository.save(user);
        } catch (DataAccessException | IOException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void deleteUser(User user, String password) throws BusinessException {
        if (!encoder.matches(password, user.getPassword())) {
            log.info("true");
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
            log.info("true");
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
