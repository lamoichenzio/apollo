package it.univaq.disim.mwt.apollo.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class StandardUser extends User{
	
	@OneToMany(
			mappedBy = "owner", 
			cascade = CascadeType.REMOVE)
	@Setter(AccessLevel.NONE)
	private List<Survey> surveys;
	
	public void addSurvey(Survey survey) {
		survey.setOwner(this);
		surveys.add(survey);
	}
}
