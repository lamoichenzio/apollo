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
import org.springframework.web.bind.annotation.RequestParam;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.SelectQuestion;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/create/choicequestion")
	public String createChoiceStart(Model model) {
		ChoiceQuestion question = new ChoiceQuestion();
		model.addAttribute("choicequestion", question);
		return "/choicequestion/form";
	}
	
	@GetMapping("/create/inputquestion")
	public String createInputStart(Model model) {
		InputQuestion question = new InputQuestion();
		model.addAttribute("inputquestion", question);
		return "/inputquestion/form";
	}
	
	@GetMapping("/create/matrixquestion")
	public String createMatrixStart(Model model) {
		MatrixQuestion question = new MatrixQuestion();
		model.addAttribute("matrixquestion", question);
		return "/matrixquestion/form";
	}
	
	@GetMapping("/create/selectquestion")
	public String createSelectStart(Model model) {
		SelectQuestion question = new SelectQuestion();
		model.addAttribute("selectquestion", question);
		return "/selectquestion/form";
	}
	
	@PostMapping("/create/choicequestion")
	public String create(@Valid @ModelAttribute("choicequestion") ChoiceQuestion question, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/choicequestion/form";
		}
		
		questionService.createQuestion(question);
		
		return "redirect:/common/form";
	}
	
	@PostMapping("/create/inputquestion")
	public String create(@Valid @ModelAttribute("inputquestion") InputQuestion question, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/inputquestion/form";
		}
		
		questionService.createQuestion(question);
		
		return "redirect:/common/form";
	}
	
	@PostMapping("/create/matrixquestion")
	public String create(@Valid @ModelAttribute("matrixquestion") MatrixQuestion question, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/matrixquestion/form";
		}
		
		questionService.createQuestion(question);
		
		return "redirect:/common/form";
	}
	
	@PostMapping("/create/selectquestion")
	public String create(@Valid @ModelAttribute("selectquestion") SelectQuestion question, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/selectquestion/form";
		}
		
		questionService.createQuestion(question);
		
		return "redirect:/common/form";
	}
	
	@GetMapping("/update/choicequestion")
	public String updateStartChoice(@RequestParam String id, Model model) throws BusinessException {
		ChoiceQuestion question = questionService.findChoiceQuestionById(id);
		model.addAttribute("choicequestion", question);
		return "/common/form";
	}
	
	@GetMapping("/update/inputquestion")
	public String updateStartInput(@RequestParam String id, Model model) throws BusinessException {
		InputQuestion question = questionService.findInputQuestionById(id);
		model.addAttribute("inputquestion", question);
		return "/common/form";
	}
	
	@GetMapping("/update/matrixquestion")
	public String updateStartMatrix(@RequestParam String id, Model model) throws BusinessException {
		MatrixQuestion question = questionService.findMatrixQuestionById(id);
		model.addAttribute("matrixquestion", question);
		return "/common/form";
	}
	
	@GetMapping("/update/selectquestion")
	public String updateStartSelect(@RequestParam String id, Model model) throws BusinessException {
		SelectQuestion question = questionService.findSelectQuestionById(id);
		model.addAttribute("selectquestion", question);
		return "/common/form";
	}
	
	@PostMapping("/update/choicequestion")
	public String update(@Valid @ModelAttribute("choicequestion") ChoiceQuestion question, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/choicequestion/form";
		}
		
		questionService.updateQuestion(question);

		return "redirect:/common/form";
	}
	
	@PostMapping("/update/inputquestion")
	public String update(@Valid @ModelAttribute("inputquestion") InputQuestion question, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/inputquestion/form";
		}
		
		questionService.updateQuestion(question);

		return "redirect:/common/form";
	}
	
	@PostMapping("/update/matrixquestion")
	public String update(@Valid @ModelAttribute("matrixquestion") MatrixQuestion question, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/matrixquestion/form";
		}
		
		questionService.updateQuestion(question);

		return "redirect:/common/form";
	}
	
	@PostMapping("/update/selectquestion")
	public String update(@Valid @ModelAttribute("selectquestion") SelectQuestion question, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/selectquestion/form";
		}
		
		questionService.updateQuestion(question);

		return "redirect:/common/form";
	}
	
	@GetMapping("/delete/choicequestion")
	public String deleteChoiceStart(@RequestParam String id, Model model) throws BusinessException {
		ChoiceQuestion question = questionService.findChoiceQuestionById(id);
		model.addAttribute("choicequestion", question);
		return "/common/form";
	}
	
	@GetMapping("/delete/inputquestion")
	public String deleteInputStart(@RequestParam String id, Model model) throws BusinessException {
		InputQuestion question = questionService.findInputQuestionById(id);
		model.addAttribute("inputquestion", question);
		return "/common/form";
	}
	
	@GetMapping("/delete/matrixquestion")
	public String deleteMatrixStart(@RequestParam String id, Model model) throws BusinessException {
		MatrixQuestion question = questionService.findMatrixQuestionById(id);
		model.addAttribute("matrixquestion", question);
		return "/common/form";
	}
	
	@GetMapping("/delete/selectquestion")
	public String deleteSelectStart(@RequestParam String id, Model model) throws BusinessException {
		SelectQuestion question = questionService.findSelectQuestionById(id);
		model.addAttribute("selectquestion", question);
		return "/common/form";
	}
	
	@PostMapping("/delete/choicequestion")
	public String delete(@ModelAttribute("choicequestion") ChoiceQuestion question) throws BusinessException {
		questionService.deleteQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/delete/inputquestion")
	public String delete(@ModelAttribute("inputquestion") InputQuestion question) throws BusinessException {
		questionService.deleteQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/delete/matrixquestion")
	public String delete(@ModelAttribute("matrixquestion") MatrixQuestion question) throws BusinessException {
		questionService.deleteQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/delete/selectquestion")
	public String delete(@ModelAttribute("selectquestion") SelectQuestion question) throws BusinessException {
		questionService.deleteQuestion(question);
		return "redirect:/common/form";
	}
	
}
