package it.univaq.disim.mwt.apollo.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.UserService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.exceptions.WrongPasswordException;
import it.univaq.disim.mwt.apollo.domain.User;
import it.univaq.disim.mwt.apollo.presentation.helpers.Utility;
import it.univaq.disim.mwt.apollo.presentation.model.GenericResponseBody;
import it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/administration")
@Slf4j
public class AdministrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @GetMapping("/users")
    public String dashboard() {
        return "/common/administration/admin_panel";
    }

    @PostMapping("/findallpaginated")
    @ResponseBody
    public ResponseGrid<User> findAllPaginated(@RequestBody RequestGrid requestGrid) throws BusinessException {
        return userService.findAllPaginated(requestGrid);
    }

    @GetMapping("/overview/{id}")
    public String overviewStart(@PathVariable("id") String id, Model model) throws BusinessException {
        User user = userService.findById(Long.valueOf(id));
        int createdSurveys = surveyService.findSurveysCountByUserId(user.getId());
        int activeSurveys = surveyService.findSurveysActiveCountByUserId(user.getId());
        model.addAttribute("managedUser", user);
        model.addAttribute("createdSurveys", createdSurveys);
        model.addAttribute("activeSurveys", activeSurveys);
        return "/common/administration/overview";
    }

    @GetMapping("/delete/{userId}")
    public String deleteStart(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "/common/administration/modal/delete_modal :: modal";
    }

    @PostMapping("/delete/{userId}")
    @ResponseBody
    public ResponseEntity<GenericResponseBody> delete(@RequestBody String password, @PathVariable Long userId) throws BusinessException{
        log.info("call delete by admin");
        User loggedUser = Utility.getUser();
        GenericResponseBody response = new GenericResponseBody();
        try{
            userService.deleteUserByAdmin(loggedUser,userService.findById(userId), password);
            response.setStatus(it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus.OK);
            return ResponseEntity.ok(response);
        } catch(WrongPasswordException e){
            response.setStatus(ResponseStatus.ERROR);
            response.setMsg("wrong password");
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }

    @ModelAttribute
    public void getUser(Model model) throws BusinessException {
        User user = userService.findById(Utility.getUser().getId());
        model.addAttribute("user",user);
    }


}