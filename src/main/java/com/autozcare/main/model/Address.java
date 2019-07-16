package com.autozcare.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "address")
public class Address extends Auditable {

	private static final long serialVersionUID = -8306332948762458830L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Integer noOfTeamMember;
	private String businessName;
	private String building;
	private String street;
	private String locality;
	private Long pincode;
	private String city;
	private String state;
	private String country;
	
	@OneToOne(mappedBy = "address")
	private User user;
}
