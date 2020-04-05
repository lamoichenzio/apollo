package it.univaq.disim.mwt.apollo.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MultiChoiceMatrixAnswerValue {
	
	@Id
	private String id;
	
	@ElementCollection
	private List<String> matrixAnswerValues;
}
