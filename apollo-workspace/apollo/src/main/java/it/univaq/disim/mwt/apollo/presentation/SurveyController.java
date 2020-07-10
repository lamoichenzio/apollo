package it.univaq.disim.mwt.apollo.presentation;

import it.univaq.disim.mwt.apollo.business.*;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.validators.FileValidator;
import it.univaq.disim.mwt.apollo.domain.InvitationPool;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;
import it.univaq.disim.mwt.apollo.presentation.helpers.SurveyHelper;
import it.univaq.disim.mwt.apollo.presentation.helpers.Utility;
import it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus;
import it.univaq.disim.mwt.apollo.presentation.model.SurveyResponseBody;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/surveys")
@Slf4j
public class SurveyController {

	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private InvitationPoolService invitationPoolService;
	
	@Autowired
	private SurveyAnswerService surveyAnswerService;
	
	@Autowired
	private FileValidator fileValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	@GetMapping("/dashboard")
	public String dashboard() {
		return "/common/dashboard";
	}

	@PostMapping("/findallpaginated")
	@ResponseBody
	public ResponseGrid<Survey> findAllPaginated(@RequestBody RequestGrid requestGrid) throws BusinessException {
		User user = Utility.getUser();
		return surveyService.findAllSurveysByUserPaginated(requestGrid, user);
	}
	
	@GetMapping("/detail/{id}")
	public String detailStart(@PathVariable("id") String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		User currentUser = Utility.getUser();
		if(!survey.getUser().getId().equals(currentUser.getId())){
			return "redirect:/unauthorized";
		}
		model.addAttribute("survey", survey);
		return "/common/surveys/detail";
	}

	@GetMapping("/overview/{id}")
	public String overviewStart(@PathVariable("id") String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		User currentUser = Utility.getUser();
		if(!survey.getUser().getId().equals(currentUser.getId())){
			return "redirect:/unauthorized";
		}
		model.addAttribute("survey", survey);
		model.addAttribute("groups", survey.getQuestionGroups());
		model.addAttribute("answers", surveyAnswerService.findAllBySurvey(survey));
		return "/common/surveys/overview";
	}

	@GetMapping("/publish")
	public String publishStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		User currentUser = Utility.getUser();
		if(!survey.getUser().getId().equals(currentUser.getId())){
			return "redirect:/unauthorized";
		}
		model.addAttribute("survey", survey);
		return "/common/surveys/modals/publish_survey_modal :: surveyPublish";
	}

	@PostMapping("/publish")
	@ResponseBody
	public ResponseEntity<SurveyResponseBody> publish(@Valid @RequestBody Survey request, Errors errors)
			throws BusinessException {

		SurveyResponseBody response = new SurveyResponseBody();
		
		// If error, just return a 400 bad request, along with the error message
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(SurveyHelper.addErrorResult(errors));
		}

		Survey survey = surveyService.findSurveyById(request.getId());
		
		if (survey.isActive()) {
			survey.setActive(false);
			survey.removeSurveyUrl();
			response.setMsg("inactive");
		} else {
			survey.createSurveyUrl(survey.getId());

			if (survey.isSecret()) {
				if (survey.getInvitationPool() != null && survey.getInvitationPool().getEmails().size() > 0) {
					emailService.sendSurveyInvitationMail(survey);
				} else {
					response.setMsg("No email address found.");
					response.setStatus(ResponseStatus.ERROR);
					return ResponseEntity.badRequest().body(response);
				}
			}
			
			survey.setActive(true);
			response.setMsg("active");
		}
		
		surveyService.updateSurvey(survey, null);
		response.setStatus(ResponseStatus.OK);
		response.setResult(survey);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/invitationpool")
	public String invitationPoolStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		User currentUser = Utility.getUser();
		if(!survey.getUser().getId().equals(currentUser.getId())){
			return "redirect:/unauthorized";
		}
		model.addAttribute("survey", survey);
		return "/common/surveys/modals/invitation_survey_modal :: surveyInvitationPool";
	}
	
	@PostMapping("/invitationpool")
	@ResponseBody
	public ResponseEntity<SurveyResponseBody> invitationPool(@RequestBody String request, @RequestParam String id) throws BusinessException {
		SurveyResponseBody response = new SurveyResponseBody();

		Survey survey = surveyService.findSurveyById(id);

		if (survey.isActive()) {
			response.setStatus(ResponseStatus.ERROR);
			response.setMsg("Survey already active");
			return ResponseEntity.badRequest().body(response);
		} else {
			// Create Invitation Pool
			InvitationPool invitationPool = SurveyHelper.buildInvitationPool(request, invitationPoolService.findInvitationPoolBySurvey(survey), survey);
			invitationPoolService.updateInvitationPool(invitationPool);

			// update survey
			survey.setInvitationPool(invitationPool);
			surveyService.updateSurvey(survey, null);
			response.setMsg("Invitation Pool created.");
		}

		response.setStatus(ResponseStatus.OK);
		response.setResult(survey);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/create")
	public String createStart(Model model) {
		Survey survey = new Survey();
		log.info("[SurveyController]::Survey Creation");
		model.addAttribute("survey", survey);
		return "/common/surveys/modals/new_survey_modal :: surveyForm";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("survey") Survey survey, Errors errors,
			@RequestParam("iconfile") MultipartFile iconfile) throws BusinessException {
		fileValidator.validate(iconfile, errors);
		if (errors.hasErrors()) {
			return "redirect:/surveys/dashboard?error=true";
		}
		// Get logged user
		User user = Utility.getUser();
		survey.setUser(user);
		surveyService.createSurvey(survey, iconfile);

		return "redirect:/surveys/detail/" + survey.getId();
	}

	@GetMapping("/update")
	public String updateStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		User currentUser = Utility.getUser();
		if(!survey.getUser().getId().equals(currentUser.getId())){
			return "redirect:/unauthorized";
		}
		model.addAttribute("survey", survey);
		return "/common/surveys/modals/new_survey_modal :: surveyForm";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("survey") Survey survey, Errors errors,
			@RequestParam("iconfile") MultipartFile iconfile) throws BusinessException {
		fileValidator.validate(iconfile, errors);
		if (errors.hasErrors()) {
			return "redirect:/surveys/detail/"+survey.getId()+"?error=true";
		}
		surveyService.updateSurvey(survey, iconfile);
		return "redirect:/surveys/detail/" + survey.getId();
	}

	@GetMapping("/delete")
	public String delete(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		User currentUser = Utility.getUser();
		if(!survey.getUser().getId().equals(currentUser.getId())){
			return "redirect:/unauthorized";
		}
		model.addAttribute("survey_id", id);
		return "/common/surveys/modals/delete_survey_modal :: surveyDelete";

	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute("survey_id") String id) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		surveyService.deleteSurvey(survey);

		return "redirect:/surveys/dashboard";
	}
	
	@ModelAttribute
	public void setUser(Model model) throws BusinessException {
		User user = userService.findById(Utility.getUser().getId());
		model.addAttribute("user",user);
	}

}
