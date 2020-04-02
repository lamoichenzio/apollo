package it.univaq.disim.mwt.apollo.domain;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

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
