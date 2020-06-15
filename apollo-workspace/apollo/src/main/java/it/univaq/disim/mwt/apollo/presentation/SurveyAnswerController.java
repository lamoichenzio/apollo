package it.univaq.disim.mwt.apollo.presentation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.apollo.business.SurveyAnswerService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.ConversionUtility;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/forms/survey")
@Slf4j
public class SurveyAnswerController {

	@Autowired
	private SurveyAnswerService surveyAnswerService;
	
	@Autowired
	private SurveyService surveyService;
	

	@GetMapping("/{id}/fill")
	public String createStart(@PathVariable("id") String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		
		if(!survey.isActive()) {
			return "common/common_pages/survey_not_active";
		}
		if(survey.isSecret()) { 
			//TODO gestire logica sondaggio privato
			return "common/common_pages/survey_private";
		}
		
		SurveyAnswer surveyAnswer = ConversionUtility.survey2SurveyAnswer(survey);
		
		model.addAttribute("surveyanswer", surveyAnswer);
		model.addAttribute("survey", survey);
		return "common/user_view/survey";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("surveyanswer") SurveyAnswer surveyAnswer, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			log.info(errors.toString());
		}
		surveyAnswerService.createSurveyAnswer(surveyAnswer);
		return "common/common_pages/survey_submitted";
	}
	
	@GetMapping("/{surveyid}/answer/{id}")
	public String createView(@PathVariable("id") String id, @PathVariable("surveyid") String surveyId, @RequestParam int group, Model model) throws BusinessException {
		SurveyAnswer surveyAnswer = surveyAnswerService.findSurveyAnswerById(id);

		log.info(surveyAnswer.toString());
		
		model.addAttribute("surveyanswer", surveyAnswer);
		model.addAttribute("readonly", true);
		model.addAttribute("groupIndex", group);
		
		return "common/user_view/survey";
	}
		 
	@PostMapping("/findbysurveypaginated")
	@ResponseBody
	public ResponseGrid<SurveyAnswer> findAllPaginated(@RequestBody RequestGrid requestGrid, @RequestParam String id) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		ResponseGrid<SurveyAnswer> response = surveyAnswerService.findAllSurveyAnswersPaginated(requestGrid, survey);

		return response;
	}
	
}
