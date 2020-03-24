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

@Data
@Entity
@Table(name = "MULTI_CHOICE_MATRIX_ASWER_VALUES")
public class MultiChoiceMatrixAnswerValue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ElementCollection
	@CollectionTable(name="MultiChoicheMatrixAnswerStringValues")
	private List<String> matrixAnswerValues;
}
