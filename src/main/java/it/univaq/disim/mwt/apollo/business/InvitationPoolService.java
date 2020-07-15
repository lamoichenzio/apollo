package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.InvitationPool;
import it.univaq.disim.mwt.apollo.domain.Survey;

public interface InvitationPoolService {

	InvitationPool findInvitationPoolBySurvey(Survey survey) throws BusinessException;

	void updateInvitationPool(InvitationPool invitationPool) throws BusinessException;

	void deleteInvitationPool(InvitationPool invitationPool) throws BusinessException;

}
