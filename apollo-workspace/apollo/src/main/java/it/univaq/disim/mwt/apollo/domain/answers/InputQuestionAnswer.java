package it.univaq.disim.mwt.apollo.domain.answers;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.InputQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "Answers")
@TypeAlias("InputQuestionAnswer")
public class InputQuestionAnswer extends Answer {

	@DBRef
	@NotNull
	private InputQuestion question;

	private String answer;
}
