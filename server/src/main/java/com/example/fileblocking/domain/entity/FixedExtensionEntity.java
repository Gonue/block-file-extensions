package com.example.fileblocking.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "fixed_extension_entity")
@Entity
public class FixedExtensionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "extension", length = 20, nullable = false, unique = true)
    private String extension;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
