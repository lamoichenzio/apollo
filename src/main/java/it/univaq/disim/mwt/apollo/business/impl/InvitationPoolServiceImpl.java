package it.univaq.disim.mwt.apollo.business.impl;

import it.univaq.disim.mwt.apollo.business.InvitationPoolService;
import it.univaq.disim.mwt.apollo.business.exceptions.BusinessException;
import it.univaq.disim.mwt.apollo.business.impl.repositories.mongo.InvitationPoolRepository;
import it.univaq.disim.mwt.apollo.domain.InvitationPool;
import it.univaq.disim.mwt.apollo.domain.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvitationPoolServiceImpl implements InvitationPoolService {

	@Autowired
	private InvitationPoolRepository invitationPoolRepository;

	@Override
	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
	public List<InvitationPool> findAllInvitationPools() throws BusinessException {
		return invitationPoolRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public InvitationPool findInvitationPoolByPassword(String password) throws BusinessException {
		return invitationPoolRepository.findByPassword(password);
	}

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
	@Transactional(readOnly = true)
	public InvitationPool findInvitationPoolById(String id) throws BusinessException {
		try {
			return invitationPoolRepository.findById(id).get();
		}catch (DataAccessException e){
			throw new BusinessException(e);
		}
	}

	@Override
	public void createInvitationPool(InvitationPool invitationPool) throws BusinessException {
		invitationPoolRepository.save(invitationPool);
	}

	@Override
	public void updateInvitationPool(InvitationPool invitationPool) throws BusinessException {
		invitationPoolRepository.save(invitationPool);
	}

	@Override
	public void deleteInvitationPool(InvitationPool invitationPool) throws BusinessException {
		invitationPoolRepository.delete(invitationPool);
	}

}
