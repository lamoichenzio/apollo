package it.univaq.disim.mwt.apollo.domain;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;

import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.types.ChoiceType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "MatrixQuestions")
public class MatrixQuestion extends Question{
	
	@ElementCollection
	private List<String> questions;
	
	@ElementCollection
	@CollectionTable(name = "MatrixQuestionValues", joinColumns = @JoinColumn(name = "question_id"))
	private List<String> questionValues;

	@Getter(AccessLevel.NONE)
	private ChoiceType type;
	
	@Override
	public String getType() {
		return type.toString();
	}

}
