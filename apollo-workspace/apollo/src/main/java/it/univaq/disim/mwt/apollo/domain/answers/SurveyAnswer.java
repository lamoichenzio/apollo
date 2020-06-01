package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
	@NotNull
	private Survey survey;
	
	@CreatedDate
	@DateTimeFormat(iso=ISO.DATE)
	private Date date;
	
	@DBRef
	@NotNull
	@Setter(AccessLevel.NONE)
	private List<GroupAnswer> groupAnswers = new ArrayList<>();
	
	public void addGroupAnswer(GroupAnswer group) {
		groupAnswers.add(group);
		group.setSurveyAnswer(this);
	}
	
}
