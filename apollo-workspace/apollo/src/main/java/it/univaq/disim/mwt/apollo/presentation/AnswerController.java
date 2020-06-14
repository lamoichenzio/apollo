package it.univaq.disim.mwt.apollo.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.apollo.business.AnswerService;
import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceType;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.InputType;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import it.univaq.disim.mwt.apollo.presentation.model.AnswerBody;
import it.univaq.disim.mwt.apollo.presentation.model.AnswerResponseBody;
import it.univaq.disim.mwt.apollo.presentation.model.AnswerType;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionBody;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionRequestBody;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionType;
import it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus;

@Controller
@RequestMapping("/answers")
public class AnswerController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;

	// TO DO: Rework the method in another class
	@PostMapping("/findanswers")
	@ResponseBody
	public ResponseEntity<AnswerResponseBody> getAnswersData(@Valid @RequestBody QuestionRequestBody request) throws BusinessException {
		
		AnswerResponseBody response = new AnswerResponseBody();
		
		// Input
		if(request.getType().equals(QuestionType.INPUT)) {
			InputQuestion question = questionService.findInputQuestionById(request.getId());
			List<InputQuestionAnswer> answers = answerService.findInputQuestionAnswersByQuestion(question);
			
			// Set QuestionBody
			response.setQuestion(
					QuestionBody.builder()
					.id(question.getId())
					.type(QuestionType.INPUT)
					.build());
			
			// Set Answers
			for(InputQuestionAnswer answer : answers) {
				AnswerBody body = AnswerBody.builder()
						.answers(new ArrayList<String>(Arrays.asList(answer.getAnswer())))
						.build();
				response.addAnswerBody(body);
			}
			
			// TO DO: Rework
			if (question.getType().equals(InputType.TEXT) || question.getType().equals(InputType.TEXTAREA)) {
				response.setType(AnswerType.TEXT);
			} else if (question.getType().equals(AnswerType.NUMBER)) {
				response.setType(AnswerType.NUMBER);
			} else if (question.getType().equals(AnswerType.DATE)) {
				response.setType(AnswerType.DATE);
			}
		}
		
		// Choice
		if(request.getType().equals(QuestionType.CHOICE)) {
			ChoiceQuestion question = questionService.findChoiceQuestionById(request.getId());

			// Set QuestionBody
			response.setQuestion(QuestionBody.builder()
					.id(question.getId())
					.options(question.getOptions())
					.type(QuestionType.CHOICE)
					.otherChoice(question.isOtherChoice())
					.build());
			
			if(question.getChoiceType().equals(ChoiceType.RADIO)) {
				List<ChoiceQuestionSingleAnswer> answers = answerService.findChoiceQuestionSingleAnswersByQuestion(question);

				// Set Answers
				for(ChoiceQuestionSingleAnswer answer : answers) {
					AnswerBody body = AnswerBody.builder()
							.answers(new ArrayList<String>(Arrays.asList(answer.getAnswer())))
							.build();
					response.addAnswerBody(body);
				}
				response.setType(AnswerType.SINGLE);
			}
			
			if(question.getChoiceType().equals(ChoiceType.CHECK)) {
				List<ChoiceQuestionMultiAnswer> answers = answerService.findChoiceQuestionMultiAnswersByQuestion(question);

				// Set Answers
				for(ChoiceQuestionMultiAnswer answer : answers) {
					AnswerBody body = AnswerBody.builder()
							.answers(answer.getAnswers())
							.build();
					response.addAnswerBody(body);
				}
				response.setType(AnswerType.MULTIPLE);
			}
		}
		
		// Matrix
		if(request.getType().equals(QuestionType.MATRIX)) {
			MatrixQuestion question = questionService.findMatrixQuestionById(request.getId());
			
			// Set QuestionBody
			response.setQuestion(QuestionBody.builder()
					.id(question.getId())
					.options(question.getOptions())
					.optionValues(question.getOptionValues())
					.type(QuestionType.MATRIX)
					.build());
			
			if(question.getType().equals(ChoiceType.RADIO)) {
				List<SingleChoiceMatrixAnswer> answers = answerService.findSingleChoiceMatrixAnswersByQuestion(question);
				
				// Set Answers
				for(SingleChoiceMatrixAnswer answer : answers) {
					Map<String, List<String>> awr = new HashMap<>();
					
					for (String key : answer.getAnswers().keySet()) {
						awr.put(key, Arrays.asList(answer.getAnswers().get(key)));
					}
					
					AnswerBody body = AnswerBody.builder()
							.matrixAnswers(awr)
							.build();
					response.addAnswerBody(body);
				}
				response.setType(AnswerType.SINGLE);
			}
			
			if(question.getType().equals(ChoiceType.CHECK)) {
				List<MultiChoiceMatrixAnswer> answers = answerService.findMultiChoiceMatrixAnswersByQuestion(question);
				
				// Set Answers
				for(MultiChoiceMatrixAnswer answer : answers) {
					Map<String, List<String>> awr = new HashMap<>();
					
					for (String key : answer.getAnswers().keySet()) {
						awr.put(key, answer.getAnswers().get(key).getValues());
					}
					
					response.addAnswerBody(AnswerBody.builder().matrixAnswers(awr).build());
				}
				response.setType(AnswerType.MULTIPLE);
			}
		}
		
		// Selection
		if (request.getType().equals(QuestionType.SELECTION)) {
			SelectionQuestion question = questionService.findSelectionQuestionById(request.getId());
			List<SelectionQuestionAnswer> answers = answerService.findSelectionQuestionAnswersByQuestion(question);
			
			// Set Answers
			for(SelectionQuestionAnswer answer : answers) {
				AnswerBody body = AnswerBody.builder()
						.answers(new ArrayList<String>(Arrays.asList(answer.getAnswer())))
						.build();
				response.addAnswerBody(body);
			}
			response.setType(AnswerType.SINGLE);
		}
		
		response.setStatus(ResponseStatus.OK);
		
		return ResponseEntity.ok(response);
	}
	
	/** CREATE **/
	
