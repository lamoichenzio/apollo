package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.List;

import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionMultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.ChoiceQuestionSingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.InputQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SelectionQuestionAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerResponseBody extends ResponseBody {
    private List<ChoiceQuestionMultiAnswer> choiceQuestionMultiAnswers;
    private List<ChoiceQuestionSingleAnswer> choiceQuestionSingleAnswers;
    private List<InputQuestionAnswer> inputQuestionAnswers;
    private List<MultiChoiceMatrixAnswer> multiChoiceMatrixAnswers;
    private List<SingleChoiceMatrixAnswer> singleChoiceMatrinxAnswers;
    private List<SelectionQuestionAnswer> selectionQuestionAnswers;
    private QuestionType questionType;
}
