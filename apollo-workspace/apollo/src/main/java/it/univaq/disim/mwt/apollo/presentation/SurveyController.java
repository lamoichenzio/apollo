package it.univaq.disim.mwt.apollo.presentation;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.business.QuestionGroupService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;

@Controller
@RequestMapping("/surveys")
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	@GetMapping("/dashboard")
	public String dashboard() {
		return "/common/dashboard";
	}
	
	@PostMapping("/findallpaginated")
	@ResponseBody
	public ResponseGrid<Survey> findAllPaginated(@RequestBody RequestGrid requestGrid) 
			throws BusinessException{
		User user = Utility.getUser();
		return surveyService.findAllSurveysByUserPaginated(requestGrid, user);
	}
	
	@GetMapping("/detail")
	public String detailStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		model.addAttribute("survey", survey);
		return "/common/surveys/detail";
	}
	
	@GetMapping("/create")
	public String createStart(Model model) {
		Survey survey = new Survey();
		model.addAttribute("survey", survey);
		return "/common/surveys/modals/new_survey_modal :: surveyForm";
	}	
	
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("survey") Survey survey, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/common/surveys/modals/new_survey_modal :: surveyForm";
		}
		
		// Get logged user
		User user = Utility.getUser();
		
		survey.setUser(user);
		survey.setCreationDate(new Date());
		surveyService.createSurvey(survey);
		
		return "redirect:/surveys/detail?id="+survey.getId();
	}
	
	@GetMapping("/update")
	public String updateStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		model.addAttribute("survey", survey);
		return "/common/form";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("survey") Survey survey, Errors errors) throws BusinessException {
		System.out.println(survey.toString());
		if(errors.hasErrors()) {
			return "redirect:/surveys/detail?id="+survey.getId() + "&erro=true";
		}
		surveyService.updateSurvey(survey);

		return "redirect:/surveys/detail?id="+survey.getId();
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);

		model.addAttribute("survey", survey);
		return "/common/surveys/modals/delete_survey_modal :: surveyDelete";

	}
	
	@PostMapping("/delete")
	public String delete(@ModelAttribute("survey") Survey survey) throws BusinessException {
		System.out.println(survey.getQuestionGroups().toString());

//		surveyService.deleteSurvey(survey);
		return "redirect:/surveys/dashboard";
	}

}
