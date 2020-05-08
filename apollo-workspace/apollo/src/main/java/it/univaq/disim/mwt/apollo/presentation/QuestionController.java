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
	
	/** CREATE **/
	
	@GetMapping("/choicequestion/create")
	public String createChoiceStart(Model model) {
		ChoiceQuestion question = new ChoiceQuestion();
		model.addAttribute("choicequestion", question);
		return "/choicequestion/form";
	}
	
	@GetMapping("/inputquestion/create")
	public String createInputStart(Model model) {
		InputQuestion question = new InputQuestion();
		model.addAttribute("inputquestion", question);
		return "/inputquestion/form";
	}
	
	@GetMapping("/matrixquestion/create")
	public String createMatrixStart(Model model) {
		MatrixQuestion question = new MatrixQuestion();
		model.addAttribute("matrixquestion", question);
		return "/matrixquestion/form";
	}
	
	@GetMapping("/selectquestion/create")
	public String createSelectStart(Model model) {
		SelectQuestion question = new SelectQuestion();
		model.addAttribute("selectquestion", question);
		return "/selectquestion/form";
	}
	
	@PostMapping("/choicequestion/create")
	public String create(@Valid @ModelAttribute("choicequestion") ChoiceQuestion question, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/choicequestion/form";
		}
		
		questionService.createQuestion(question);
		
		return "redirect:/common/form";
	}
	
	@PostMapping("/inputquestion/create")
	public String create(@Valid @ModelAttribute("inputquestion") InputQuestion question, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/inputquestion/form";
		}
		
		questionService.createQuestion(question);
		
		return "redirect:/common/form";
	}
	
	@PostMapping("/matrixquestion/create")
	public String create(@Valid @ModelAttribute("matrixquestion") MatrixQuestion question, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/matrixquestion/form";
		}
		questionService.createQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/selectquestion/create")
	public String create(@Valid @ModelAttribute("selectquestion") SelectQuestion question, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/selectquestion/form";
		}
		questionService.createQuestion(question);
		return "redirect:/common/form";
	}
	
	/** UPDATE **/
	
	@GetMapping("/choicequestion/update")
	public String updateStartChoice(@RequestParam String id, Model model) throws BusinessException {
		ChoiceQuestion question = questionService.findChoiceQuestionById(id);
		model.addAttribute("choicequestion", question);
		return "/common/form";
	}
	
	@GetMapping("/inputquestion/update")
	public String updateStartInput(@RequestParam String id, Model model) throws BusinessException {
		InputQuestion question = questionService.findInputQuestionById(id);
		model.addAttribute("inputquestion", question);
		return "/common/form";
	}
	
	@GetMapping("/matrixquestion/update")
	public String updateStartMatrix(@RequestParam String id, Model model) throws BusinessException {
		MatrixQuestion question = questionService.findMatrixQuestionById(id);
		model.addAttribute("matrixquestion", question);
		return "/common/form";
	}
	
	@GetMapping("/selectquestion/update")
	public String updateStartSelect(@RequestParam String id, Model model) throws BusinessException {
		SelectQuestion question = questionService.findSelectQuestionById(id);
		model.addAttribute("selectquestion", question);
		return "/common/form";
	}
	
	@PostMapping("/choicequestion/update")
	public String update(@Valid @ModelAttribute("choicequestion") ChoiceQuestion question, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/choicequestion/form";
		}
		questionService.updateQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/inputquestion/update")
	public String update(@Valid @ModelAttribute("inputquestion") InputQuestion question, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/inputquestion/form";
		}
		questionService.updateQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/matrixquestion/update")
	public String update(@Valid @ModelAttribute("matrixquestion") MatrixQuestion question, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/matrixquestion/form";
		}
		questionService.updateQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/selectquestion/update")
	public String update(@Valid @ModelAttribute("selectquestion") SelectQuestion question, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/selectquestion/form";
		}
		questionService.updateQuestion(question);
		return "redirect:/common/form";
	}

	/** DELETE **/
	
	@GetMapping("/choicequestion/delete")
	public String deleteChoiceStart(@RequestParam String id, Model model) throws BusinessException {
		ChoiceQuestion question = questionService.findChoiceQuestionById(id);
		model.addAttribute("choicequestion", question);
		return "/common/form";
	}
	
	@GetMapping("/inputquestion/delete")
	public String deleteInputStart(@RequestParam String id, Model model) throws BusinessException {
		InputQuestion question = questionService.findInputQuestionById(id);
		model.addAttribute("inputquestion", question);
		return "/common/form";
	}
	
	@GetMapping("/matrixquestion/delete")
	public String deleteMatrixStart(@RequestParam String id, Model model) throws BusinessException {
		MatrixQuestion question = questionService.findMatrixQuestionById(id);
		model.addAttribute("matrixquestion", question);
		return "/common/form";
	}
	
	@GetMapping("/selectquestion/delete")
	public String deleteSelectStart(@RequestParam String id, Model model) throws BusinessException {
		SelectQuestion question = questionService.findSelectQuestionById(id);
		model.addAttribute("selectquestion", question);
		return "/common/form";
	}
	
	@PostMapping("/choicequestion/delete")
	public String delete(@ModelAttribute("choicequestion") ChoiceQuestion question) throws BusinessException {
		questionService.deleteQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/inputquestion/delete")
	public String delete(@ModelAttribute("inputquestion") InputQuestion question) throws BusinessException {
		questionService.deleteQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/matrixquestion/delete")
	public String delete(@ModelAttribute("matrixquestion") MatrixQuestion question) throws BusinessException {
		questionService.deleteQuestion(question);
		return "redirect:/common/form";
	}
	
	@PostMapping("/selectquestion/delete")
	public String delete(@ModelAttribute("selectquestion") SelectQuestion question) throws BusinessException {
		questionService.deleteQuestion(question);
		return "redirect:/common/form";
	}
	
}