package it.univaq.disim.mwt.apollo.presentation.helpers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.mwt.apollo.domain.InvitationPool;
import it.univaq.disim.mwt.apollo.domain.Survey;

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

        Set<String> em = new HashSet<>(Arrays.asList(emails.replace("\"", "").split(";")));
		invitationPool.setEmails(em);
		
		return invitationPool;
	}
}
