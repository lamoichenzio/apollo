package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.List;

import it.univaq.disim.mwt.apollo.domain.answers.MultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerResponseBody extends ResponseBody {
    private List<MultiAnswer> multiAnswers;
    private List<SingleAnswer> singleAnswers;
    private List<MultiChoiceMatrixAnswer> multiChoiceMatrixAnswers;
    private List<SingleChoiceMatrixAnswer> singleChoiceMatrixAnswers;
}
