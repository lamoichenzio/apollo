package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.sql.Blob;

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
	
	@Size(min = 5)
	@Transient
	private String passwordConfirm;
	
	@Email
	private String email;
	
	private String firstname;
	
	private String lastname;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private Blob pic;
	
	@OneToOne
	@NotNull
	private Role role;
	
}
