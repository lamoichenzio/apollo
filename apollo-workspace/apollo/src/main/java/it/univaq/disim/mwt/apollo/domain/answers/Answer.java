package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Document(collection = "Answers")
@EqualsAndHashCode(exclude = "surveyAnswer")
@ToString(exclude = "surveyAnswer")
public abstract class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@CreatedDate
	private Date createdDate;

	@LastModifiedDate
	private Date lastModifiedDate;

	@NotNull
	@DBRef
	private SurveyAnswer surveyAnswer;
}
