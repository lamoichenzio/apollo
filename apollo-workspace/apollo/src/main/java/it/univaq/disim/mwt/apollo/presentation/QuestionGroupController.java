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

import it.univaq.disim.mwt.apollo.business.QuestionGroupService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/questiongroups")
@Slf4j
public class QuestionGroupController {

	@Autowired
	private QuestionGroupService service;
	
	@Autowired
	private SurveyService surveyService;

	@GetMapping("/create")
	public String createStart(@RequestParam String survey_id, Model model) throws BusinessException {
		QuestionGroup group = new QuestionGroup();
		group.setSurvey(surveyService.findSurveyById(survey_id));
		
		model.addAttribute("group", group);

		return "/common/surveys/components/question_group/modals/new_group_modal :: questionGroupForm";
	}
	

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("group") QuestionGroup group, Errors errors) throws BusinessException {	
		if (errors.hasErrors()) {
			log.info(errors.toString());
			return "/common/surveys/components/question_group/modals/new_group_modal :: questionGroupForm";
		}
		
		Survey survey = group.getSurvey();
		
		// Create group
		service.createQuestionGroup(group);
		
		// Update survey
		survey.addQuestionGroup(group);
		surveyService.updateSurvey(survey, null);
		
		return "redirect:/surveys/detail?id="+survey.getId();
	}
	

	@GetMapping("/update")
	public String updateStart(@RequestParam String id, Model model) throws BusinessException {
		QuestionGroup group = service.findQuestionGroupById(id);
		model.addAttribute("group", group);
		return "/common/surveys/components/question_group/modals/new_group_modal :: questionGroupForm";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("group") QuestionGroup group, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			log.info(errors.toString());
			return "/common/surveys/components/question_group/modals/new_group_modal :: questionGroupForm";
		}
		service.updateQuestionGroup(group);
		return "redirect:/surveys/detail?id="+group.getSurvey().getId();
	}

	@GetMapping("/delete")
	public String deleteStart(@RequestParam String id, Model model) throws BusinessException {
		model.addAttribute("group_id", id);
		return "/common/surveys/components/question_group/modals/delete_group_modal :: questionGroupDelete";
	}
	
	@PostMapping("/delete")
	public String delete(@ModelAttribute("group_id") String id, Errors errors) throws BusinessException {
		QuestionGroup group = service.findQuestionGroupById(id);
		Survey survey = group.getSurvey();

		// Update survey
		survey.removeQuestionGroup(group);
		surveyService.updateSurvey(survey, null);

		// Delete group
		service.deleteQuestionGroup(group);
		
		return "redirect:/surveys/detail?id="+survey.getId();
	}
}
