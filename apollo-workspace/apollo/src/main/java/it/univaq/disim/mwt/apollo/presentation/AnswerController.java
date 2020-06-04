package it.univaq.disim.mwt.apollo.presentation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univaq.disim.mwt.apollo.business.AnswerService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.answers.MultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceType;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import it.univaq.disim.mwt.apollo.presentation.model.AnswerResponseBody;

@Controller
@RequestMapping("/answers")
public class AnswerController {

	@Autowired
	private AnswerService answerService;

	@GetMapping("/findanswers")
	public ResponseEntity<AnswerResponseBody> getAnswerData(@Valid @RequestBody Question question, Errors errors) throws BusinessException {
		
		AnswerResponseBody result = new AnswerResponseBody();
		System.out.println(question);
		// If error, just return a 400 bad request, along with the error message
		if (errors.hasErrors()) {
			result.setStatus(0);
			result.setMsg(
					errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));

			return ResponseEntity.badRequest().body(result);
		}
		
		if(question instanceof InputQuestion || question instanceof SelectionQuestion) {
			List<SingleAnswer> answers = answerService.findSingleAnswersByQuestion(question);
			result.setSingleAnswers(answers);
		}
		if(question instanceof ChoiceQuestion) {
			if(((ChoiceQuestion) question).getChoiceType().equals(ChoiceType.RADIO)) {
				List<SingleAnswer> answers = answerService.findSingleAnswersByQuestion(question);
				result.setSingleAnswers(answers);
			}
			if(((ChoiceQuestion) question).getChoiceType().equals(ChoiceType.CHECK)) {
				List<MultiAnswer> answers = answerService.findMultiAnswersByQuestion(question);
				result.setMultiAnswers(answers);
			}
		}
		if(question instanceof MatrixQuestion) {
			if(((MatrixQuestion) question).getType().equals(ChoiceType.RADIO)) {
				List<SingleChoiceMatrixAnswer> answers = answerService.findSingleChoiceMatrixAnswersByQuestion(question);
				result.setSingleChoiceMatrixAnswers(answers);
			}
			if(((MatrixQuestion) question).getType().equals(ChoiceType.CHECK)) {
				List<MultiChoiceMatrixAnswer> answers = answerService.findMultiChoiceMatrixAnswersByQuestion(question);
				result.setMultiChoiceMatrixAnswers(answers);
			}
		}
		
		result.setStatus(1);
		
