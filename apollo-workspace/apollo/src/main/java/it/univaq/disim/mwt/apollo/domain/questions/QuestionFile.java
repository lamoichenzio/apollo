package it.univaq.disim.mwt.apollo.domain.questions;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.bson.types.Binary;

import lombok.Data;

@Data
public class QuestionFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String name;
	private Binary data;
}
