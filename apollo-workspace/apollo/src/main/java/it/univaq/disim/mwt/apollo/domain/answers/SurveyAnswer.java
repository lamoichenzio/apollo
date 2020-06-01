package it.univaq.disim.mwt.apollo.domain.answers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@Size(max=50)
	private String name;
	
	@Email(message = "Email should have a valid format")
	@Size(max=60)
	private String email;
	
	@DBRef
	private Survey survey;
	
	@NotNull
	private int totAnswers;
	
	@CreatedDate
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd@HH:mm:ss")
	private LocalDateTime date;
	
	@DBRef
	@NotNull
	@Setter(AccessLevel.NONE)
	private Set<GroupAnswer> groupAnswers = new HashSet<>();
	
	public void addGroupAnswer(GroupAnswer group) {
		groupAnswers.add(group);
		group.setSurveyAnswer(this);
	}
	
}
