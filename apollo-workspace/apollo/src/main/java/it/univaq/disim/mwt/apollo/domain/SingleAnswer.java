package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SINGLE_ANSWERS")
public class SingleAnswer extends Answer {

	private String answer;
}
