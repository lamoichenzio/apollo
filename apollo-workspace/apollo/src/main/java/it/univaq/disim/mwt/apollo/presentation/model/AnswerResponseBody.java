package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerResponseBody extends ResponseBodyGeneric {
	
    private QuestionBody question;
    private AnswerType type;
    
    @Setter(AccessLevel.NONE)
    private List<AnswerBody> values = new ArrayList<>();

	public void addAnswerBody(AnswerBody answerBody) {
		values.add(answerBody);
	}
}
