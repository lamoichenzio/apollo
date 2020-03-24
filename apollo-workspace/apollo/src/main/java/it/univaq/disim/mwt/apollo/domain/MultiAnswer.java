package it.univaq.disim.mwt.apollo.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "MULTI_ANSWERS")
public class MultiAnswer extends Answer{

	@ElementCollection
	private List<String> answers;
}
