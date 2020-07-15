package it.univaq.disim.mwt.apollo.domain;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@Document(collection = "SurveyFiles")
@TypeAlias("SurveyFile")
public class SurveyFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String name;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String data;

}
