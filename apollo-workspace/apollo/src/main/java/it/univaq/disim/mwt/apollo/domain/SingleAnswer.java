package it.univaq.disim.mwt.apollo.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="SingleAnswers")
public class SingleAnswer extends Answer {

	private String answer;
}
