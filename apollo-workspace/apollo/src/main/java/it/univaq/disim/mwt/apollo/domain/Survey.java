package it.univaq.disim.mwt.apollo.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "Surveys")
public class Survey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Column(nullable = false)
	private String name;
	private String description;
	private boolean secret;
	private boolean active;
	private Date startDate;
	private Date endDate;
	private String urlId;
	
	@DBRef
	@Setter(AccessLevel.NONE)
	private String ownerId;
	
	@DBRef
	@Setter(AccessLevel.NONE)
	private Set<QuestionGroup> questionGroups = new HashSet<>();
	
	public void addQuestionGroup(QuestionGroup questionGroup) {
		questionGroup.setSurvey(this);
		questionGroups.add(questionGroup);
	}
	
	public void setOwnerId(Long ownerId) {
		this.ownerId = Long.toString(ownerId);
	}
}
