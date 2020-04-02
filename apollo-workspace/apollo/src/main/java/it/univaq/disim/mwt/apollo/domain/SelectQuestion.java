package it.univaq.disim.mwt.apollo.domain;

import java.util.List;

import javax.persistence.ElementCollection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection="SelectQuestions")
public class SelectQuestion extends Question {
	
	private static final String type = "SELECT";

	@ElementCollection
	private List<String> options;
	
	@Override
	public String getType() {
		return type.toString();
	}
	
}
