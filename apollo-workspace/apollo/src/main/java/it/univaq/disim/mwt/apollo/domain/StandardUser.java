package it.univaq.disim.mwt.apollo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.crossstore.RelatedDocument;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class StandardUser extends User {
	
	
//	@Setter(AccessLevel.NONE)
//	@RelatedDocument
//	@DBRef
//	private Set<Survey> surveys = new HashSet<>();
//	
//	public void addSurvey(Survey survey) {
//		survey.setOwner(this);
//		surveys.add(survey);
//	}
}
