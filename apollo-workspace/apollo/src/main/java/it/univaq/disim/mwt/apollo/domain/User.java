package it.univaq.disim.mwt.apollo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "USERS")
@DiscriminatorColumn(name = "USER_TYPE")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	private String email;
	private Date birthdate;
	private Gender gender;
	private String firstname;
	private String lastname;
	
	@OneToOne
	private Role role;
	
}
