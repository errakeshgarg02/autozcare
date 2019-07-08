package com.autozcare.main.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.autozcare.main.enums.RoleName;

import lombok.Data;

@Data
@Entity
@Table(name="role")
@EntityListeners(AuditingEntityListener.class)
public class Role implements Serializable {

	private static final long serialVersionUID = 2383085479399369233L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    @Enumerated(EnumType.STRING)
    @NaturalId
	private RoleName name;

}
