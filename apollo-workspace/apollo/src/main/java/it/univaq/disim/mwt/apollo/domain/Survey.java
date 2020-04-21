package it.univaq.disim.mwt.apollo.domain;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Document(collection = "Surveys")
@TypeAlias("Survey")
public class Survey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Indexed
	@Column(nullable = false)
	private String name;
	
	private String description;
	private File icon;
	private boolean secret;
	private boolean active;
	
	@CreatedDate
	private Date creationDate;
	
	private Date startDate;
	private Date endDate;
	
	@Indexed(unique = true)
	private String urlId;
	
	@OneToMany
	private User user;
	
	@DBRef
	@Setter(AccessLevel.NONE)
	private Set<QuestionGroup> questionGroups = new HashSet<>();
	
	public void addQuestionGroup(QuestionGroup questionGroup) {
		questionGroup.setSurvey(this);
		questionGroups.add(questionGroup);
	}

}
