package it.univaq.disim.mwt.apollo.presentation;

import javax.validation.Valid;

import it.univaq.disim.mwt.apollo.business.validators.FileValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import it.univaq.disim.mwt.apollo.business.RoleService;
import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.exceptions.DoubleEntryException;
import it.univaq.disim.mwt.apollo.business.validators.UserValidator;
import it.univaq.disim.mwt.apollo.domain.Role;
import it.univaq.disim.mwt.apollo.domain.User;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private UserValidator validator;

	@Autowired
	private FileValidator fileValidator;
	
	@Autowired
	private RoleService roleService;

	@GetMapping("/create")
	public String create(Model model) throws BusinessException {
		User user = new User();
		Role standard = roleService.getStandardRole();
		user.setRole(standard);
		model.addAttribute(user);
		return "auth/register";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("user") User user, Errors errors, Model model)
			throws BusinessException {
		validator.validate(user, errors);
		if (errors.hasErrors()) {
			return "auth/register";
		}
		try {
			service.createUser(user);
		} catch (DoubleEntryException e) {
			model.addAttribute("duplicate", true);
			return "auth/register";
		}
		model.addAttribute("userCreated", true);
		return "index";
	}
}
