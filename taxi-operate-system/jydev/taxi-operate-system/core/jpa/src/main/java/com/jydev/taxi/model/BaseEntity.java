package com.jydev.taxi.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@MappedSuperclass
public abstract class BaseEntity extends AbstractAggregateRoot<BaseEntity> {

    public abstract Object getId();
    protected boolean isPersisted() {
        return getId() != null;
    }

    @Override
    protected <T> T registerEvent(T event) {
        if (isPersisted()) {
            return super.registerEvent(event);
        } else {
            return event;
        }
    }
}
