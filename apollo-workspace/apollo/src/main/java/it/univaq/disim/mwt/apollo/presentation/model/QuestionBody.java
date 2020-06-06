package it.univaq.disim.mwt.apollo.presentation.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionBody {
	private String id;
	private String title;
	private List<String> options;
	private List<String> optionValues;
	private QuestionType type;
}
