package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerResponseBody extends ResponseBody {
//    private List<ChoiceQuestionMultiAnswer> choiceQuestionMultiAnswers;
//    private List<ChoiceQuestionSingleAnswer> choiceQuestionSingleAnswers;
//    private List<InputQuestionAnswer> inputQuestionAnswers;
//    private List<MultiChoiceMatrixAnswer> multiChoiceMatrixAnswers;
//    private List<SingleChoiceMatrixAnswer> singleChoiceMatrinxAnswers;
//    private List<SelectionQuestionAnswer> selectionQuestionAnswers;
	
    private QuestionBody question;
    private AnswerType type;
    
    @Setter(AccessLevel.NONE)
    private List<AnswerBody> values = new ArrayList<AnswerBody>();

	public void addAnswerBody(AnswerBody answerBody) {
		values.add(answerBody);
	}
}
