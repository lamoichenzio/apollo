package it.univaq.disim.mwt.apollo.domain.answers;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Id;

import lombok.Data;

@Data
public class MultiChoiceMatrixAnswerValue {
	
	@Id
	private String id;
	
	@ElementCollection
	private List<String> values;
}
