package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "SurveyFiles")
@TypeAlias("SurveyFile")
public class DocumentFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String name;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String data;

}
