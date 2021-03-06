package it.univaq.disim.mwt.apollo.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Document(collection = "Invitations")
@TypeAlias("Invitation")
@ToString(exclude="survey")
public class InvitationPool {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@DBRef
	@NotNull
	@JsonIgnore
	private Survey survey;
	
	@NotBlank
	@Size(min = 8, max = 32)
	private String password;
	
	@CreatedDate
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd@HH:mm:ss")
	private LocalDateTime creationDate;
	

	@ElementCollection
	@Email
	private Set<String> emails = new HashSet<>();
	
}
