package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.List;

import it.univaq.disim.mwt.apollo.domain.answers.MultiAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.MultiChoiceMatrixAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleAnswer;
import it.univaq.disim.mwt.apollo.domain.answers.SingleChoiceMatrixAnswer;
import lombok.Data;

@Data
public class AnswerResponseBody {
    private String msg;
    private int status;
    private List<MultiAnswer> multiAnswers;
    private List<SingleAnswer> singleAnswers;
    private List<MultiChoiceMatrixAnswer> multiChoiceMatrixAnswers;
    private List<SingleChoiceMatrixAnswer> singleChoiceMatrixAnswers;
}
