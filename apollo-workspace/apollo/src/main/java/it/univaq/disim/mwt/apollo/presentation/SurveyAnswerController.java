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

import it.univaq.disim.mwt.apollo.business.ConversionUtility;
import it.univaq.disim.mwt.apollo.business.InvitationPoolService;
import it.univaq.disim.mwt.apollo.business.SurveyAnswerService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.InvitationPool;
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
	
	@Autowired
	private InvitationPoolService invitationPoolService;
	

	@GetMapping("/{id}/fill")
	public String createStart(@PathVariable("id") String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		
		if(!survey.isActive()) {
			return "common/common_pages/survey_not_active";
		}
		
		SurveyAnswer surveyAnswer = ConversionUtility.survey2SurveyAnswer(survey);

		model.addAttribute("surveyanswer", surveyAnswer);
		model.addAttribute("survey", survey);
		
		if(survey.isSecret()) { 
			//TODO aprire la stessa pagina ma con la modale per le credenziali già attiva
			return "common/user_view/survey :: survey_private";
		}
		
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

	@GetMapping("/{id}/fill/identityverification")
	@ResponseBody
	public Boolean identityVerification(@PathVariable("id") String id, @RequestParam String password, @RequestParam String email, Errors errors) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		InvitationPool invitationPool = invitationPoolService.findInvitationPoolBySurvey(survey);
		
		if (invitationPool.getPassword().equals(password) && invitationPool.getEmails().stream().filter(mail -> mail.equals(email)).findAny().orElse(null) != null) {
			// Autenticato, chiusura della modale 
			return true;
		}
		// non autenticato, mostrare l'errore nella view
		return false;
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
