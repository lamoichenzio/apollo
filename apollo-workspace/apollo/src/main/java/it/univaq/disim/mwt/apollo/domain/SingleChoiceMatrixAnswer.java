package it.univaq.disim.mwt.apollo.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SIGLE_CHOICE_MATRIX_ANSWER")
public class SingleChoiceMatrixAnswer extends Answer{

	@ElementCollection
	private Map<String, String> answer = new HashMap<>();
}
