package it.univaq.disim.mwt.apollo.business.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.disim.mwt.apollo.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long>{
	
}
