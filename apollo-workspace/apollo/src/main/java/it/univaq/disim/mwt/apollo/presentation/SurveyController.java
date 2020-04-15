package it.univaq.disim.mwt.apollo.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.apollo.business.BusinessException;
import it.univaq.disim.mwt.apollo.business.SurveyService;
import it.univaq.disim.mwt.apollo.business.datatable.RequestGrid;
import it.univaq.disim.mwt.apollo.business.datatable.ResponseGrid;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.domain.User;

@Controller
@RequestMapping("/surveys")
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	@GetMapping("/dashboard")
	public String dashboard() {
		return "/common/dashboard";
	}
	
	@PostMapping("/findallpaginated")
	@ResponseBody
	public ResponseGrid<Survey> findAllPaginated(@RequestBody RequestGrid requestGrid) 
			throws BusinessException{
		User user = Utility.getUser();
		return surveyService.findAllSurveysByUserPaginated(requestGrid, user);
	}

}
