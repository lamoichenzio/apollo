package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Document(collection="SurveyAnswers")
public class SurveyAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@DBRef
	private Survey survey;
	
	@CreatedDate
	@DateTimeFormat(iso=ISO.DATE)
	private Date date;
	
	@DBRef
	private Set<Answer> answers = new HashSet<>();
	
	public void addAnswer(Answer answer) {
		answer.setUserAnswer(this);
		answers.add(answer);
	}

}
