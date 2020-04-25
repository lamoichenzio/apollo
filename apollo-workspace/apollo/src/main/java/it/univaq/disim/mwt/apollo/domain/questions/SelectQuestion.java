package it.univaq.disim.mwt.apollo.domain.questions;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection="Questions")
@TypeAlias("SelectQuestion")
public class SelectQuestion extends Question {
	
	private static final String type = "SELECT";

	@NotNull
	@ElementCollection
	private List<String> options;
	
	@Override
	public String getType() {
		return type.toString();
	}
	
}
