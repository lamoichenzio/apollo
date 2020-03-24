package it.univaq.disim.mwt.apollo.domain;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "QUESTIONS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	private File file;
	
	@ManyToOne
	private QuestionGroup questionGroup;
	
	public abstract String getType();
}
