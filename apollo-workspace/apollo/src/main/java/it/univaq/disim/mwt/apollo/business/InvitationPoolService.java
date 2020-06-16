package it.univaq.disim.mwt.apollo.business;

import java.util.List;

import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.domain.InvitationPool;
import it.univaq.disim.mwt.apollo.domain.Survey;

public interface InvitationPoolService {
	
	List<InvitationPool> findAllInvitationPools() throws BusinessException;

	InvitationPool findInvitationPoolByPassword(String password) throws BusinessException;
	
	InvitationPool findInvitationPoolBySurvey(Survey survey) throws BusinessException;

	InvitationPool findInvitationPoolById(String id) throws BusinessException;

	void createInvitationPool(InvitationPool invitationPool) throws BusinessException;

	void updateInvitationPool(InvitationPool invitationPool) throws BusinessException;

	void deleteInvitationPool(InvitationPool invitationPool) throws BusinessException;
}
