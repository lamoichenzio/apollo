package it.univaq.disim.mwt.apollo.domain.questions;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "Questions")
@TypeAlias("InputQuestion")
public class InputQuestion extends Question {
	
	@NotNull
	@Getter(AccessLevel.NONE)
	private InputType inputType;

	@Override
	public String getType() {
		return inputType.toString();
	}

}
