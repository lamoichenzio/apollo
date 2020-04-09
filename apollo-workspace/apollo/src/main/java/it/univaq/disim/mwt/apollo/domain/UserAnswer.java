package it.univaq.disim.mwt.apollo.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Document(collection="UserAnswers")
public class UserAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@DBRef
	private Survey survey;
	
	private Date date;
	
	@DBRef
	@Setter(AccessLevel.NONE)
	private Set<Answer> answers = new HashSet<>();
	
	public void addAnswer(Answer answer) {
		answer.setUserAnswer(this);
		answers.add(answer);
	}

}
