package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "Answers")
@TypeAlias("MultiAnswer")
public class ChoiceQuestionMultiAnswer extends Answer {

	@DBRef
	@NotNull
	private ChoiceQuestion question;
	
	@ElementCollection
	private List<String> answers;
	
}
