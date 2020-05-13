package it.univaq.disim.mwt.apollo.domain.questions;

import java.io.File;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Document(collection = "Questions")
public abstract class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@NotNull
	@Indexed(unique=false)
	@Size(max=255)
	private String title;
	
	private File file;
	
	@DBRef
	private QuestionGroup questionGroup;	
}
