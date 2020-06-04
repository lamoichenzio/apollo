package it.univaq.disim.mwt.apollo.domain;

import java.util.Base64;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.bson.types.Binary;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@Document(collection = "SurveyFiles")
@TypeAlias("SurveyFile")
public class DocumentFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String name;
	
	@Getter(AccessLevel.NONE)
	private Binary data;
	
	public String getData() {
		return Base64.getEncoder().encodeToString(data.getData());
	}
}
