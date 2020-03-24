package it.univaq.disim.mwt.apollo.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.univaq.disim.mwt.apollo.domain.types.ChoiceType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CHOICE_QUESTIONS")
public class ChoiceQuestion extends Question {
	
	@Getter(AccessLevel.NONE)
	private ChoiceType type;
	
	@ElementCollection
	private List<String> options;
	
	private boolean otherChoice;
	
	@Override
	public String getType() {
		return type.toString();
	}
	
}
