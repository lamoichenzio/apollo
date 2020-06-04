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
