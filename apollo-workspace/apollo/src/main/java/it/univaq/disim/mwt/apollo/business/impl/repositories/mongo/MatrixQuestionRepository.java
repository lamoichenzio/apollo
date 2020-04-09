package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.MatrixQuestion;

@Repository
public interface MatrixQuestionRepository extends MongoRepository<MatrixQuestion, String> {
    
	/**
	 * Find first MatixQuestion by id.
	 * @param id This is the id of the MatrixQuestion you want to find.
	 * @return MatrixQuestion
	 */
	MatrixQuestion findFirstById(String id);

}
