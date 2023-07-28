package com.example.fileblocking.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "custom_extension_entity")
@Entity
public class CustomExtensionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "extension", length = 20, nullable = false, unique = true)
    private String extension;
}
