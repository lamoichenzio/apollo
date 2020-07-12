package it.univaq.disim.mwt.apollo.presentation.helpers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.validation.Errors;

import it.univaq.disim.mwt.apollo.domain.InvitationPool;
import it.univaq.disim.mwt.apollo.domain.Survey;
import it.univaq.disim.mwt.apollo.presentation.model.ResponseStatus;
import it.univaq.disim.mwt.apollo.presentation.model.SurveyResponseBody;

public class SurveyHelper {

	/**
	 * Build invitation pool for secret survey.
	 * @param emails String
	 * @param invitationPool InvitationPool
	 * @param survey Survey
	 * @return InvitationPool
	 */
	public static InvitationPool buildInvitationPool(String emails, InvitationPool invitationPool, Survey survey) {
		if (invitationPool == null) {
			invitationPool = new InvitationPool();
			invitationPool.setPassword(Utility.generatingRandomAlphanumericString());
			invitationPool.setSurvey(survey);
		}
		
		if (emails != null && !emails.equals("")) {
	        Set<String> em = new HashSet<>(Arrays.asList(emails.split(";")));
			invitationPool.setEmails(em);
		}

		return invitationPool;
	}
	
	/**
	 * Add errors to response.
	 * @param errors Errors
	 * @return SurveyResponseBody
	 */
	public static SurveyResponseBody addErrorResult(Errors errors) {
		SurveyResponseBody response = new SurveyResponseBody();
		response.setStatus(ResponseStatus.ERROR);
		response.setMsg(
			errors.getAllErrors().stream()
			.map(x -> x.getDefaultMessage())
			.collect(Collectors.joining(","))
		);
		return response;
	}


}
