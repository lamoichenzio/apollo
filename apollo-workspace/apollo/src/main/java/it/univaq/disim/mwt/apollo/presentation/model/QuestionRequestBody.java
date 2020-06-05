package it.univaq.disim.mwt.apollo.presentation.model;

import lombok.Data;

@Data
public class QuestionRequestBody {
	private String id;
	private QuestionType type;
}
