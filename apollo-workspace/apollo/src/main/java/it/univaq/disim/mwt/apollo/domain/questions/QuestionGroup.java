package it.univaq.disim.mwt.apollo.domain.questions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Document(collection = "QuestionGroups")
@TypeAlias("QuestionGroup")
@EqualsAndHashCode(exclude = "questions")
@ToString(exclude = "questions")
public class QuestionGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@NotBlank
	@Size(max=60)
	private String title;
	
	private String description;
	
	@NotNull
	@DBRef
	private Survey survey;

	@DBRef
	private List<Question> questions = new ArrayList<>();
	
	public void addQuestion(Question question) {
		question.setQuestionGroup(this);
		questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		questions.remove(question);
	}

}
