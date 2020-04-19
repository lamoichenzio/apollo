package it.univaq.disim.mwt.apollo.domain.questions;

import java.util.List;

import javax.persistence.ElementCollection;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "Questions")
@TypeAlias("MatrixQuestion")
public class MatrixQuestion extends Question {
	
	@ElementCollection
	private List<String> questions;
	
	@ElementCollection
	private List<String> questionValues;

	@Getter(AccessLevel.NONE)
	private ChoiceType type;
	
	@Override
	public String getType() {
		return type.toString();
	}

}
