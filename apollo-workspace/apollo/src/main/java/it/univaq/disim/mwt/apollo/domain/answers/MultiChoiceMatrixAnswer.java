package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.MatrixQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "Answers")
@TypeAlias("MultiChoiceMatrixAnswer")
public class MultiChoiceMatrixAnswer extends Answer {
	
	@DBRef
	@NotNull
	private MatrixQuestion question;

	@ElementCollection
	private Map<String, MultiChoiceMatrixAnswerValue> answers = new HashMap<>();
}
