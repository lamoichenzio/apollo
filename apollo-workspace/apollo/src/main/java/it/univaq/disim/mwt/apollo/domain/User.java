package it.univaq.disim.mwt.apollo.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 4)
	@Column(unique = true)
	private String username;

	@Size(min = 5)
	private String password;
	
	@Transient
	private String passwordConfirm;
	
	@Transient
	private String oldPassword;
	
	@Transient
	private String newPassword;
	
	@Email
	private String email;
	
	private String firstname;
	
	private String lastname;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String pic;
	
	@OneToOne
	private Role role;
	
}
