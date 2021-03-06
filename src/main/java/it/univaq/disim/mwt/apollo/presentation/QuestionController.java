package it.univaq.disim.mwt.apollo.presentation;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.univaq.disim.mwt.apollo.business.QuestionGroupService;
import it.univaq.disim.mwt.apollo.business.QuestionService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.validators.FileValidator;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.ChoiceType;
import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import it.univaq.disim.mwt.apollo.domain.questions.Question;
import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import it.univaq.disim.mwt.apollo.presentation.helpers.Utility;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionBody;
import it.univaq.disim.mwt.apollo.presentation.model.QuestionResponseBody;
import it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/questions")
@Slf4j
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionGroupService questionGroupService;

    @Autowired
    private FileValidator validator;

    
	@GetMapping("/questionlist/{groupId}")
	@ResponseBody
	public ResponseEntity<QuestionResponseBody> findQuestionsByGroup(@PathVariable("groupId") String groupId) throws BusinessException {
		QuestionGroup group = questionGroupService.findQuestionGroupById(groupId);
		QuestionResponseBody response = new QuestionResponseBody();
		
		for (Question question : group.getQuestions()) {
			QuestionBody body = QuestionBody.builder()
					.title(question.getTitle())
					.id(question.getId())
					.type(Utility.getQuestionType(question))
					.build();
			response.addQuestionBody(body);
		}
		
		response.setStatus(ResponseStatus.OK);
		
		return ResponseEntity.ok(response);
	}
	
    /**
     * Choice Question
     **/

    @GetMapping("/choicequestion/create")
    public String createChoiceStart(@RequestParam String group_id, @RequestParam ChoiceType type, Model model)
            throws BusinessException {
        ChoiceQuestion question = new ChoiceQuestion();
        List<String> optionList = Arrays.asList("");

        // Set question group
        QuestionGroup group = questionGroupService.findQuestionGroupById(group_id);
        question.setQuestionGroup(group);

        question.setOptions(optionList);
        question.setChoiceType(type);

        model.addAttribute("question", question);

        return "/common/surveys/components/questions/modals/choice_question_modal :: questionChoiceForm";
    }

    @PostMapping("/choicequestion/create")
    public String create(@Valid @ModelAttribute("question") ChoiceQuestion question, Errors errors, @RequestParam("questionfile") MultipartFile file) throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        
        // Validate file
        validator.validate(file, errors);
        
        if (errors.hasErrors()) {
            log.error(errors.toString());
            return "redirect:/surveys/detail/" + group.getSurvey().getId() + "?error=true";
        }

        // Option list has duplicates or is too short
        if (Utility.findDuplicates(question.getOptions()) || question.getOptions().size() < 2) {
        	return "redirect:/surveys/detail/" + group.getSurvey().getId() + "?error=true";
        }
        
        // Create question
        questionService.createQuestion(question, file);
        
        // Update group
        group.addQuestion(question);
        questionGroupService.updateQuestionGroup(group);

        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

    @GetMapping("/choicequestion/update")
    public String updateStartChoice(@RequestParam String id, Model model) throws BusinessException {
        ChoiceQuestion question = questionService.findChoiceQuestionById(id);
        model.addAttribute("question", question);
        return "/common/surveys/components/questions/modals/choice_question_modal :: questionChoiceForm";
    }

    @PostMapping("/choicequestion/update")
    public String update(@Valid @ModelAttribute("question") ChoiceQuestion question, Errors errors, @RequestParam("questionfile") MultipartFile file, @RequestParam("deleteFile") Boolean deleteFile) throws BusinessException {
        // Validate file
    	validator.validate(file, errors);

        if (errors.hasErrors()) {
        	log.error(errors.toString());
            return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        // Option list has duplicates or is too short
        if (Utility.findDuplicates(question.getOptions()) || question.getOptions().size() < 2) {
        	return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        questionService.updateQuestion(question, file, deleteFile);
        return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId();
    }

    @GetMapping("/choicequestion/delete")
    public String deleteChoiceStart(@RequestParam String id, Model model) throws BusinessException {
        ChoiceQuestion question = questionService.findChoiceQuestionById(id);
        model.addAttribute("question", question);
        return "/common/surveys/components/questions/modals/delete_question_modal :: questionDelete";
    }

    @PostMapping("/choicequestion/delete")
    public String delete(@ModelAttribute("choicequestion") ChoiceQuestion question) throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        questionService.deleteQuestion(question);
        group.removeQuestion(question);
        questionGroupService.updateQuestionGroup(group);

        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

    /**
     * Input Question
     **/

    @GetMapping("/inputquestion/create")
    public String createInputStart(@RequestParam String group_id, Model model) throws BusinessException {
        InputQuestion question = new InputQuestion();
        QuestionGroup group = questionGroupService.findQuestionGroupById(group_id);
        question.setQuestionGroup(group);
        model.addAttribute("question", question);
        return "/common/surveys/components/questions/modals/input_question_modal :: modal-input-question";
    }

    @PostMapping("/inputquestion/create")
    public String create(@Valid @ModelAttribute("question") InputQuestion question, Errors errors,
                         @RequestParam("questionfile") MultipartFile file) throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        validator.validate(file, errors);
        
        if (errors.hasErrors()) {
            log.error(errors.toString());
            return "redirect:/surveys/detail/" + group.getSurvey().getId() + "?error=true";
        }
        
        questionService.createQuestion(question, file);
        group.addQuestion(question);
        questionGroupService.updateQuestionGroup(group);
        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

    @GetMapping("/inputquestion/update")
    public String updateStartInput(@RequestParam String id, Model model) throws BusinessException {
        InputQuestion question = questionService.findInputQuestionById(id);
        model.addAttribute("question", question);
        return "/common/surveys/components/questions/modals/input_question_modal :: modal-input-question";
    }

    @PostMapping("/inputquestion/update")
    public String update(@Valid @ModelAttribute("question") InputQuestion question, Errors errors,
                         @RequestParam("questionfile") MultipartFile file, @RequestParam("deleteFile") Boolean deleteFile)
            throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        validator.validate(file, errors);
        
        if (errors.hasErrors()) {
            return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        questionService.updateQuestion(question, file, deleteFile);
        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

    @GetMapping("/inputquestion/delete")
    public String deleteInputStart(@RequestParam String id, Model model) throws BusinessException {
        InputQuestion question = questionService.findInputQuestionById(id);
        model.addAttribute("question", question);
        return "/common/surveys/components/questions/modals/delete_question_modal :: questionDelete";
    }

    @PostMapping("/inputquestion/delete")
    public String delete(@ModelAttribute("question") InputQuestion question) throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        questionService.deleteQuestion(question);
        group.removeQuestion(question);
        questionGroupService.updateQuestionGroup(group);
        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

    /**
     * Matrix Question.
     **/

    @GetMapping("/matrixquestion/create")
    public String createMatrixStart(@RequestParam String group_id, @RequestParam ChoiceType type, Model model) throws BusinessException {
        MatrixQuestion question = new MatrixQuestion();
        List<String> optionList = Arrays.asList("");

        question.setOptions(optionList);
        question.setOptionValues(optionList);
        question.setType(type);

        // Set Group
        QuestionGroup group = questionGroupService.findQuestionGroupById(group_id);
        question.setQuestionGroup(group);
        
        model.addAttribute("question", question);
        model.addAttribute("instance", "MATRIX");
        
        return "/common/surveys/components/questions/modals/matrix_question_modal :: modal_matrix_question(question=${question},instance=${instance})";
    }

    @PostMapping("/matrixquestion/create")
    public String create(@Valid @ModelAttribute("question") MatrixQuestion question, Errors errors, @RequestParam("questionfile") MultipartFile file) throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        validator.validate(file, errors);

        if (errors.hasErrors()) {
            log.info(errors.toString());
            return "redirect:/surveys/detail/" + group.getSurvey().getId() + "?error=true";
        }
        
        // Option list has duplicates or is too short
        if (Utility.findDuplicates(question.getOptions()) || question.getOptions().size() < 2) {
        	return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        // Option list has duplicates or is too short
        if (Utility.findDuplicates(question.getOptionValues()) || question.getOptionValues().size() < 2) {
        	return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        questionService.createQuestion(question, file);

        // Update group
        group.addQuestion(question);
        questionGroupService.updateQuestionGroup(group);

        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

    @GetMapping("/matrixquestion/update")
    public String updateStartMatrix(@RequestParam String id, Model model) throws BusinessException {
        MatrixQuestion question = questionService.findMatrixQuestionById(id);
        model.addAttribute("question", question);
        model.addAttribute("instance", "MATRIX");
        return "/common/surveys/components/questions/modals/matrix_question_modal :: modal_matrix_question(instance=${instance})";
    }

    @PostMapping("/matrixquestion/update")
    public String update(@Valid @ModelAttribute("question") MatrixQuestion question, Errors errors, @RequestParam("questionfile") MultipartFile file, @RequestParam("deleteFile") Boolean deleteFile)
            throws BusinessException {
        validator.validate(file, errors);
        
        if (errors.hasErrors()) {
            return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        // Option list has duplicates or is too short
        if (Utility.findDuplicates(question.getOptions()) || question.getOptions().size() < 2) {
        	return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        // Option list has duplicates or is too short
        if (Utility.findDuplicates(question.getOptionValues()) || question.getOptionValues().size() < 2) {
        	return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        questionService.updateQuestion(question, file, deleteFile);
        return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId();
    }

    @GetMapping("/matrixquestion/delete")
    public String deleteMatrixStart(@RequestParam String id, Model model) throws BusinessException {
        MatrixQuestion question = questionService.findMatrixQuestionById(id);
        model.addAttribute("question", question);
        return "/common/surveys/components/questions/modals/delete_question_modal :: questionDelete";
    }

    @PostMapping("/matrixquestion/delete")
    public String delete(@ModelAttribute("question") MatrixQuestion question) throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        questionService.deleteQuestion(question);
        group.removeQuestion(question);
        questionGroupService.updateQuestionGroup(group);

        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

    /**
     * Selection Question.
     **/

    @GetMapping("/selectionquestion/create")
    public String createSelectStart(@RequestParam String group_id, Model model) throws BusinessException {
        SelectionQuestion question = new SelectionQuestion();
        List<String> optionList = Arrays.asList("");

        // Get question group
        QuestionGroup group = questionGroupService.findQuestionGroupById(group_id);

        question.setQuestionGroup(group);
        question.setOptions(optionList);

        model.addAttribute("question", question);

        return "/common/surveys/components/questions/modals/selection_question_modal :: questionSelectionForm";
    }

    @PostMapping("/selectionquestion/create")
    public String create(@Valid @ModelAttribute("question") SelectionQuestion question, Errors errors, @RequestParam("questionfile") MultipartFile file) throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        validator.validate(file, errors);
        
        if (errors.hasErrors()) {
        	log.error(errors.toString());
            return "redirect:/surveys/detail/" + group.getSurvey().getId() + "?error=true";
        }

        // Option list has duplicates or is too short
        if (Utility.findDuplicates(question.getOptions()) || question.getOptions().size() < 2) {
        	return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        // Create question
        questionService.createQuestion(question, file);

        // Update group
        group.addQuestion(question);
        questionGroupService.updateQuestionGroup(group);

        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

    @GetMapping("/selectionquestion/update")
    public String updateStartSelect(@RequestParam String id, Model model) throws BusinessException {
        SelectionQuestion question = questionService.findSelectionQuestionById(id);
        model.addAttribute("question", question);
        return "/common/surveys/components/questions/modals/selection_question_modal :: questionSelectionForm";
    }

    @PostMapping("/selectionquestion/update")
    public String update(@Valid @ModelAttribute("question") SelectionQuestion question, Errors errors, @RequestParam("questionfile") MultipartFile file, @RequestParam("deleteFile") Boolean deleteFile) throws BusinessException {
        validator.validate(file, errors);
        
        if (errors.hasErrors()) {
        	log.error(errors.toString());
            return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        // Option list has duplicates or is too short
        if (Utility.findDuplicates(question.getOptions()) || question.getOptions().size() < 2) {
        	return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId() + "?error=true";
        }
        
        // Update Question
        questionService.updateQuestion(question, file, deleteFile);
        return "redirect:/surveys/detail/" + question.getQuestionGroup().getSurvey().getId();
    }

    @GetMapping("/selectionquestion/delete")
    public String deleteSelectStart(@RequestParam String id, Model model) throws BusinessException {
        SelectionQuestion question = questionService.findSelectionQuestionById(id);
        model.addAttribute("question", question);
        return "/common/surveys/components/questions/modals/delete_question_modal :: questionDelete";
    }

    @PostMapping("/selectionquestion/delete")
    public String delete(@ModelAttribute("question") SelectionQuestion question) throws BusinessException {
        QuestionGroup group = question.getQuestionGroup();
        questionService.deleteQuestion(question);
        group.removeQuestion(question);
        questionGroupService.updateQuestionGroup(group);

        return "redirect:/surveys/detail/" + group.getSurvey().getId();
    }

}
