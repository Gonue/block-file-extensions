package com.example.fileblocking.domain.repository;

import com.example.fileblocking.domain.entity.UploadFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFileEntity, Long> {
}
