package it.univaq.disim.mwt.apollo.domain.questions;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.bson.types.Binary;
import org.springframework.data.annotation.CreatedDate;
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
	
	@NotBlank
	@Indexed(unique=false)
	@Size(max=255)
	private String title;
	
	@CreatedDate
	private Date creationDate;
	
	private QuestionFile file;

	@DBRef(lazy = true)
	private QuestionGroup questionGroup;
	
}
