package it.univaq.disim.mwt.apollo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "QUESTION_GROUPS")
public class QuestionGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	private String description;
	
	@ManyToOne
	private Survey survey;

	@OneToMany(mappedBy = "questionGroup", 
			cascade = CascadeType.ALL)
	@Setter(AccessLevel.NONE)
	private Set<Question> questions = new HashSet<>();
	
	public void addQuestion(Question question) {
		question.setQuestionGroup(this);
		questions.add(question);
	}

}
