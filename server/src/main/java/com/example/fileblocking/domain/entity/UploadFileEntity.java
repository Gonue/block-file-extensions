package com.example.fileblocking.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "upload_file_entity")
@Entity
public class UploadFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "file_name", length = 100, nullable = false)
    private String fileName;

    @Column(name = "file_path", length = 500, nullable = false)
    private String filePath;

    @Column(name = "extension", length = 20, nullable = false)
    private String extension;
}
