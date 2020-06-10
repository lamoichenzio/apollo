package it.univaq.disim.mwt.apollo.domain.answers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.univaq.disim.mwt.apollo.domain.Survey;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@Document(collection="SurveyAnswers")
@ToString(exclude = "survey")
@TypeAlias("SurveyAnswer")
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
	@NotNull
	private Survey survey;
	
	@NotNull
	private int totAnswers;
	
	@CreatedDate
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd@HH:mm:ss")
	private LocalDateTime date;
	
	@DBRef
	@NotNull
	@Setter(AccessLevel.NONE)
	private List<InputQuestionAnswer> inputQuestionAnswers = new ArrayList<>();
	
	@DBRef
	@NotNull
	@Setter(AccessLevel.NONE)
	private List<ChoiceQuestionSingleAnswer> choiceQuestionSingleAnswers = new ArrayList<>();
	
	@DBRef
	@NotNull
	@Setter(AccessLevel.NONE)
	private List<SelectionQuestionAnswer> selectionQuestionAnswers = new ArrayList<>();
	
	@DBRef
	@NotNull
	@Setter(AccessLevel.NONE)
	private List<ChoiceQuestionMultiAnswer> choiceQuestionMultiAnswers = new ArrayList<>();
	
	@DBRef
	@NotNull
	@Setter(AccessLevel.NONE)
	private List<SingleChoiceMatrixAnswer> singleChoiceMatrixAnswers = new ArrayList<>();
	
	@DBRef
	@NotNull
	@Setter(AccessLevel.NONE)
	private List<MultiChoiceMatrixAnswer> multiChoiceMatrixAnswers = new ArrayList<>();
	
	
	public void addInputQuestionAnswer(InputQuestionAnswer answer) {
		answer.setSurveyAnswer(this);
		inputQuestionAnswers.add(answer);
	}
	
	public void addChoiceQuestionSingleAnswer(ChoiceQuestionSingleAnswer answer) {
		answer.setSurveyAnswer(this);
		choiceQuestionSingleAnswers.add(answer);
	}
	
	public void addSelectionQuestionAnswer(SelectionQuestionAnswer answer) {
		answer.setSurveyAnswer(this);
		selectionQuestionAnswers.add(answer);
	}
	
	public void addChoiceQuestionMultiAnswer(ChoiceQuestionMultiAnswer answer) {
		answer.setSurveyAnswer(this);
		choiceQuestionMultiAnswers.add(answer);
	}
	
	public void addSingleChoiceMatrixAnswer(SingleChoiceMatrixAnswer answer) {
		answer.setSurveyAnswer(this);
		singleChoiceMatrixAnswers.add(answer);
	}
	
	public void addMultiChoiceMatrixAnswer(MultiChoiceMatrixAnswer answer) {
		answer.setSurveyAnswer(this);
		multiChoiceMatrixAnswers.add(answer);
	}
	
	
}
