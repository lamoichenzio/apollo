package it.univaq.disim.mwt.apollo.domain;

import java.io.File;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Questions")
public abstract class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Indexed(unique=false)
	private String title;
	
	private File file;
	
	@DBRef
	private QuestionGroup questionGroup;
	
	public abstract String getType();
}
