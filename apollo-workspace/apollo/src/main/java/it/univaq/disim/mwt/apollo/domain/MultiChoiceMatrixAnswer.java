package it.univaq.disim.mwt.apollo.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "MULTI_CHOICE_MATRIX_ANSWER")
public class MultiChoiceMatrixAnswer extends Answer {

	@ElementCollection
	@CollectionTable(name = "multi_choiche_matrix_answer_jointable")
	private Map<String, MultiChoiceMatrixAnswerValue> answer = new HashMap<>();
}
