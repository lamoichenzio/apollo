package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class StandardUser extends User {
	
//	@Setter(AccessLevel.NONE)
//	@ManyToOne
//	private Set<String> surveyIds = new HashSet<>();
//	
//	public void addSurvey(String surveyId) {
////		surveyIds.setOwner(this);
//		surveyIds.add(surveyId);
//	}
	
}
