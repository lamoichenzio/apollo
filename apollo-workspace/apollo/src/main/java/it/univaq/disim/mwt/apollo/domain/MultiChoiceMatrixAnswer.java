package it.univaq.disim.mwt.apollo.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "MultiChoiceMatrixAnswers")
public class MultiChoiceMatrixAnswer extends Answer {

	@ElementCollection
	private Map<String, MultiChoiceMatrixAnswerValue> answer = new HashMap<>();
}
