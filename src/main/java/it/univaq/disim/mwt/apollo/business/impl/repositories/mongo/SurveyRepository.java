package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.apollo.domain.Survey;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {

	/**
	 * Find all surveys with the same name.
	 * @param name String
	 * @return List<Survey>
	 */
    List<Survey> findSurveysByName(String name);

	/**
	 * Count all surveys by User Id.
	 * @param userId Long
	 * @return List<Survey>
	 */
	@Query(value= "{'user._id' : ?0}", count = true)
	int findSurveysCountByUser(Long userId);

	/**
	 * Count all active surveys by User Id.
	 * @param userId Long
	 * @return List<Survey>
	 */
	@Query(value= "{'user._id' : ?0, 'active': true}", count = true)
	int findSurveysActiveCountByUser(Long userId);

    /**
     * Find all survey by start date and end date.
     * @param startDate LocalDate
     * @param endDate LocalDate
     * @return List<Survey>
     */
	List<Survey> findSurveyByStartDateOrEndDate(LocalDate startDate, LocalDate endDate);
    
}
