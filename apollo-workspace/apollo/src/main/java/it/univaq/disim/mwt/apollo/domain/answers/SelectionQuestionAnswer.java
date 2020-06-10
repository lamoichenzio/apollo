package it.univaq.disim.mwt.apollo.domain.answers;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.SelectionQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection="Answers")
@TypeAlias("SelectionQuestionAnswer")
public class SelectionQuestionAnswer extends Answer {

	@DBRef
	@NotNull
	private SelectionQuestion question;
	
	private String answer;
}
