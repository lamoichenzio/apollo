package it.univaq.disim.mwt.apollo.domain.answers;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.ChoiceQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Document(collection="Answers")
@TypeAlias("ChoiceQuestionSingleAnswer")
public class ChoiceQuestionSingleAnswer extends Answer {

	@DBRef
	@NotNull
	private ChoiceQuestion question;
	
	private String answer;
}
