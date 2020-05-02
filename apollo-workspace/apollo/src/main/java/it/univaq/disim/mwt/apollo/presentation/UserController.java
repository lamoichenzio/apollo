package it.univaq.disim.mwt.apollo.presentation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.exceptions.DoubleEntryException;
import it.univaq.disim.mwt.apollo.domain.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/create")
	public String create(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "auth/register";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("user") User user, Errors errors, Model model)
			throws BusinessException {
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
