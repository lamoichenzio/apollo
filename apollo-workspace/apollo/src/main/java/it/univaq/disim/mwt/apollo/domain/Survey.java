package it.univaq.disim.mwt.apollo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "SURVEYS")
public class Survey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	private String description;
	private boolean secret;
	private boolean active;
	private Date startDate;
	private Date endDate;
	private String urlId;
	
	@ManyToOne
	private User owner;
	
//	private List<Question> questions;
}
