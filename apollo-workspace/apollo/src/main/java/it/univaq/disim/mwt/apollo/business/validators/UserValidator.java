package it.univaq.disim.mwt.apollo.business.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.univaq.disim.mwt.apollo.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if(user.getUsername() == null || user.getUsername() == "") {
			errors.rejectValue("username", "NotBlank");
		}
		if(user.getPassword() == null || user.getPassword() == "") {
			errors.rejectValue("password", "NotBlank");
		}
		if(user.getRole() == null) {
			errors.reject("NotNull");
		}
		if(user.getId() == null && !user.getPassword().equals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "differentfields");
		}
	}

}
