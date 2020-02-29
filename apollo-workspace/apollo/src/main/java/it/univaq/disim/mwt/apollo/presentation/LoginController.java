package it.univaq.disim.mwt.apollo.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	@PostMapping("login")
	public String login() {
		return "redirect:/common/dashboard";
	}
	
	@GetMapping("logout")
	public String logout() {
		return "redirect:/";
	}
}
