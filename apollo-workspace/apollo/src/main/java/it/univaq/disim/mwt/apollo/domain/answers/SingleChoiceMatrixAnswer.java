package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection="Answers")
@TypeAlias("SingleChoiceMatrixAnswer")
public class SingleChoiceMatrixAnswer extends Answer {

	@ElementCollection
	private Map<String, String> answer = new HashMap<>();
}
