package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.List;

import javax.persistence.ElementCollection;

import lombok.Data;

@Data
public class MultiChoiceMatrixAnswerValue {
	
	@ElementCollection
	private List<String> values;
}
