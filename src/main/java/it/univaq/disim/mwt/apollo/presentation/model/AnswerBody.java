package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerBody {
	private List<String> answers;
	private Map<String, List<String>> matrixAnswers;
}
