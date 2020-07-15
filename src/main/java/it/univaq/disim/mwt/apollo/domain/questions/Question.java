package it.univaq.disim.mwt.apollo.domain.questions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.univaq.disim.mwt.apollo.domain.SurveyFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Document(collection = "Questions")
@EqualsAndHashCode(exclude = "questionGroup")
@ToString(exclude="questionGroup")
@JsonIgnoreProperties(value = {"questionGroup"})
public abstract class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@NotBlank
	@Indexed()
	@Size(max=255)
	private String title;

	private Boolean mandatory;

	@CreatedDate
	private Date creationDate;

	@DBRef
	private SurveyFile file;

	@DBRef
	private QuestionGroup questionGroup;
	
}
