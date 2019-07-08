package com.autozcare.main.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
        )
@Data
public abstract class DateModel implements Serializable {

	private static final long serialVersionUID = -2657400493847354448L;

	@CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
