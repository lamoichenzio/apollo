package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 4)
	@Column(unique = true)
	private String username;

	@NotBlank
	@Size(min = 5)
	private String password;
	
	@Email
	private String email;
		
	@NotBlank
	private String firstname;
	
	@NotBlank
	private String lastname;
	
	@OneToOne
	private Role role;
	
}
