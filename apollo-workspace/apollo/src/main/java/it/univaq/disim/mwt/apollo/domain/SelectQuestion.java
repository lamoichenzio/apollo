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
@Table(name = "SELECT_QUESTIONS")
public class SelectQuestion extends Question {
	
	@ElementCollection
	private List<String> options;
	
	private static final String type = "SELECT";

	@Override
	public String getType() {
		return type.toString();
	}
	
}
