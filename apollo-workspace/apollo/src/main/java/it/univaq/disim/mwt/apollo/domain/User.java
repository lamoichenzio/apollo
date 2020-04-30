package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "USERS")
@DiscriminatorColumn(name = "USER_TYPE")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@NotBlank
	private String username;


	private String password;
	
	@Column(unique=true)
	@Email
	private String email;
	
//	private Date birthdate;
//	private Gender gender;
	
	@NotBlank
	private String firstname;
	
	@NotBlank
	private String lastname;
	
	@OneToOne
	private Role role;
	
}
