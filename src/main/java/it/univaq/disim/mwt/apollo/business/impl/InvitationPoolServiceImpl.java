package it.univaq.disim.mwt.apollo.business.impl;

import it.univaq.disim.mwt.apollo.business.InvitationPoolService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.InvitationPoolRepository;
import it.univaq.disim.mwt.apollo.domain.InvitationPool;
import it.univaq.disim.mwt.apollo.domain.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvitationPoolServiceImpl implements InvitationPoolService {

	@Autowired
	private InvitationPoolRepository invitationPoolRepository;

	@Override
	@Transactional(readOnly = true)
	public InvitationPool findInvitationPoolBySurvey(Survey survey) throws BusinessException {
		try{
			return invitationPoolRepository.findBySurvey(survey);
		}catch(DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	public void updateInvitationPool(InvitationPool invitationPool) throws BusinessException {
		try{
			invitationPoolRepository.save(invitationPool);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteInvitationPool(InvitationPool invitationPool) throws BusinessException {
		try{
			invitationPoolRepository.delete(invitationPool);
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

}
