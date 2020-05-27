package it.univaq.disim.mwt.apollo.presentation;

import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.validators.FileValidator;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.SurveyResponseBody;
import it.univaq.disim.mwt.apollo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/surveys")
@Slf4j
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private FileValidator validator;

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

	@GetMapping("/detail")
	public String detailStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		model.addAttribute("survey", survey);
		return "/common/surveys/detail";
	}

	@GetMapping("/overview")
	public String overviewStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		model.addAttribute("survey", survey);
		return "/common/surveys/overview";
	}

	@GetMapping("/publish")
	public String publishStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		model.addAttribute("survey", survey);
		return "/common/surveys/modals/publish_survey_modal :: surveyPublish";
	}

	@PostMapping("/publish")
	public ResponseEntity<SurveyResponseBody> publish(@Valid @RequestBody Survey survey, Errors errors)
			throws BusinessException {

		SurveyResponseBody result = new SurveyResponseBody();

		// If error, just return a 400 bad request, along with the error message
		if (errors.hasErrors()) {

			result.setMsg(
					errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));

			return ResponseEntity.badRequest().body(result);

		}

		if (survey.isActive()) {
			survey.setActive(false);
			survey.removeSurveyUrl();
			surveyService.updateSurvey(survey, null);
			result.setMsg("disabled");
		} else {
			survey.createSurveyUrl(survey.getId());
			survey.setActive(true);
			surveyService.updateSurvey(survey, null);
			result.setMsg("active");
		}

		result.setResult(survey);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/create")
	public String createStart(Model model) {
		Survey survey = new Survey();
		model.addAttribute("survey", survey);
		return "/common/surveys/modals/new_survey_modal :: surveyForm";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("survey") Survey survey,
			@RequestParam("iconfile") MultipartFile iconfile, Errors errors) throws BusinessException {
		validator.validate(iconfile, errors);
		if (errors.hasErrors()) {
			return "/common/surveys/modals/new_survey_modal :: surveyForm";
		}

		// Get logged user
		User user = Utility.getUser();

		survey.setUser(user);
		surveyService.createSurvey(survey, iconfile);

		return "redirect:/surveys/detail?id=" + survey.getId();
	}

	@GetMapping("/update")
	public String updateStart(@RequestParam String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		model.addAttribute("survey", survey);
		return "/common/surveys/modals/new_survey_modal :: surveyForm";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("survey") Survey survey,
			@RequestParam("iconfile") MultipartFile iconfile, Errors errors) throws BusinessException {
		validator.validate(iconfile, errors);
		if (errors.hasErrors()) {
			return "redirect:/surveys/detail?id=" + survey.getId() + "&erro=true";
		}

		if (iconfile == null) {
			surveyService.updateSurvey(survey, null);
		} else {
			surveyService.updateSurvey(survey, iconfile);
		}

		return "redirect:/surveys/detail?id=" + survey.getId();
	}

	@GetMapping("/delete")
	public String delete(@RequestParam String id, Model model) throws BusinessException {
		model.addAttribute("survey_id", id);
		return "/common/surveys/modals/delete_survey_modal :: surveyDelete";

	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute("survey_id") String id) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		surveyService.deleteSurvey(survey);

		return "redirect:/surveys/dashboard";
	}

}
