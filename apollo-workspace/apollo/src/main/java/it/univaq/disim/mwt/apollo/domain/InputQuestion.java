package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import it.univaq.disim.mwt.apollo.domain.types.InputType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "INPUT_QUESTIONS")
public class InputQuestion extends Question {
	
	@Getter(AccessLevel.NONE)
	private InputType inputType;

	@Override
	public String getType() {
		return inputType.toString();
	}

}
