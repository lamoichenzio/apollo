package it.univaq.disim.mwt.apollo.presentation;

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
import it.univaq.disim.mwt.apollo.presentation.model.GenericResponseBody;
import it.univaq.disim.mwt.apollo.presentation.model.PrivateSurveyCredentials;
import it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public String createStart(@PathVariable("id") String id, Model model, HttpSession session) throws BusinessException {
        Survey survey = surveyService.findSurveyById(id);
        model.addAttribute("survey", survey);
        if (!survey.isActive()) {
            return "common/user_view/common_pages/survey_not_active";
        }
        if (survey.isSecret() && session.getAttribute("logged") == null) {
            return "common/user_view/common_pages/survey_private";
        }
        SurveyAnswer surveyAnswer = ConversionUtility.survey2SurveyAnswer(survey);
        model.addAttribute("surveyanswer", surveyAnswer);
        session.removeAttribute("logged");
        return "common/user_view/survey";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("surveyanswer") SurveyAnswer surveyAnswer, Errors errors) throws BusinessException {
        if (errors.hasErrors()) {
            log.info(errors.toString());
        }
        surveyAnswerService.createSurveyAnswer(surveyAnswer);
        return "redirect:/common/survey_submitted";
    }

    @GetMapping("/validate")
    public String validateStart(@RequestParam("id") String id, Model model) {
        PrivateSurveyCredentials credentials = new PrivateSurveyCredentials();
        credentials.setSurveyId(id);
        model.addAttribute("credentials", credentials);
        return "common/user_view/components/modals/login_modal :: loginForm";
    }

    @PostMapping("/validate")
    @ResponseBody
    public ResponseEntity<GenericResponseBody> validate(@RequestBody PrivateSurveyCredentials credentials, HttpServletRequest request, HttpSession session) throws BusinessException {
        Survey survey = surveyService.findSurveyById(credentials.getSurveyId());
        InvitationPool pool = invitationPoolService.findInvitationPoolBySurvey(survey);
        GenericResponseBody response = new GenericResponseBody();
        if (pool.getEmails().contains(credentials.getEmail())
                && pool.getPassword().equals(credentials.getPassword())) {
            response.setStatus(ResponseStatus.OK);
            session.setAttribute("logged", true);
            response.setMsg(request.getContextPath() + "/forms/survey/" + survey.getId() + "/fill");
            return ResponseEntity.ok(response);
        }
        response.setStatus(ResponseStatus.ERROR);
        return ResponseEntity.badRequest().body(response);
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
