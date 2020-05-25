package it.univaq.disim.mwt.apollo.business.impl.repositories.mongo;

import it.univaq.disim.mwt.apollo.domain.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {

    List<Survey> findSurveysByName(String name);
    List<Survey> findSurveyByStartDateOrEndDate(Date startDate, Date endDate);
    
}
