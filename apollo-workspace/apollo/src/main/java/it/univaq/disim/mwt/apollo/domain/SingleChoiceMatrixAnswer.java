package it.univaq.disim.mwt.apollo.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection="Answers")
@TypeAlias("SingleChoiceMatrixAnswers")
public class SingleChoiceMatrixAnswer extends Answer {

	@ElementCollection
	private Map<String, String> answer = new HashMap<>();
}