		return ResponseEntity.ok(result);
	}
	
	/** CREATE **/
	
	@GetMapping("/multianswer/create")
	public String createMultiAnswerStart(Model model) {
		MultiAnswer answer = new MultiAnswer();
		model.addAttribute("multianswer", answer);
		return "/multianswer/form";
	}
	
	@GetMapping("/multichoicematrixanswer/create")
	public String createMultiChoiceMatrixAnswerStart(Model model) {
		MultiChoiceMatrixAnswer answer = new MultiChoiceMatrixAnswer();
		model.addAttribute("multichoicematrix", answer);
		return "/multichoicematrix/form";
	}
	
	@GetMapping("/singleanswer/create")
	public String createSingleAnswerStart(Model model) {
		SingleAnswer answer = new SingleAnswer();
		model.addAttribute("singleanswer", answer);
		return "/singleanswer/form";
	}
	
	@GetMapping("/singlechoicematrixanswer/create")
	public String createSingleChoiceMatrixAnswerStart(Model model) {
		SingleChoiceMatrixAnswer answer = new SingleChoiceMatrixAnswer();
		model.addAttribute("singlechoicematrixanswer", answer);
		return "/singlechoicematrixanswer/form";
	}
	
	@PostMapping("/multianswer/create")
	public String create(@Valid @ModelAttribute("multianswer") MultiAnswer answer, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/multianswer/form";
		}
		answerService.createAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/multichoicematrixanswer/create")
	public String create(@Valid @ModelAttribute("multichoicematrixanswer") MultiChoiceMatrixAnswer answer, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/multichoicematrixanswer/form";
		}
		answerService.createAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/singleanswer/create")
	public String create(@Valid @ModelAttribute("singleanswer") SingleAnswer answer, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/singleanswer/form";
		}
		
		answerService.createAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/singlechoicematrixanswer/create")
	public String create(@Valid @ModelAttribute("singlechoicematrixanswer") SingleChoiceMatrixAnswer answer, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/singlechoicematrixanswer/form";
		}
		answerService.createAnswer(answer);
		return "redirect:/common/form";
	}
	
	/** UPDATE **/
	
	@GetMapping("/multianswer/update")
	public String updateMultiAnswerStart(@RequestParam String id, Model model) throws BusinessException {
		MultiAnswer answer = answerService.findMultiAnswerById(id);
		model.addAttribute("multianswer", answer);
		return "/common/form";
	}
	
	@GetMapping("/multichoicematrixanswer/update")
	public String updateMultiChoiceMatrixAnswerStart(@RequestParam String id, Model model) throws BusinessException {
		MultiChoiceMatrixAnswer answer = answerService.findMultiChoiceMatrixAnswerById(id);
		model.addAttribute("multichoicematrixanswer", answer);
		return "/common/form";
	}
	
	@GetMapping("/singleanswer/update")
	public String updateSingleAnswerStart(@RequestParam String id, Model model) throws BusinessException {
		SingleAnswer answer = answerService.findSingleAnswerById(id);
		model.addAttribute("singleanswer", answer);
		return "/common/form";
	}
	
	@GetMapping("/singlechoicematrixanswer/update")
	public String updateSingleChoiceMatrixAnswerStart(@RequestParam String id, Model model) throws BusinessException {
		SingleChoiceMatrixAnswer answer = answerService.findSingleChoiceMatrixAnswerById(id);
		model.addAttribute("singlechoicematrixanswer", answer);
		return "/common/form";
	}
	
	@PostMapping("/multianswer/update")
	public String update(@Valid @ModelAttribute("multianswer") MultiAnswer answer, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/multianswer/form";
		}
		answerService.updateAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/multichoicematrixanswer/update")
	public String update(@Valid @ModelAttribute("multichoicematrixanswer") MultiChoiceMatrixAnswer answer, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/multichoicematrixanswer/form";
		}
		answerService.updateAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/singleanswer/update")
	public String update(@Valid @ModelAttribute("singleanswer") SingleAnswer answer, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/singleanswer/form";
		}
		answerService.updateAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/singlechoicematrixanswer/update")
	public String update(@Valid @ModelAttribute("selectanswer") SingleChoiceMatrixAnswer answer, Errors errors) throws BusinessException {
		if(errors.hasErrors()) {
			return "/singlechoicematrixanswer/form";
		}
		answerService.updateAnswer(answer);
		return "redirect:/common/form";
	}

	/** DELETE **/
	
	@GetMapping("/multianswer/delete")
	public String deleteMultiAnswerStart(@RequestParam String id, Model model) throws BusinessException {
		MultiAnswer answer = answerService.findMultiAnswerById(id);
		model.addAttribute("multianswer", answer);
		return "/common/form";
	}
	
	@GetMapping("/multichoicematrixanswer/delete")
	public String deleteMultiChoiceMatrixAnswerStart(@RequestParam String id, Model model) throws BusinessException {
		MultiChoiceMatrixAnswer answer = answerService.findMultiChoiceMatrixAnswerById(id);
		model.addAttribute("multichoicematrixanswer", answer);
		return "/common/form";
	}
	
	@GetMapping("/singleanswer/delete")
	public String deleteSingleAnswerStart(@RequestParam String id, Model model) throws BusinessException {
		SingleAnswer answer = answerService.findSingleAnswerById(id);
		model.addAttribute("singleanswer", answer);
		return "/common/form";
	}
	
	@GetMapping("/singlechoicematrixanswer/delete")
	public String deleteSingleChoiceMatrixAnswerStart(@RequestParam String id, Model model) throws BusinessException {
		SingleChoiceMatrixAnswer answer = answerService.findSingleChoiceMatrixAnswerById(id);
		model.addAttribute("singlechoicematrixanswer", answer);
		return "/common/form";
	}
	
	@PostMapping("/multianswer/delete")
	public String delete(@ModelAttribute("multianswer") MultiAnswer answer) throws BusinessException {
		answerService.deleteAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/multichoicematrixanswer/delete")
	public String delete(@ModelAttribute("multichoicematrixanswer") MultiChoiceMatrixAnswer answer) throws BusinessException {
		answerService.deleteAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/singleanswer/delete")
	public String delete(@ModelAttribute("singleanswer") SingleAnswer answer) throws BusinessException {
		answerService.deleteAnswer(answer);
		return "redirect:/common/form";
	}
	
	@PostMapping("/singlechoicematrixanswer/delete")
	public String delete(@ModelAttribute("singlechoicematrixanswer") SingleChoiceMatrixAnswer answer) throws BusinessException {
		answerService.deleteAnswer(answer);
		return "redirect:/common/form";
	}
}
