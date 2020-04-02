package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Answers")
public abstract class Answer {
	
	@Id
	private String id;
	
	@DBRef
	private Question question;
	
	@DBRef
	private UserAnswer userAnswer; 
}
