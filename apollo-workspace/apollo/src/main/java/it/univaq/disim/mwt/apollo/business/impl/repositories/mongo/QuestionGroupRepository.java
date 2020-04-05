package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.QuestionGroup;

@Repository
public interface QuestionGroupRepository extends MongoRepository<QuestionGroup, String> {
    
	// CRUD Operations
	QuestionGroup findFirstById(String Id);

}
