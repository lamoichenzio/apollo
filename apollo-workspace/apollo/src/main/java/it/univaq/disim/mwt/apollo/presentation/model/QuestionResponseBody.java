package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.mwt.apollo.domain.questions.Question;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class QuestionResponseBody {
    private String msg;
    private int status;
    
    @Setter(AccessLevel.NONE)
    List<QuestionBody> result = new ArrayList<QuestionBody>();

	public void addQuestionBody(QuestionBody questionBody) {
		result.add(questionBody);
	}
}
