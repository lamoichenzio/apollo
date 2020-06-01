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
import org.springframework.web.bind.annotation.RequestMapping;

import it.univaq.disim.mwt.apollo.business.SurveyAnswerService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/answers")
@Slf4j
public class SurveyAnswerController {

	@Autowired
	private SurveyAnswerService surveyAnswerService;
	
	@Autowired
	private SurveyService surveyService;

	@GetMapping("/{id}")
	public String createStart(@PathVariable("id") String id, Model model) throws BusinessException {
		Survey survey = surveyService.findSurveyById(id);
		if(!survey.isActive()) {
			return "common/common_pages/survey_not_active";
		}
		if(survey.isSecret()) {
			//TODO gestire logica sondaggio privato
			return "common/common_pages/survey_private";
		}
		SurveyAnswer surveyAnswer = surveyAnswerService.mapSurveyToSurveyAnswer(survey);
		model.addAttribute("surveyanswer", surveyAnswer);
		model.addAttribute("survey", survey);
		return "common/user_view/survey";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("surveyanswer") SurveyAnswer surveyAnswer, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			log.info(errors.toString());
//			return "useranswer/form";
		}
		//surveyAnswerService.createUserAnswer(userAnswer);
		log.info(surveyAnswer.toString());
		return "common/common_pages/survey_submitted";
	}
//	
//	@GetMapping("/update")
//	public String updateStart(@RequestParam String id, Model model) throws BusinessException {
//		SurveyAnswer userAnswer = surveyAnswerService.findUserAnswerById(id);
//		model.addAttribute("useranswer", userAnswer);
//		return "useranswer/form";
//	}
//	
//	@PostMapping("/update")
//	public String update(@Valid @ModelAttribute("useranswer") SurveyAnswer userAnswer, Errors errors) throws BusinessException {
//		if(errors.hasErrors()) {
//			return "useranswer/form";
//		}
//		surveyAnswerService.updateUserAnswer(userAnswer);
//		return "redirect:/common/form";
//	}
//	
//	@GetMapping("/delete")
//	public String delete(@RequestParam String id) throws BusinessException {
//		surveyAnswerService.deleteUserAnswerById(id);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/delete")
//	public String delete(@ModelAttribute("useranswer") SurveyAnswer userAnswer) throws BusinessException {
//		surveyAnswerService.deleteUserAnswer(userAnswer);
//		return "redirect:/common/form";
//	}
}
