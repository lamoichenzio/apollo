package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.InvitationPool;
import it.univaq.disim.mwt.apollo.domain.Survey;

@Repository
public interface InvitationPoolRepository extends MongoRepository<InvitationPool, String> {

	/**
	 * Find invitation by survey.
	 * @param survey Survey
	 * @return InvitationPool
	 */
	InvitationPool findBySurvey(Survey survey);
	
	/**
	 * Find invitation by password.
	 * @param password String
	 * @return InvitationPool
	 */
	InvitationPool findByPassword(String password);
}
