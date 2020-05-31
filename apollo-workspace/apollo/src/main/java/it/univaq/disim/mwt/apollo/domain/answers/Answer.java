package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Document(collection = "Answers")
@EqualsAndHashCode(exclude = "groupAnswer")
@ToString(exclude="groupAnswer")
public abstract class Answer {
	
	@Id
	private String id;
	
	@CreatedDate
	private Date createdDate;
	
	@LastModifiedDate
	private Date lastModifiedDate;

	@DBRef
	private Question question;
	
	@DBRef
	private GroupAnswer groupAnswer; 
}
