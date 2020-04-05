package it.univaq.disim.mwt.apollo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

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
