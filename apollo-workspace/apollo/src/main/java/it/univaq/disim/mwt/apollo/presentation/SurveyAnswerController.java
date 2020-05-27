package it.univaq.disim.mwt.apollo.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.univaq.disim.mwt.apollo.business.SurveyAnswerService;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.answers.SurveyAnswer;

@Controller
@RequestMapping("/answers")
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
		SurveyAnswer surveyAnswer = new SurveyAnswer();
		surveyAnswer.setSurvey(survey);
		model.addAttribute("surveyanswer", surveyAnswer);
		return "common/surveys/user_view/survey";
	}

//	@PostMapping("/create")
//	public String create(@Valid @ModelAttribute("useranswer") SurveyAnswer userAnswer, Errors errors) throws BusinessException {
//		if(errors.hasErrors()) {
//			return "useranswer/form";
//		}
//		surveyAnswerService.createUserAnswer(userAnswer);
//		return "redirect:/common/form";
//	}
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
