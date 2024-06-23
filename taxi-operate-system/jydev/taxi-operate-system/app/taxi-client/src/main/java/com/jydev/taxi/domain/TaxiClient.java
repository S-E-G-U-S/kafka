package com.jydev.taxi.domain;

import com.jydev.taxi.model.TimeAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Entity
@Table(name = "TAXI_CLIENT")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiClient extends TimeAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    public TaxiClient(String name) {

        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

}
