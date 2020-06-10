package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.Fetch;

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
	
	@NotBlank
	@Size(min = 5)
	@Transient
	private String passwordConfirm;
	
	@Email
	private String email;
	
	private String firstname;
	
	private String lastname;
	
	@OneToOne
	@NotNull
	private Role role;
	
}
