package it.univaq.disim.mwt.apollo.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;

@Data
@Document(collection = "Surveys")
@TypeAlias("Survey")
@EqualsAndHashCode(exclude="questionGroups")
@ToString(exclude="questionGroups")
public class Survey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@NotBlank
	@Indexed
	@Size(max = 50)
	private String name;
	
	private String description;
	
	private DocumentFile icon;
	
	private boolean secret;
	private boolean active;
	
	@CreatedDate
	@DateTimeFormat(iso=ISO.DATE)
	private Date creationDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	//@Indexed(unique=true)
	@Setter(AccessLevel.NONE)
	private String urlId;
	
	@OneToMany
	private User user;
	
	@DBRef
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private Set<QuestionGroup> questionGroups;
	
	public void addQuestionGroup(QuestionGroup questionGroup) {
		questionGroup.setSurvey(this);
		if(questionGroups == null) {
			questionGroups = new HashSet<>();
		}
		questionGroups.add(questionGroup);
	}

	/**
	 * Append the id to the static url part to create the survey public url.
	 * @param id The id of the survey
	 *	
	 */
	public void createSurveyUrl(String id){
		this.urlId = "/answers/"+id;
	}

	/**
	 * Deletes the survey public url
	 */
	public void removeSurveyUrl(){
		this.urlId = "";
	}

	public void removeQuestionGroup(QuestionGroup group) {
		questionGroups.removeIf((QuestionGroup g) -> g.getId().equals(group.getId()));
	}
}
