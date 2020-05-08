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
import it.univaq.disim.mwt.apollo.business.QuestionGroupService;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;

@Controller
@RequestMapping("/questiongroups")
public class QuestionGroupController {
	
	@Autowired
	private QuestionGroupService service;
	

	@GetMapping("/create")
	public String createStart(Model model) {
		QuestionGroup group = new QuestionGroup();
		model.addAttribute("group", group);
		return "/common/surveys/components/question_group/modals/new_group_modal :: questionGroupForm";
	}
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("group") QuestionGroup group, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "group/form";
		}
		// TO DO: insert survey reference
		service.createQuestionGroup(group);
		return "/common/surveys/components/question_group/question_group_container :: questionGroupForm";
	}
	
	@GetMapping("/update")
	public String updateStart(@RequestParam String id, Model model) throws BusinessException {
		QuestionGroup group = service.findQuestionGroupById(id);
		model.addAttribute("group", group);
		return "group/form";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("group") QuestionGroup group, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "group/form";
		}
		service.updateQuestionGroup(group);
		return "redirect:/common/form";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam String id) throws BusinessException {
		service.deleteQuestionGroupById(id);
		return "redirect:/common/form";
	}
}
