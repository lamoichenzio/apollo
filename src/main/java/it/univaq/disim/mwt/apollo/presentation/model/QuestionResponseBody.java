package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionResponseBody extends GenericResponseBody {
    
    @Setter(AccessLevel.NONE)
    List<QuestionBody> questions = new ArrayList<QuestionBody>();

	public void addQuestionBody(QuestionBody questionBody) {
		questions.add(questionBody);
	}
}
