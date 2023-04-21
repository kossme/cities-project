package com.cities.project.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue
    private UUID id;

    private String name;

    @FieldNameConstants(onlyExplicitlyIncluded = true)
    public enum Roles {
        @FieldNameConstants.Include ALLOW_READ,
        @FieldNameConstants.Include ALLOW_EDIT
    }
}