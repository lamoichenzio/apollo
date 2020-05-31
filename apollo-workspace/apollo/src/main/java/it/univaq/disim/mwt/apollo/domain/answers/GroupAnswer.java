package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@Document(collection = "GroupAnswers")
@TypeAlias("GroupAnswers")
@EqualsAndHashCode(exclude = "answers")
@ToString(exclude = "group")
public class GroupAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@NotNull
	@DBRef
	private QuestionGroup group;
	
	@NotNull
	@DBRef
	private SurveyAnswer surveyAnswer;
	
	@NotNull
	@DBRef
	@Setter(AccessLevel.NONE)
	private Set<Answer> answers = new HashSet<>();
	
	public void addAnswer(Answer answer) {
		answers.add(answer);
		answer.setGroupAnswer(this);
	}
	
	
	
	
}