//	@GetMapping("/multianswer/create")
//	public String createMultiAnswerStart(Model model) {
//		ChoiceQuestionMultiAnswer answer = new ChoiceQuestionMultiAnswer();
//		model.addAttribute("multianswer", answer);
//		return "/multianswer/form";
//	}
//	
//	@GetMapping("/multichoicematrixanswer/create")
//	public String createMultiChoiceMatrixAnswerStart(Model model) {
//		MultiChoiceMatrixAnswer answer = new MultiChoiceMatrixAnswer();
//		model.addAttribute("multichoicematrix", answer);
//		return "/multichoicematrix/form";
//	}
//	
//	@GetMapping("/singleanswer/create")
//	public String createSingleAnswerStart(Model model) {
//		InputQuestionAnswer answer = new InputQuestionAnswer();
//		model.addAttribute("singleanswer", answer);
//		return "/singleanswer/form";
//	}
//	
//	@GetMapping("/singlechoicematrixanswer/create")
//	public String createSingleChoiceMatrixAnswerStart(Model model) {
//		SingleChoiceMatrixAnswer answer = new SingleChoiceMatrixAnswer();
//		model.addAttribute("singlechoicematrixanswer", answer);
//		return "/singlechoicematrixanswer/form";
//	}
//	
//	@PostMapping("/multianswer/create")
//	public String create(@Valid @ModelAttribute("multianswer") ChoiceQuestionMultiAnswer answer, Errors errors) throws BusinessException {
//		if (errors.hasErrors()) {
//			return "/multianswer/form";
//		}
//		answerService.createAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/multichoicematrixanswer/create")
//	public String create(@Valid @ModelAttribute("multichoicematrixanswer") MultiChoiceMatrixAnswer answer, Errors errors) throws BusinessException {
//		if (errors.hasErrors()) {
//			return "/multichoicematrixanswer/form";
//		}
//		answerService.createAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/singleanswer/create")
//	public String create(@Valid @ModelAttribute("singleanswer") InputQuestionAnswer answer, Errors errors) throws BusinessException {
//		if (errors.hasErrors()) {
//			return "/singleanswer/form";
//		}
//		
//		answerService.createAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/singlechoicematrixanswer/create")
//	public String create(@Valid @ModelAttribute("singlechoicematrixanswer") SingleChoiceMatrixAnswer answer, Errors errors) throws BusinessException {
//		if (errors.hasErrors()) {
//			return "/singlechoicematrixanswer/form";
//		}
//		answerService.createAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	/** UPDATE **/
//	
//	@GetMapping("/multianswer/update")
//	public String updateMultiAnswerStart(@RequestParam String id, Model model) throws BusinessException {
//		ChoiceQuestionMultiAnswer answer = answerService.findMultiAnswerById(id);
//		model.addAttribute("multianswer", answer);
//		return "/common/form";
//	}
//	
//	@GetMapping("/multichoicematrixanswer/update")
//	public String updateMultiChoiceMatrixAnswerStart(@RequestParam String id, Model model) throws BusinessException {
//		MultiChoiceMatrixAnswer answer = answerService.findMultiChoiceMatrixAnswerById(id);
//		model.addAttribute("multichoicematrixanswer", answer);
//		return "/common/form";
//	}
//	
//	@GetMapping("/singleanswer/update")
//	public String updateSingleAnswerStart(@RequestParam String id, Model model) throws BusinessException {
//		InputQuestionAnswer answer = answerService.findSingleAnswerById(id);
//		model.addAttribute("singleanswer", answer);
//		return "/common/form";
//	}
//	
//	@GetMapping("/singlechoicematrixanswer/update")
//	public String updateSingleChoiceMatrixAnswerStart(@RequestParam String id, Model model) throws BusinessException {
//		SingleChoiceMatrixAnswer answer = answerService.findSingleChoiceMatrixAnswerById(id);
//		model.addAttribute("singlechoicematrixanswer", answer);
//		return "/common/form";
//	}
//	
//	@PostMapping("/multianswer/update")
//	public String update(@Valid @ModelAttribute("multianswer") ChoiceQuestionMultiAnswer answer, Errors errors) throws BusinessException {
//		if(errors.hasErrors()) {
//			return "/multianswer/form";
//		}
//		answerService.updateAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/multichoicematrixanswer/update")
//	public String update(@Valid @ModelAttribute("multichoicematrixanswer") MultiChoiceMatrixAnswer answer, Errors errors) throws BusinessException {
//		if(errors.hasErrors()) {
//			return "/multichoicematrixanswer/form";
//		}
//		answerService.updateAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/singleanswer/update")
//	public String update(@Valid @ModelAttribute("singleanswer") InputQuestionAnswer answer, Errors errors) throws BusinessException {
//		if(errors.hasErrors()) {
//			return "/singleanswer/form";
//		}
//		answerService.updateAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/singlechoicematrixanswer/update")
//	public String update(@Valid @ModelAttribute("selectanswer") SingleChoiceMatrixAnswer answer, Errors errors) throws BusinessException {
//		if(errors.hasErrors()) {
//			return "/singlechoicematrixanswer/form";
//		}
//		answerService.updateAnswer(answer);
//		return "redirect:/common/form";
//	}
//
//	/** DELETE **/
//	
//	@GetMapping("/multianswer/delete")
//	public String deleteMultiAnswerStart(@RequestParam String id, Model model) throws BusinessException {
//		ChoiceQuestionMultiAnswer answer = answerService.findMultiAnswerById(id);
//		model.addAttribute("multianswer", answer);
//		return "/common/form";
//	}
//	
//	@GetMapping("/multichoicematrixanswer/delete")
//	public String deleteMultiChoiceMatrixAnswerStart(@RequestParam String id, Model model) throws BusinessException {
//		MultiChoiceMatrixAnswer answer = answerService.findMultiChoiceMatrixAnswerById(id);
//		model.addAttribute("multichoicematrixanswer", answer);
//		return "/common/form";
//	}
//	
//	@GetMapping("/singleanswer/delete")
//	public String deleteSingleAnswerStart(@RequestParam String id, Model model) throws BusinessException {
//		InputQuestionAnswer answer = answerService.findSingleAnswerById(id);
//		model.addAttribute("singleanswer", answer);
//		return "/common/form";
//	}
//	
//	@GetMapping("/singlechoicematrixanswer/delete")
//	public String deleteSingleChoiceMatrixAnswerStart(@RequestParam String id, Model model) throws BusinessException {
//		SingleChoiceMatrixAnswer answer = answerService.findSingleChoiceMatrixAnswerById(id);
//		model.addAttribute("singlechoicematrixanswer", answer);
//		return "/common/form";
//	}
//	
//	@PostMapping("/multianswer/delete")
//	public String delete(@ModelAttribute("multianswer") ChoiceQuestionMultiAnswer answer) throws BusinessException {
//		answerService.deleteAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/multichoicematrixanswer/delete")
//	public String delete(@ModelAttribute("multichoicematrixanswer") MultiChoiceMatrixAnswer answer) throws BusinessException {
//		answerService.deleteAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/singleanswer/delete")
//	public String delete(@ModelAttribute("singleanswer") InputQuestionAnswer answer) throws BusinessException {
//		answerService.deleteAnswer(answer);
//		return "redirect:/common/form";
//	}
//	
//	@PostMapping("/singlechoicematrixanswer/delete")
//	public String delete(@ModelAttribute("singlechoicematrixanswer") SingleChoiceMatrixAnswer answer) throws BusinessException {
//		answerService.deleteAnswer(answer);
//		return "redirect:/common/form";
//	}
}
