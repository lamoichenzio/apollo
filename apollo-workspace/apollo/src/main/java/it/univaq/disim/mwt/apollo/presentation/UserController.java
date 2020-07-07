package it.univaq.disim.mwt.apollo.presentation;

import javax.validation.Valid;

import it.univaq.disim.mwt.apollo.business.exceptions.WrongPasswordException;
import it.univaq.disim.mwt.apollo.business.validators.FileValidator;
import it.univaq.disim.mwt.apollo.presentation.model.GenericResponseBody;
import it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import it.univaq.disim.mwt.apollo.presentation.helpers.Utility;

import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private UserValidator userValidator;

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
		userValidator.validate(user, errors);
		if (errors.hasErrors()) {
			log.info(errors.toString());
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

	@GetMapping("/update")
	public String updateStart(Model model) throws BusinessException {
		User user = Utility.getUser();
		User newUser = service.findById(user.getId());
		model.addAttribute("user", newUser);
		return "/common/user/form";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("user") @Valid User user, Errors errors, Model model,
			@RequestParam("icon") MultipartFile file) throws BusinessException {

		User oldUser = Utility.getUser();
		user.setId(oldUser.getId());
		user.setPassword(oldUser.getPassword());
		user.setRole(oldUser.getRole());

		fileValidator.validate(file, errors);
		userValidator.validate(user, errors);

		if (errors.hasErrors()) {
			log.info(errors.toString());
			model.addAttribute("errors", errors);
			return "/common/user/form";
		}
		service.updateUser(user, file);
		model.addAttribute("ok","ok");
		return "/common/user/form";
	}

	@GetMapping("/delete")
	public String deleteStart() {
		return "/common/user/delete_modal :: modal";
	}

	@PostMapping("/delete")
	@ResponseBody
	public ResponseEntity<GenericResponseBody> delete(@RequestBody String password) throws BusinessException{
		User loggedUser = Utility.getUser();
		GenericResponseBody response = new GenericResponseBody();
		try{
			service.deleteUser(service.findById(loggedUser.getId()), password);
			response.setStatus(ResponseStatus.OK);
			return ResponseEntity.ok(response);
		} catch(WrongPasswordException e){
			response.setStatus(ResponseStatus.ERROR);
			response.setMsg("wrong password");
			return ResponseEntity
					.badRequest()
					.body(response);
		}
	}
}
