package it.univaq.disim.mwt.apollo.domain;

import java.util.Base64;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String data;

}
