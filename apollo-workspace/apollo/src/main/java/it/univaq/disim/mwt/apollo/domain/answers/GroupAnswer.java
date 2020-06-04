package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import it.univaq.disim.mwt.apollo.domain.questions.QuestionGroup;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@Document(collection = "GroupAnswers")
@TypeAlias("GroupAnswers")
@ToString(exclude = {"group", "surveyAnswer"})
public class GroupAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@NotNull
	@DBRef
	private QuestionGroup group;
	
	@NotNull
	@DBRef
	private SurveyAnswer surveyAnswer;
	
	@NotNull
	@DBRef
	@Setter(AccessLevel.NONE)
	private List<SingleAnswer> singleAnswers = new ArrayList<>();
	
	@NotNull
	@DBRef
	@Setter(AccessLevel.NONE)
	private List<MultiAnswer> multiAnswers = new ArrayList<>();
	
	@NotNull
	@DBRef
	@Setter(AccessLevel.NONE)
	private List<SingleChoiceMatrixAnswer> matrixSingleAnswers = new ArrayList<>();
	
	@NotNull
	@DBRef
	@Setter(AccessLevel.NONE)
	private List<MultiChoiceMatrixAnswer> matrixMultiAnswers = new ArrayList<>();
	
	public void addSingleAnswer(SingleAnswer answer) {
		singleAnswers.add(answer);
		answer.setGroupAnswer(this);
	}
	
	public void addMultiAnswer(MultiAnswer answer) {
		multiAnswers.add(answer);
		answer.setGroupAnswer(this);
	}
	
	public void addSingleMatrixAnswer(SingleChoiceMatrixAnswer answer) {
		matrixSingleAnswers.add(answer);
		answer.setGroupAnswer(this);
	}
	
	public void addMultiMatrixAnswer(MultiChoiceMatrixAnswer answer) {
		matrixMultiAnswers.add(answer);
		answer.setGroupAnswer(this);
	}
	
}
