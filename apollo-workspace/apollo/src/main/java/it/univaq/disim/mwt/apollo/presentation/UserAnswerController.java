package it.univaq.disim.mwt.apollo.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/useranswers")
public class UserAnswerController {

//	@Autowired
//	private UserAnswerService userAnswerService;
//	
//	@GetMapping("/create")
//	public String createStart(Model model) {
//		UserAnswer userAnswer = new UserAnswer();
//		model.addAttribute("useranswer", userAnswer);
//		return "useranswer/form";
//	}
//	
//	@PostMapping("/create")
//	public String create(@Valid @ModelAttribute("useranswer") UserAnswer userAnswer, Errors errors) throws BusinessException {
//		if(errors.hasErrors()) {
//			return "useranswer/form";
//		}
//		userAnswerService.createUserAnswer(userAnswer);
//		return "redirect:/common/form";
//	}
//	
//	@GetMapping("/update")
//	public String updateStart(@RequestParam String id, Model model) throws BusinessException {
//		UserAnswer userAnswer = userAnswerService.findUserAnswerById(id);
//		model.addAttribute("useranswer", userAnswer);
//		return "useranswer/form";
//	}
//	
//	@PostMapping("/update")
//	public String update(@Valid @ModelAttribute("useranswer") UserAnswer userAnswer, Errors errors) throws BusinessException {
//		if(errors.hasErrors()) {
//			return "useranswer/form";
//		}
//		userAnswerService.updateUserAnswer(userAnswer);
//		return "redirect:/common/form";
//	}
//	
//	@GetMapping("/delete")
//	public String delete(@RequestParam String id) throws BusinessException {
//		userAnswerService.deleteUserAnswerById(id);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/delete")
//	public String delete(@ModelAttribute("useranswer") UserAnswer userAnswer) throws BusinessException {
//		userAnswerService.deleteUserAnswer(userAnswer);
//		return "redirect:/common/form";
//	}
}
