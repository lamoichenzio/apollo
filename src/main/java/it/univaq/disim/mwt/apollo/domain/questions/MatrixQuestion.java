package it.univaq.disim.mwt.apollo.domain.questions;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "Questions")
@TypeAlias("MatrixQuestion")
public class MatrixQuestion extends Question {
	
	@NotNull
	@ElementCollection
	private List<String> options;
	
	@NotNull
	@ElementCollection
	private List<String> optionValues;

	@NotNull
	private ChoiceType type;
	
}
