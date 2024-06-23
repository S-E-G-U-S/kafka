package com.jydev.taxi.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class TimeAuditableEntity extends BaseEntity {

	@CreatedDate
	@Column(name = "CREATION_TIME_UTC", updatable = false)
	protected Instant creationTimeUtc;

	@LastModifiedDate
	@Column(name = "LAST_MODIFICATION_TIME_UTC")
	protected Instant lastModificationTimeUtc;

}
