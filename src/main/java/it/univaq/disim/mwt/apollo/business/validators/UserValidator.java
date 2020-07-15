package it.univaq.disim.mwt.apollo.business.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.User;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserValidator implements Validator{
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserService service;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if(user.getUsername() == null || user.getUsername().isEmpty()) {
			errors.rejectValue("username", "NotBlank");
		}
		if(user.getPassword() == null || user.getPassword().isEmpty()) {
			errors.rejectValue("password", "NotBlank");
		}
		if(user.getRole() == null) {
			errors.reject("NotNull");
		}
		
		//Username Validation
		try {
			if(service.userExistsByUsername(user.getUsername())) {
				User otherUser = service.findByUsername(user.getUsername());
				if(!otherUser.getId().equals(user.getId())) {
					errors.rejectValue("username", "NotUnique");
				}
			}
		} catch (BusinessException e) {
			log.info(e.getMessage());
			errors.reject("genericError");
		}
		
		//Check password confirm on creation
		if(user.getId() == null && !user.getPassword().equals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "differentfields");
		}
		
		//Check password on update
		if(user.getOldPassword() != null && !user.getOldPassword().isEmpty()) {
			if(!encoder.matches(user.getOldPassword(), user.getPassword())) {
				errors.rejectValue("oldPassword", "NotEqual");
			}else {
				if(!user.getNewPassword().equals(user.getPasswordConfirm())) {
					errors.rejectValue("passwordConfirm", "differentfields");
				}
			}
		}
		
	}

}
