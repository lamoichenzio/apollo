package it.univaq.disim.mwt.apollo.domain.questions;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Document(collection = "QuestionGroups")
@TypeAlias("QuestionGroup")
public class QuestionGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@NotBlank
	@Size(max=100)
	private String title;
	
	@Size(max=200)
	private String description;
	
	@DBRef
	private Survey survey;

    // Genera sub-document embedded (@DBRef per salvataggio via document references)
	@DBRef
	@Setter(AccessLevel.NONE)
	private Set<Question> questions = new HashSet<>();
	
	public void addQuestion(Question question) {
		question.setQuestionGroup(this);
		questions.add(question);
	}

}
