package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.univaq.disim.mwt.apollo.domain.Question;

public interface QuestionRepository extends MongoRepository<Question, Long> {
    
	// CRUD Operations
    Question findFirstById(String Id);

}
